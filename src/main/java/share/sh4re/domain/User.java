package share.sh4re.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Getter
@Entity
public class User extends Base {
  public enum Roles {
    STUDENT, TEACHER
  }

  @NotNull
  private String username;

  @NotNull
  private String name;

  @NotNull
  @JsonIgnore
  private String password;

  @NotNull
  private Long grade;

  @NotNull
  private Long classNumber;

  @NotNull
  private Long studentNumber;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Roles role = Roles.STUDENT;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties({"user"})
  @OrderBy("createdAt DESC")
  private List<Code> codeList;

  public void savePassword(String newPassword){
    if(newPassword == null) throw new IllegalArgumentException("newPassword is null");
    if(newPassword.isEmpty()) throw new IllegalArgumentException("newPassword is empty");
    password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
  }

  public boolean checkPassword(String password){
    return BCrypt.checkpw(password, this.password);
  }

  public void update(String username, String name, Long grade, Long classNumber, Long studentNumber){
    this.username = username;
    this.name = name;
    this.grade = grade;
    this.classNumber = classNumber;
    this.studentNumber = studentNumber;
  }
}
