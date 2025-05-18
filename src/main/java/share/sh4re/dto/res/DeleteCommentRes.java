package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.DeleteCommentRes.DeleteCommentResData;

public class DeleteCommentRes extends BaseRes<DeleteCommentResData> {

  public DeleteCommentRes(boolean ok, DeleteCommentResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class DeleteCommentResData {
    private Long id;
  }
}