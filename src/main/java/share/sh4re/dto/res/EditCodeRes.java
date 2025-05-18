package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import share.sh4re.dto.res.EditCodeRes.EditCodeResData;

public class EditCodeRes extends BaseRes<EditCodeResData> {

  public EditCodeRes(boolean ok, EditCodeResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class EditCodeResData {
    private Long id;
  }
}