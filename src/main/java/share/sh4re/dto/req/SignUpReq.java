package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReq {
  @NotBlank(message = "아이디는 필수 입력값입니다")
  String username;

  @NotBlank(message = "이름은 필수 입력값입니다")
  String name;

  @NotBlank(message = "비밀번호는 필수 입력값입니다")
  String password;

  @NotNull(message = "학년은 필수 입력값입니다")
  Long grade;

  @NotNull(message = "반 번호는 필수 입력값입니다")
  Long classNumber;

  @NotNull(message = "학생 번호는 필수 입력값입니다")
  Long studentNumber;
}
