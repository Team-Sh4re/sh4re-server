package share.sh4re.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Base {
  @NotNull
  private String content;

  @NotNull
  @ManyToOne
  @JsonIgnoreProperties({"commentList"})
  private Code code;

  @NotNull
  @ManyToOne
  @JsonIgnoreProperties({"codeList"})
  private User author;

  public void update(String content, Code code, User author){
    this.content = content;
    this.code = code;
    this.author = author;
  }

  public void edit(String content){
    if(content != null) this.content = content;
  }
}
