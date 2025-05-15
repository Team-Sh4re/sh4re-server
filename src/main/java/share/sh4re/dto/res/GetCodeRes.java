package share.sh4re.dto.res;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
  public static class GetCodeResData {
    private final Long id;
    private final Long likes;
    private final Long views;
    private final String code;
    private final String title;
    private final String description;
    private final Code.Fields field;
    private final User user;

    public GetCodeResData(Code code) {
      this.id = code.getId();
      this.likes = code.getLikes();
      this.views = code.getViews();
      this.code = code.getCode();
      this.title = code.getTitle();
      this.description = code.getDescription();
      this.field = code.getField();
      this.user = code.getUser();
    }
  }
}
