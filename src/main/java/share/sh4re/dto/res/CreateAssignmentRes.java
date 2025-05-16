package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.CreateAssignmentRes.CreateAssignmentResData;

public class CreateAssignmentRes extends BaseRes<CreateAssignmentResData> {

  public CreateAssignmentRes(boolean ok, CreateAssignmentResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class CreateAssignmentResData {
    private Long id;
  }
}
