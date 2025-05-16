package share.sh4re.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.domain.Code;
import share.sh4re.domain.Like;
import share.sh4re.domain.User;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.dto.res.CreateCodeRes.CreateCodeResData;
import share.sh4re.dto.res.DeleteCodeRes;
import share.sh4re.dto.res.DeleteCodeRes.DeleteCodeResData;
import share.sh4re.dto.res.GetAllCodesRes;
import share.sh4re.dto.res.GetAllCodesRes.GetAllCodesResData;
import share.sh4re.dto.res.GetCodeRes;
import share.sh4re.dto.res.GetCodeRes.GetCodeResData;
import share.sh4re.dto.res.LikeCodeRes;
import share.sh4re.dto.res.LikeCodeRes.LikeCodeResData;
import share.sh4re.exceptions.errorcode.CodeErrorCode;
import share.sh4re.exceptions.errorcode.UserErrorCode;
import share.sh4re.repository.CodeRepository;
import share.sh4re.repository.LikeRepository;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class CodeService {
  private final int PAGE_SIZE = 12;
  private final CodeRepository codeRepository;
  private final UserRepository userRepository;
  private final OpenAiService openAiService;
  private final LikeRepository likeRepository;

  public ResponseEntity<CreateCodeRes> createCode(CreateCodeReq createCodeReq) {
    Code newCode = new Code();
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    Optional<User> userRes = userRepository.findByUsername(username);
    if(userRes.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = userRes.get();
//    String generateDescription = openAiService.generateDescription(createCodeReq.getCode());
    newCode.update(
        createCodeReq.getTitle(),
        "generateDescription",
        createCodeReq.getCode(),
        createCodeReq.getField(),
        user
    );
    codeRepository.save(newCode);
    return new ResponseEntity<>(new CreateCodeRes(true, new CreateCodeResData(newCode.getId())), HttpStatus.OK);
  }

  public ResponseEntity<GetAllCodesRes> getAllCodes(int pageNo, String criteria) {
    if(pageNo < 0) throw CodeErrorCode.INVALID_ARGUMENT.defaultException();
    Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
    Page<Code> page = codeRepository.findAll(pageable);
    return new ResponseEntity<>(new GetAllCodesRes(true, new GetAllCodesResData(page.getContent())), HttpStatus.OK);
  }

  public ResponseEntity<GetCodeRes> getCode(String codeId) {
    Code code = validateCodeId(codeId);
    Boolean isLiked = false;
    boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    if(isAuthenticated){
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
}
