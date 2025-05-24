package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.domain.Comment;
import share.sh4re.dto.res.CreateCommentRes.CreateCommentResData;

public class CreateCommentRes extends BaseRes<CreateCommentResData> {

  public CreateCommentRes(boolean ok, CreateCommentResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class CreateCommentResData {
    private Comment comment;
  }
}