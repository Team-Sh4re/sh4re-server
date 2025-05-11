package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.SignInRes.SignInResData;

public class SignInRes extends BaseRes<SignInResData> {

  public SignInRes(boolean ok, SignInResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class SignInResData {

    private String accessToken;
    private String refreshToken;
  }
}
