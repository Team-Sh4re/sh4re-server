package share.sh4re.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.domain.Code;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.repository.CodeRepository;

@Service
@Transactional
@AllArgsConstructor
public class CodeService {
  private final CodeRepository codeRepository;

  public ResponseEntity<CreateCodeRes> createCode(CreateCodeReq createCodeReq) {
    Code newCode = new Code();
//    newCode.update(
//        createCodeReq.getTitle(),
//        "description",
//        createCodeReq.getCodeFile(), // file
//        createCodeReq.getField(),
//        // user
//    );
    return new ResponseEntity<>(new CreateCodeRes(true, null), null);
  }
}
