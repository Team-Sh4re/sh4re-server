package share.sh4re.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCommentReq {
  @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
  String content;
}
