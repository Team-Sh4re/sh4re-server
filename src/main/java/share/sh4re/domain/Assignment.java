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
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignment")
  private List<Code> codeList = new ArrayList<>();

  public void update(String name){
    this.name = name;
  }
}
