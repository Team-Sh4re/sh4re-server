package share.sh4re.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.service.CodeService;

@Controller
public class CodeController {
  private CodeService codeService;

  @PostMapping("/codes")
  public ResponseEntity<CreateCodeRes> createCode(@ModelAttribute CreateCodeReq createCodeReq){
    return codeService.createCode(createCodeReq);
  }
}
