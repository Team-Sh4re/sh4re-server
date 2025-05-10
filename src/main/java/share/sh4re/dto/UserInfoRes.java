package share.sh4re.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoRes {
  private Long id;
  private String name;
  private Long classNumber;
  private Long studentNumber;
}
