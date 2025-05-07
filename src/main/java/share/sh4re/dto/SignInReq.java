package share.sh4re.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInReq {
  String name;
  String password;
}
