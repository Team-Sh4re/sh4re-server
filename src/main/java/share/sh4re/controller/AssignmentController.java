package share.sh4re.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import share.sh4re.dto.req.CreateAssignmentReq;
import share.sh4re.dto.req.DeleteAssignmentReq;
import share.sh4re.dto.req.UpdateAssignmentReq;
import share.sh4re.dto.res.CreateAssignmentRes;
import share.sh4re.dto.res.DeleteAssignmentRes;
import share.sh4re.dto.res.GetAllAssignmentsRes;
import share.sh4re.dto.res.UpdateAssignmentRes;
import share.sh4re.service.AssignmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {
  private final AssignmentService assignmentService;

  @GetMapping
  public ResponseEntity<GetAllAssignmentsRes> getAllAssignments() {
    return assignmentService.getAllAssignments();
  }

  @PostMapping
  public ResponseEntity<CreateAssignmentRes> createAssignment(@Valid @RequestBody CreateAssignmentReq createAssignmentReq) {
    return assignmentService.createAssignment(createAssignmentReq);
  }

  @PatchMapping
  public ResponseEntity<UpdateAssignmentRes> updateAssignment(@Valid @RequestBody UpdateAssignmentReq updateAssignmentReq) {
    return assignmentService.updateAssignment(updateAssignmentReq);
  }

  @DeleteMapping
  public ResponseEntity<DeleteAssignmentRes> deleteAssignment(@Valid @RequestBody DeleteAssignmentReq deleteAssignmentRes) {
    return assignmentService.deleteAssignment(deleteAssignmentRes);
  }
}
