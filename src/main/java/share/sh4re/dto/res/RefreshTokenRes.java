package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RefreshTokenRes extends BaseRes<RefreshTokenRes.RefreshTokenData> {

  public RefreshTokenRes(boolean ok, RefreshTokenData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class RefreshTokenData {

    private String accessToken;
    private String refreshToken;
  }
}
