package share.sh4re.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import share.sh4re.domain.Code;
import share.sh4re.domain.User;
import share.sh4re.dto.res.MyInfoRes.MyInfoResData;

public class MyInfoRes extends BaseRes<MyInfoResData> {
  public MyInfoRes(boolean ok, MyInfoRes.MyInfoResData data) {
    super(ok, data);
  }

  @Getter
  public static class MyInfoResData {
    private final Long id;
    private final String username;
    private final String name;
    private final Long grade;
    private final Long classNumber;
    private final Long studentNumber;
    private final List<Code> codeList;

    public MyInfoResData(User user) {
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
