package share.sh4re.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import share.sh4re.dto.res.DeleteCodeRes.DeleteCodeResData;

@Getter
@Setter
public class DeleteCodeRes extends BaseRes<DeleteCodeResData> {
  public DeleteCodeRes(boolean ok, DeleteCodeResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class DeleteCodeResData {
    private Long id;
  }
}
