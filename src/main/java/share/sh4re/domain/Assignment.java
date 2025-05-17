package share.sh4re.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Assignment extends Base {
  @NotNull
  private String title;

  @NotNull
  private String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignment")
  private List<Code> codeList = new ArrayList<>();

  public void update(String title, String description){
    if(title != null && !title.isEmpty()) this.title = title;
    if(description != null && !description.isEmpty()) this.description = description;
  }
}
