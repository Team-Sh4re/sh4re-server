package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentReq {
  @NotBlank(message = "내용은 필수 입력값입니다.")
  String content;
}
