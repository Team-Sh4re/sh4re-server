package share.sh4re.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "code_id"})
)
public class Like extends Base {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "code_id", nullable = false)
  private Code code;

  public Like(User user, Code code) {
    this.user = user;
    this.code = code;
  }
}
