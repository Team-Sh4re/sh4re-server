package share.sh4re.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReq {
  String name;
  String password;
  Long classNumber;
  Long studentNumber;
}
