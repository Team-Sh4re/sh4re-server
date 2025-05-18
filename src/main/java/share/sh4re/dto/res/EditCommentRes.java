package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.EditCommentRes.EditCommentResData;

public class EditCommentRes extends BaseRes<EditCommentResData> {

  public EditCommentRes(boolean ok, EditCommentResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class EditCommentResData {
    private Long id;
  }
}