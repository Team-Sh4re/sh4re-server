package share.sh4re.dto.res;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import share.sh4re.domain.Code;
import share.sh4re.domain.User;
import share.sh4re.dto.res.GetCodeRes.GetCodeResData;

public class GetCodeRes extends BaseRes<GetCodeResData> {
  public GetCodeRes(boolean ok, GetCodeResData data) {
    super(ok, data);
  }

  @Getter
  @AllArgsConstructor
  public static class GetCodeResData {
    private Long id;
    private Long likes;
    private Long views;
    private String code;
    private String title;
    private String description;
    private Code.Fields field;
    private User user;

    public GetCodeResData(Code code) {
      this.id = code.getId();
      this.likes = code.getLikes();
      this.views = code.getViews();
      this.code = code.getCode();
      this.title = code.getTitle();
      this.description = code.getDescription();
      this.field = code.getField();
    }
  }
}
