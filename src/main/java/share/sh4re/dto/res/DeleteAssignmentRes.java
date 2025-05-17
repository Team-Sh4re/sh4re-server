package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.DeleteAssignmentRes.DeleteAssignmentResData;
import share.sh4re.dto.res.UpdateAssignmentRes.UpdateAssignmentResData;

public class DeleteAssignmentRes extends BaseRes<DeleteAssignmentResData> {

  public DeleteAssignmentRes(boolean ok, DeleteAssignmentResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class DeleteAssignmentResData {
    private Long id;
  }
}
