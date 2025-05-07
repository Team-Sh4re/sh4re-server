package share.sh4re.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import share.sh4re.domain.User;
import share.sh4re.dto.SignInReq;
import share.sh4re.dto.SignUpReq;
import share.sh4re.dto.TokenResponse;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public User signUp(SignUpReq signUpReq){
    validateUserInput(
        signUpReq.getName(),
        signUpReq.getPassword(),
        signUpReq.getClassNumber(),
        signUpReq.getStudentNumber()
    );
    if (userRepository.findByName(signUpReq.getName()).isPresent()) throw new IllegalStateException("해당 이름의 유저가 이미 존재합니다.");
    User user = new User();
    user.savePassword(signUpReq.getPassword());
    user.update(signUpReq.getName(), signUpReq.getClassNumber(), signUpReq.getStudentNumber());
    return userRepository.save(user);
  }

  public TokenResponse login(SignInReq signInReq){
    Optional<User> res = userRepository.findByName(signInReq.getName());
    if(res.isEmpty()) throw new IllegalStateException("해당 이름의 유저가 존재하지 않습니다.");
    User user = res.get();
    if(user.checkPassword(signInReq.getPassword())) {
      // jwt 토큰 생성 및 TokenResponse 규격에 맞춰 반환
    } else throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
  }

  public User findById(Long id){
    return userRepository.findById(id).orElse(null);
  }

  public void validateUserInput(String name, String password, Long classNumber, Long studentNumber){
    if(name == null) throw new IllegalArgumentException("name is null");
    if(name.isEmpty()) throw new IllegalArgumentException("name is empty");
    if(password == null) throw new IllegalArgumentException("password is null");
    if(password.isEmpty()) throw new IllegalArgumentException("password is empty");
    if(classNumber == null) throw new IllegalArgumentException("classNumber is null");
    if(studentNumber == null) throw new IllegalArgumentException("studentNumber is null");
  }
}
