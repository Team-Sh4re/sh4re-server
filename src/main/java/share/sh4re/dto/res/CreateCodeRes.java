package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.CreateCodeRes.CreateCodeResData;

public class CreateCodeRes extends BaseRes<CreateCodeResData> {
  public CreateCodeRes(boolean ok, CreateCodeRes.CreateCodeResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class CreateCodeResData {
    private Long id;
  }
}
