package share.sh4re.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.req.CreateCommentReq;
import share.sh4re.dto.req.EditCodeReq;
import share.sh4re.dto.req.EditCommentReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.dto.res.CreateCommentRes;
import share.sh4re.dto.res.DeleteCodeRes;
import share.sh4re.dto.res.DeleteCommentRes;
import share.sh4re.dto.res.EditCodeRes;
import share.sh4re.dto.res.EditCommentRes;
import share.sh4re.dto.res.GetAllCodesRes;
import share.sh4re.dto.res.GetCodeRes;
import share.sh4re.dto.res.LikeCodeRes;
import share.sh4re.service.CodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/codes")
public class CodeController {
  private final CodeService codeService;

  @PostMapping
  public ResponseEntity<CreateCodeRes> createCode(@Valid @RequestBody CreateCodeReq createCodeReq){
    return codeService.createCode(createCodeReq);
  }

  @GetMapping
  public ResponseEntity<GetAllCodesRes> getAllCodes(
      @RequestParam(required = false, defaultValue = "1", value = "page") int pageNo,
      @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria,
      @RequestParam(required = false, value = "classNo") Long classNo,
      @RequestParam(required = false, value = "assignmentId") Long assignmentId,
      @RequestParam(required = false, value = "role") String role,
      @RequestParam(required = false, value = "isTeacher") boolean isTeacher
  ){
    return codeService.getAllCodes(pageNo, criteria, classNo, assignmentId, role, isTeacher);
  }

  @GetMapping("/{codeId}")
  public ResponseEntity<GetCodeRes> getCode(@PathVariable String codeId) {
    return codeService.getCode(codeId);
  }

  @DeleteMapping("/{codeId}")
  public ResponseEntity<DeleteCodeRes> deleteCode(@PathVariable String codeId){
    return codeService.deleteCode(codeId);
  }

  @PatchMapping("/{codeId}")
  public ResponseEntity<EditCodeRes> editCode(@RequestBody EditCodeReq editCodeReq, @PathVariable String codeId){
    return codeService.editCode(editCodeReq, codeId);
  }

  @PostMapping("/{codeId}/comment")
  public ResponseEntity<CreateCommentRes> createComment(@Valid @RequestBody CreateCommentReq createCommentReq, @PathVariable String codeId){
    return codeService.createComment(createCommentReq, codeId);
  }

  @PatchMapping("/{codeId}/comment/{commentId}")
  public ResponseEntity<EditCommentRes> editComment(@Valid @RequestBody EditCommentReq editCommentReq, @PathVariable String codeId, @PathVariable String commentId){
    return codeService.editComment(commentId, editCommentReq.getContent());
  }

  @DeleteMapping("/comment/{commentId}")
  public ResponseEntity<DeleteCommentRes> deleteComment(@PathVariable String commentId){
    return codeService.deleteComment(commentId);
  }

  @PostMapping("/{codeId}/like")
  public ResponseEntity<LikeCodeRes> likeCode(@PathVariable String codeId){
    return codeService.likeCode(codeId);
  }

}
