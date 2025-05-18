package share.sh4re.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCodeReq {
  String title;
  String description;
  String code;
  Long assignmentId;
}
