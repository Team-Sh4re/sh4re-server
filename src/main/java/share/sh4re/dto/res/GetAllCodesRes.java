package share.sh4re.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.domain.Code;
import share.sh4re.dto.res.GetAllCodesRes.GetAllCodesResData;

public class GetAllCodesRes extends BaseRes<GetAllCodesResData> {
  public GetAllCodesRes(boolean ok, GetAllCodesResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class GetAllCodesResData {
    private List<Code> codes;
    private int totalPages;
  }
}
