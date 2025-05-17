package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.domain.Assignment;
import share.sh4re.dto.res.GetAssignmentRes.GetCodesByAssignmentIdResData;

public class GetAssignmentRes extends BaseRes<GetCodesByAssignmentIdResData>{
  public GetAssignmentRes(boolean ok, GetCodesByAssignmentIdResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class GetCodesByAssignmentIdResData {
    private Assignment assignment;
  }
}
