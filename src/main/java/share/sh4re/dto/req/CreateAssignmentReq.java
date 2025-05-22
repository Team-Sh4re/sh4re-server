package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAssignmentReq {
  @NotBlank(message = "과제 이름은 필수 입력값입니다.")
  String title;

  String description;
}
