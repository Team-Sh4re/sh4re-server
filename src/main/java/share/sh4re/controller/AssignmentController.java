package share.sh4re.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import share.sh4re.dto.req.CreateAssignmentReq;
import share.sh4re.dto.res.CreateAssignmentRes;
import share.sh4re.service.AssignmentService;

@RestController
@RequiredArgsConstructor
public class AssignmentController {
  private final AssignmentService assignmentService;

  @PostMapping("/assignments")
  public ResponseEntity<CreateAssignmentRes> createAssignment(@RequestBody CreateAssignmentReq createAssignmentReq, @RequestHeader("Authorization") String authorizationHeader) {
    return assignmentService.createAssignment(createAssignmentReq, authorizationHeader);
  }
}
