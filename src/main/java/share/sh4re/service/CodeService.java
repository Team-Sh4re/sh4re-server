package share.sh4re.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import share.sh4re.domain.Code;
import share.sh4re.domain.User;
import share.sh4re.dto.req.CreateCodeReq;
import share.sh4re.dto.res.CreateCodeRes;
import share.sh4re.dto.res.CreateCodeRes.CreateCodeResData;
import share.sh4re.exceptions.errorcode.UserErrorCode;
import share.sh4re.repository.CodeRepository;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class CodeService {
  private final CodeRepository codeRepository;
  private final UserRepository userRepository;

  public ResponseEntity<CreateCodeRes> createCode(CreateCodeReq createCodeReq) {
    Code newCode = new Code();
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    Optional<User> userRes = userRepository.findByUsername(username);
    if(userRes.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = userRes.get();
    newCode.update(
        createCodeReq.getTitle(),
        "description",
        createCodeReq.getCode(),
        createCodeReq.getField(),
        user
    );
    codeRepository.save(newCode);
    return new ResponseEntity<>(new CreateCodeRes(true, new CreateCodeResData(0L)), HttpStatus.OK);
  }
}
