package share.sh4re.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Getter
@Entity
public class User extends Base {
  private String username;

  private String name;

  private String password;

  private Long grade;

  private Long classNumber;

  private Long studentNumber;

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
