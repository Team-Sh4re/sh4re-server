package share.sh4re.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import share.sh4re.config.JwtConfig;
import share.sh4re.domain.User;
import share.sh4re.dto.req.SignInReq;
import share.sh4re.dto.res.MyInfoRes;
import share.sh4re.dto.res.MyInfoRes.MyInfoResData;
import share.sh4re.dto.res.RefreshTokenRes;
import share.sh4re.dto.res.RefreshTokenRes.RefreshTokenData;
import share.sh4re.dto.res.SignInRes;
import share.sh4re.dto.res.SignInRes.SignInResData;
import share.sh4re.dto.req.SignUpReq;
import share.sh4re.dto.res.SignUpRes;
import share.sh4re.dto.res.UserInfoRes;
import share.sh4re.dto.res.UserInfoRes.UserInfoResData;
import share.sh4re.exceptions.errorcode.AuthErrorCode;
import share.sh4re.exceptions.errorcode.UserErrorCode;
import share.sh4re.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final JwtConfig jwtConfig;

  public ResponseEntity<SignUpRes> signUp(SignUpReq signUpReq){
    if (userRepository.findByUsername(signUpReq.getUsername()).isPresent()) throw UserErrorCode.USERNAME_ALREADY_EXISTS.defaultException();
    User user = new User();
    user.savePassword(signUpReq.getPassword());
    user.update(signUpReq.getUsername(), signUpReq.getName(), signUpReq.getGrade(), signUpReq.getClassNumber(), signUpReq.getStudentNumber());
    userRepository.save(user);
    return new ResponseEntity<>(new SignUpRes(true, new SignUpRes.SignUpResData(user.getId())), HttpStatus.OK);
  }

  public ResponseEntity<SignInRes> login(SignInReq signInReq){
    Optional<User> res = userRepository.findByUsername(signInReq.getUsername());
    if(res.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = res.get();
    if(user.checkPassword(signInReq.getPassword())) {
      String accessToken = jwtConfig.generateToken(user);
      String refreshToken = jwtConfig.generateRefreshToken(user);
      return new ResponseEntity<>(new SignInRes(true, new SignInResData(accessToken, refreshToken)),
          HttpStatus.OK);
    } else throw UserErrorCode.SIGN_UP_PASSWORD_INCORRECT.defaultException();
  }

  public ResponseEntity<RefreshTokenRes> refreshToken(String refreshToken) {
    if (refreshToken == null || refreshToken.isEmpty()) {
      throw AuthErrorCode.INVALID_TOKEN.defaultException();
    }

    String processedRefreshToken = refreshToken.substring(7);

    jwtConfig.validateRefreshToken(processedRefreshToken);

    String newAccessToken = jwtConfig.generateTokenFromRefreshToken(processedRefreshToken);
    return new ResponseEntity<>(new RefreshTokenRes(true, new RefreshTokenData(newAccessToken, processedRefreshToken)), HttpStatus.OK);
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public ResponseEntity<MyInfoRes> getMyInfo(String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    String token = authorizationHeader.substring(7);
    Long userId = jwtConfig.extractClaim(token, claims -> claims.get("id", Long.class));
    Optional<User> userRes = findById(userId);
    if (userRes.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = userRes.get();
    MyInfoRes myInfoRes = new MyInfoRes(
        true,
        new MyInfoResData(user)
    );
    return new ResponseEntity<>(myInfoRes, HttpStatus.OK);
  }

  public ResponseEntity<UserInfoRes> getUserInfo(Long userId) {
    if(userId == null) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    Optional<User> userRes = findById(userId);
    if (userRes.isEmpty()) throw UserErrorCode.MEMBER_NOT_FOUND.defaultException();
    User user = userRes.get();
    UserInfoRes userInfoRes = new UserInfoRes(
        true,
        new UserInfoResData(user)
    );
    return new ResponseEntity<>(userInfoRes, HttpStatus.OK);
  }
}
