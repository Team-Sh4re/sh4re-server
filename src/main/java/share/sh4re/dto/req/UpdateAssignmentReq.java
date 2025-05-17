package share.sh4re.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAssignmentReq {
  @NotNull(message = "과제 ID는 필수 입력값입니다.")
  Long id;
  String title;
  String description;
}
