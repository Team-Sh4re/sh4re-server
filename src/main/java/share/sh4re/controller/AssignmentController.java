package share.sh4re.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import share.sh4re.dto.req.CreateAssignmentReq;
import share.sh4re.dto.res.CreateAssignmentRes;
import share.sh4re.dto.res.GetAllAssignmentsRes;
import share.sh4re.dto.res.GetAssignmentRes;
import share.sh4re.service.AssignmentService;

@RestController
@RequiredArgsConstructor
public class AssignmentController {
  private final AssignmentService assignmentService;

  @GetMapping("/assignments")
  public ResponseEntity<GetAllAssignmentsRes> getAllAssignments() {
    return assignmentService.getAllAssignments();
  }

  @PostMapping("/assignments")
  public ResponseEntity<CreateAssignmentRes> createAssignment(@RequestBody CreateAssignmentReq createAssignmentReq, @RequestHeader("Authorization") String authorizationHeader) {
    return assignmentService.createAssignment(createAssignmentReq, authorizationHeader);
  }

  @GetMapping("/assignments/{assignmentId}")
  public ResponseEntity<GetAssignmentRes> getAssignment(@PathVariable String assignmentId) {
    return assignmentService.getAssignment(assignmentId);
  }
}
