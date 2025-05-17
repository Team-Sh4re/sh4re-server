package share.sh4re.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.domain.Code;
import share.sh4re.domain.User;

public class UserInfoRes extends BaseRes<UserInfoRes.UserInfoResData> {
  public UserInfoRes(boolean ok, UserInfoRes.UserInfoResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class UserInfoResData {
    private final Long id;
    private final String username;
    private final String name;
    private final Long grade;
    private final Long classNumber;
    private final Long studentNumber;
    @JsonIgnoreProperties({"user"})
    private final List<Code> codeList;

    public UserInfoResData(User user) {
      this.id = user.getId();
      this.username = user.getUsername();
      this.name = user.getName();
      this.grade = user.getGrade();
      this.classNumber = user.getClassNumber();
      this.studentNumber = user.getStudentNumber();
      this.codeList = user.getCodeList();
    }
  }
}
