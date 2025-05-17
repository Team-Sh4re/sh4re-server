package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.UpdateAssignmentRes.UpdateAssignmentResData;

public class UpdateAssignmentRes extends BaseRes<UpdateAssignmentResData> {

  public UpdateAssignmentRes(boolean ok, UpdateAssignmentResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class UpdateAssignmentResData {
    private Long id;
  }
}
