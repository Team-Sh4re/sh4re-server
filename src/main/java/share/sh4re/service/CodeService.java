package share.sh4re.service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.domain.Assignment;
import share.sh4re.domain.Code;
import share.sh4re.domain.Like;
import share.sh4re.domain.User;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.req.EditCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.dto.res.CreateCodeRes.CreateCodeResData;
import share.sh4re.dto.res.DeleteCodeRes;
import share.sh4re.dto.res.DeleteCodeRes.DeleteCodeResData;
import share.sh4re.dto.res.EditCodeRes;
import share.sh4re.dto.res.GetAllCodesRes;
import share.sh4re.dto.res.GetAllCodesRes.GetAllCodesResData;
import share.sh4re.dto.res.GetCodeRes;
import share.sh4re.dto.res.GetCodeRes.GetCodeResData;
import share.sh4re.dto.res.LikeCodeRes;
import share.sh4re.dto.res.LikeCodeRes.LikeCodeResData;
import share.sh4re.exceptions.errorcode.AssignmentErrorCode;
import share.sh4re.exceptions.errorcode.CodeErrorCode;
import share.sh4re.exceptions.errorcode.UserErrorCode;
import share.sh4re.repository.AssignmentRepository;
import share.sh4re.repository.CodeRepository;
import share.sh4re.repository.LikeRepository;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CodeService {
  private final int PAGE_SIZE = 16;
  private final CodeRepository codeRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final OpenAiService openAiService;
  private final AssignmentRepository assignmentRepository;

  public ResponseEntity<CreateCodeRes> createCode(CreateCodeReq createCodeReq) {
    Code newCode = new Code();
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if(createCodeReq.getAssignmentId() != null) {
      Optional<Assignment> assignment = assignmentRepository.findById(createCodeReq.getAssignmentId());
      if(assignment.isEmpty()) throw AssignmentErrorCode.ASSIGNMENT_NOT_FOUND.defaultException();
      newCode.setAssignment(assignment.get());
    }
    Optional<User> userRes = userRepository.findByUsername(username);
    if(userRes.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = userRes.get();
    String generateDescription = openAiService.generateDescription(createCodeReq.getCode());
    newCode.update(
        createCodeReq.getTitle(),
         generateDescription,
//        "코드 설명 자동 생성 기능이 일시정지되었습니다.",
        createCodeReq.getCode(),
        createCodeReq.getField(),
        user.getClassNumber(),
        user
    );
    codeRepository.save(newCode);
    return new ResponseEntity<>(new CreateCodeRes(true, new CreateCodeResData(newCode.getId())), HttpStatus.OK);
  }

  public ResponseEntity<GetAllCodesRes> getAllCodes(int pageNo, String criteria, Long classNo, Long assignmentId) {
    if(pageNo <= 0) throw CodeErrorCode.INVALID_ARGUMENT.defaultException();
    pageNo -= 1;

    Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
    Specification<Code> specs = getSpecsForAllCodes(classNo, assignmentId);

    Page<Code> page = codeRepository.findAll(specs, pageable);
    return new ResponseEntity<>(new GetAllCodesRes(true, new GetAllCodesResData(page.getContent(), page.getTotalPages())), HttpStatus.OK);
  }

  public ResponseEntity<GetCodeRes> getCode(String codeId) {
    Code code = validateCodeId(codeId);
    Boolean isLiked = false;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)){
      String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
      User user = userRepository.findByUsername(username).orElseThrow(UserErrorCode.MEMBER_NOT_FOUND::defaultException);
      isLiked = likeRepository.existsByCodeAndUser(code, user);
    }
    return new ResponseEntity<>(new GetCodeRes(true, new GetCodeResData(code, isLiked)), HttpStatus.OK);
  }

  public ResponseEntity<DeleteCodeRes> deleteCode(String codeId) {
    Code code = validateCodeId(codeId);
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if(!username.equals(code.getUser().getUsername())) throw CodeErrorCode.FORBIDDEN_REQUEST.defaultException();
    codeRepository.deleteById(code.getId());
    return new ResponseEntity<>(new DeleteCodeRes(
        true, new DeleteCodeResData(code.getId())
    ), HttpStatus.OK);
  }

  public ResponseEntity<EditCodeRes> editCode(EditCodeReq editCodeReq, String codeId) {
    Code code = validateCodeId(codeId);
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if(!username.equals(code.getUser().getUsername())) throw CodeErrorCode.FORBIDDEN_REQUEST.defaultException();
    String description = editCodeReq.getDescription();
    if(!editCodeReq.getCode().equals(code.getCode())){
      description = openAiService.generateDescription(editCodeReq.getCode());
    }
    code.update(editCodeReq.getTitle(), description, editCodeReq.getCode(), null, null, null);
    code.setAssignment(null);
    if(editCodeReq.getAssignmentId() != null){
      Optional<Assignment> assignment = assignmentRepository.findById(editCodeReq.getAssignmentId());
      if(assignment.isEmpty()) throw AssignmentErrorCode.ASSIGNMENT_NOT_FOUND.defaultException();
      code.setAssignment(assignment.get());
    }
    codeRepository.save(code);
    return new ResponseEntity<>(new EditCodeRes(true, new EditCodeRes.EditCodeResData(code.getId())), HttpStatus.OK);
  }

  public synchronized ResponseEntity<LikeCodeRes> likeCode(String codeId) {
    Code code = validateCodeId(codeId);
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    User user = userRepository.findByUsername(username).orElseThrow(UserErrorCode.MEMBER_NOT_FOUND::defaultException);
    Optional<Like> likeRes = likeRepository.findByCodeAndUser(code, user);
    if(likeRes.isEmpty()){
      Like newLike = new Like(user, code);
      likeRepository.save(newLike);
      code.increaseLikes();
    } else {
      Like like = likeRes.get();
      likeRepository.delete(like);
      code.decreaseLikes();
    }
    return new ResponseEntity<>(new LikeCodeRes(true, new LikeCodeResData(code.getId())), HttpStatus.OK);
  }

  private Code validateCodeId(String codeId){
    if(codeId == null || codeId.isEmpty()) throw CodeErrorCode.INVALID_ARGUMENT.defaultException();
    long id;
    try {
      id = Long.parseLong(codeId);
    } catch (NumberFormatException e){
      throw CodeErrorCode.INVALID_ARGUMENT.defaultException();
    }
    Optional<Code> codeRes = codeRepository.findById(id);
    if(codeRes.isEmpty()) throw CodeErrorCode.CODE_NOT_FOUND.defaultException();
    return codeRes.get();
  }

  public Specification<Code> getSpecsForAllCodes(Long classNo, Long assignmentId) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (classNo != null) {
        predicates.add(cb.equal(root.get("classNo"), classNo));
      }
      if (assignmentId != null) {
        predicates.add(cb.equal(root.get("assignment").get("id"), assignmentId));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }

}
