package share.sh4re.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import share.sh4re.config.JwtConfig;
import share.sh4re.domain.User;
import share.sh4re.dto.SignInReq;
import share.sh4re.dto.SignUpReq;
import share.sh4re.dto.TokenResponse;
import share.sh4re.exceptions.errorcode.UserErrorCode;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final JwtConfig jwtConfig;

  public User signUp(SignUpReq signUpReq){
    validateUserInput(
        signUpReq.getName(),
        signUpReq.getPassword(),
        signUpReq.getClassNumber(),
        signUpReq.getStudentNumber()
    );
    if (userRepository.findByName(signUpReq.getName()).isPresent()) throw UserErrorCode.USERNAME_ALREADY_EXISTS.defaultException();
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
      String token = jwtConfig.generateToken(user);
      String refreshToken = jwtConfig.generateRefreshToken(user);
      return new TokenResponse(token, refreshToken);
    } else throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
  }

  public TokenResponse refreshToken(String refreshToken) {
    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new IllegalArgumentException("Refresh token is required");
    }

    if (!jwtConfig.validateRefreshToken(refreshToken)) {
      throw new IllegalArgumentException("Invalid or expired refresh token");
    }

    String newAccessToken = jwtConfig.generateTokenFromRefreshToken(refreshToken);
    return new TokenResponse(newAccessToken, refreshToken);
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
