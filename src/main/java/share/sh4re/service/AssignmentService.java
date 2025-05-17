package share.sh4re.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.domain.Assignment;
import share.sh4re.domain.Code;
import share.sh4re.domain.User;
import share.sh4re.domain.User.Roles;
import share.sh4re.dto.req.CreateAssignmentReq;
import share.sh4re.dto.req.DeleteAssignmentReq;
import share.sh4re.dto.req.UpdateAssignmentReq;
import share.sh4re.dto.res.CreateAssignmentRes;
import share.sh4re.dto.res.CreateAssignmentRes.CreateAssignmentResData;
import share.sh4re.dto.res.DeleteAssignmentRes;
import share.sh4re.dto.res.GetAllAssignmentsRes;
import share.sh4re.dto.res.GetAllAssignmentsRes.GetAllAssignmentsResData;
import share.sh4re.dto.res.UpdateAssignmentRes;
import share.sh4re.exceptions.errorcode.AssignmentErrorCode;
import share.sh4re.exceptions.errorcode.AuthErrorCode;
import share.sh4re.exceptions.errorcode.UserErrorCode;
import share.sh4re.repository.AssignmentRepository;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {
  private final AssignmentRepository assignmentRepository;
  private final UserRepository userRepository;

  public ResponseEntity<CreateAssignmentRes> createAssignment(CreateAssignmentReq createAssignmentReq) {
    validatePermission();
    if(createAssignmentReq.getTitle() == null || createAssignmentReq.getTitle().isEmpty()) throw AssignmentErrorCode.INVALID_ARGUMENT.defaultException();
    Assignment assignment = new Assignment();
    assignment.update(createAssignmentReq.getTitle(), createAssignmentReq.getDescription());
    assignmentRepository.save(assignment);
    return new ResponseEntity<>(new CreateAssignmentRes(true, new CreateAssignmentResData(assignment.getId())),
        HttpStatus.OK);
  }

  public ResponseEntity<GetAllAssignmentsRes> getAllAssignments() {
    List<Assignment> assignments = assignmentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    return new ResponseEntity<>(new GetAllAssignmentsRes(true, new GetAllAssignmentsResData(assignments)), HttpStatus.OK);
  }

  public ResponseEntity<UpdateAssignmentRes> updateAssignment(UpdateAssignmentReq updateAssignmentReq) {
    validatePermission();
    Optional<Assignment> assignmentRes = assignmentRepository.findById(updateAssignmentReq.getId());
    if(assignmentRes.isEmpty()) throw AssignmentErrorCode.ASSIGNMENT_NOT_FOUND.defaultException();
    Assignment assignment = assignmentRes.get();
    assignment.update(updateAssignmentReq.getTitle(), updateAssignmentReq.getDescription());
    assignmentRepository.save(assignment);
    return new ResponseEntity<>(new UpdateAssignmentRes(true, new UpdateAssignmentRes.UpdateAssignmentResData(assignment.getId())), HttpStatus.OK);
  }

  public ResponseEntity<DeleteAssignmentRes> deleteAssignment(DeleteAssignmentReq deleteAssignmentRes) {
    validatePermission();
    Optional<Assignment> assignmentRes = assignmentRepository.findById(deleteAssignmentRes.getId());
    if(assignmentRes.isEmpty()) throw AssignmentErrorCode.ASSIGNMENT_NOT_FOUND.defaultException();
    Assignment assignment = assignmentRes.get();
    for(Code code : assignment.getCodeList()) {
      code.setAssignment(null);
    }
    assignmentRepository.delete(assignment);
    return new ResponseEntity<>(new DeleteAssignmentRes(true, new DeleteAssignmentRes.DeleteAssignmentResData(assignment.getId())), HttpStatus.OK);
  }

  private void validatePermission() {
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    Optional<User> userRes = userRepository.findByUsername(username);
    if (userRes.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = userRes.get();
    if(user.getRole() != Roles.TEACHER) throw AuthErrorCode.FORBIDDEN_REQUEST.defaultException();
  }
}
