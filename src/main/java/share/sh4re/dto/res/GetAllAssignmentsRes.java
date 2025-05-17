package share.sh4re.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.domain.Assignment;
import share.sh4re.dto.res.GetAllAssignmentsRes.GetAllAssignmentsResData;

public class GetAllAssignmentsRes  extends BaseRes<GetAllAssignmentsResData>{
  public GetAllAssignmentsRes(boolean ok, GetAllAssignmentsResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class GetAllAssignmentsResData {
    @JsonIgnoreProperties({"codeList"})
    private List<Assignment> assignments;
  }
}
