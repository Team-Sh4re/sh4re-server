package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.SignUpRes.SignUpResData;

public class SignUpRes extends BaseRes<SignUpResData> {

  public SignUpRes(boolean ok, SignUpResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class SignUpResData {
    private Long id;
  }
}
