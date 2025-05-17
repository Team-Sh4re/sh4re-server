package share.sh4re.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.dto.res.DeleteCodeRes;
import share.sh4re.dto.res.GetAllCodesRes;
import share.sh4re.dto.res.GetCodeRes;
import share.sh4re.dto.res.LikeCodeRes;
import share.sh4re.service.CodeService;

@RestController
@RequiredArgsConstructor
public class CodeController {
  private final CodeService codeService;

  @PostMapping("/codes")
  public ResponseEntity<CreateCodeRes> createCode(@RequestBody CreateCodeReq createCodeReq){
    return codeService.createCode(createCodeReq);
  }

  @GetMapping("/codes")
  public ResponseEntity<GetAllCodesRes> getAllCodes(
      @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
      @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria,
      @RequestParam(required = false, value = "classNo") Long classNo,
      @RequestParam(required = false, value = "assignmentId") Long assignmentId
  ){
    return codeService.getAllCodes(pageNo, criteria, classNo, assignmentId);
  }

  @GetMapping("/codes/{codeId}")
  public ResponseEntity<GetCodeRes> getCode(@PathVariable String codeId) {
    return codeService.getCode(codeId);
  }

  @DeleteMapping("/codes/{codeId}")
  public ResponseEntity<DeleteCodeRes> deleteCode(@PathVariable String codeId){
    return codeService.deleteCode(codeId);
  }

  @PostMapping("/codes/{codeId}/like")
  public ResponseEntity<LikeCodeRes> likeCode(@PathVariable String codeId){
    return codeService.likeCode(codeId);
  }
}
