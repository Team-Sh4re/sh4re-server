package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import share.sh4re.domain.Code;

@Getter
@Setter
public class CreateCodeReq {
  @NotBlank(message = "제목은 필수 입력값입니다.")
  String title;

  @NotBlank(message = "코드는 필수 입력값입니다.")
  String code;

  @NotBlank(message = "종류는 필수 입력값입니다.")
  Code.Fields field;

  Long assignmentId;
}
