package share.sh4re.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import share.sh4re.config.JwtConfig;
import share.sh4re.domain.User;
import share.sh4re.dto.SignInReq;
import share.sh4re.dto.SignUpReq;
import share.sh4re.dto.TokenResponse;
import share.sh4re.dto.UserInfoRes;
import share.sh4re.service.UserService;

@RestController
@AllArgsConstructor
public class UserController {
  public UserService userService;
  public JwtConfig jwtConfig;

  @PostMapping("/api/auth/signup")
  public ResponseEntity<String> signUp(@RequestBody SignUpReq signUpReq){
    User user = userService.signUp(signUpReq);
    return new ResponseEntity<>("성공적으로 회원가입이 완료되었습니다.", HttpStatus.OK);
  }

  @PostMapping("/api/auth/signin")
  public ResponseEntity<TokenResponse> signIn(@RequestBody SignInReq signInReq){
    TokenResponse tokenResponse = userService.login(signInReq);
    return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
  }

  @PostMapping("/api/auth/refresh")
  public ResponseEntity<TokenResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    String refreshToken = authorizationHeader.substring(7);
    try {
      TokenResponse tokenResponse = userService.refreshToken(refreshToken);
      return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/api/auth/info")
  public ResponseEntity<UserInfoRes> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    String token = authorizationHeader.substring(7);
    Long userId = jwtConfig.extractClaim(token, claims -> claims.get("id", Long.class));
    User user = userService.findById(userId);
    if (user == null) throw new IllegalArgumentException("User not found");
    UserInfoRes userInfoRes = new UserInfoRes(
        user.getId(),
        user.getName(),
        user.getClassNumber(),
        user.getStudentNumber()
    );
    return new ResponseEntity<>(userInfoRes, HttpStatus.OK);
  }
}
