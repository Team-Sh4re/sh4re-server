package share.sh4re.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.domain.Assignment;
import share.sh4re.dto.req.CreateAssignmentReq;
import share.sh4re.dto.res.CreateAssignmentRes;
import share.sh4re.dto.res.CreateAssignmentRes.CreateAssignmentResData;
import share.sh4re.dto.res.GetAllAssignmentsRes;
import share.sh4re.dto.res.GetAllAssignmentsRes.GetAllAssignmentsResData;
import share.sh4re.exceptions.errorcode.AssignmentErrorCode;
import share.sh4re.repository.AssignmentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {
  private final AssignmentRepository assignmentRepository;

  public ResponseEntity<CreateAssignmentRes> createAssignment(CreateAssignmentReq createAssignmentReq, String authorizationHeader) {
    if(createAssignmentReq.getName() == null || createAssignmentReq.getName().isEmpty()) throw AssignmentErrorCode.INVALID_ARGUMENT.defaultException();
    Assignment assignment = new Assignment();
    assignment.update(createAssignmentReq.getName());
    assignmentRepository.save(assignment);
    return new ResponseEntity<>(new CreateAssignmentRes(true, new CreateAssignmentResData(assignment.getId())),
        HttpStatus.OK);
  }

  public ResponseEntity<GetAllAssignmentsRes> getAllAssignments() {
    List<Assignment> assignments = assignmentRepository.findAll();
    return new ResponseEntity<>(new GetAllAssignmentsRes(true, new GetAllAssignmentsResData(assignments)), HttpStatus.OK);
  }
}
