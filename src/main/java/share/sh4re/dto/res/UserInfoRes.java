package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.UserInfoRes.UserInfoResData;

public class UserInfoRes extends BaseRes<UserInfoResData> {
  public UserInfoRes(boolean ok, UserInfoRes.UserInfoResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class UserInfoResData {
    private Long id;
    private String name;
    private Long classNumber;
    private Long studentNumber;
  }
}
