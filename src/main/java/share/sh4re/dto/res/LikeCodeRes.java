package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.LikeCodeRes.LikeCodeResData;

public class LikeCodeRes extends BaseRes<LikeCodeResData> {

  public LikeCodeRes(boolean ok, LikeCodeResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class LikeCodeResData {
    private Long id;
  }
}