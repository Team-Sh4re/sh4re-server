package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import share.sh4re.domain.Code;

@Getter
@Setter
public class CreateCodeReq {
  @NotBlank(message = "제목은 필수 입력값입니다.")
  String title;

  String description;

  @NotBlank(message = "코드는 필수 입력값입니다.")
  String code;

  @NotNull(message = "종류는 필수 입력값입니다.")
  Code.Fields field;

  @NotNull(message = "설명 자동 생성은 필수 입력값입니다.")
  boolean generateDescription;

  Long assignmentId;
}
