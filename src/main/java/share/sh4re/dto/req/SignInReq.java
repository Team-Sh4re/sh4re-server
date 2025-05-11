package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInReq {
  @NotBlank(message = "이름은 필수 입력값입니다")
  String name;

  @NotBlank(message = "비밀번호는 필수 입력값입니다")
  String password;
}
