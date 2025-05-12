package share.sh4re.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.dto.res.GetAllCodesRes;
import share.sh4re.dto.res.GetCodeRes;
import share.sh4re.service.CodeService;

@Controller
@AllArgsConstructor
public class CodeController {
  private CodeService codeService;

  @PostMapping("/codes")
  public ResponseEntity<CreateCodeRes> createCode(@RequestBody CreateCodeReq createCodeReq){
    return codeService.createCode(createCodeReq);
  }

  @GetMapping("/codes")
  public ResponseEntity<GetAllCodesRes> getAllCodes(){
    return codeService.getAllCodes();
  }

  @GetMapping("/codes/{codeId}")
  public ResponseEntity<GetCodeRes> getCode(@PathVariable String codeId) {
    return codeService.getCode(codeId);
  }
}
