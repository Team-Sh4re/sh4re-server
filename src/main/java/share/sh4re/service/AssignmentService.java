package share.sh4re.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.dto.req.CreateAssignmentReq;
import share.sh4re.dto.res.CreateAssignmentRes;
import share.sh4re.dto.res.CreateAssignmentRes.CreateAssignmentResData;
import share.sh4re.repository.AssignmentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {
  private final AssignmentRepository assignmentRepository;

  public ResponseEntity<CreateAssignmentRes> createAssignment(CreateAssignmentReq createAssignmentReq) {
    return new ResponseEntity<>(new CreateAssignmentRes(true, new CreateAssignmentResData(0L)),
        HttpStatus.OK);
  }
}
