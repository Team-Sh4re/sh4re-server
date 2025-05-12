package share.sh4re.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import share.sh4re.config.JwtConfig;
import share.sh4re.dto.req.SignInReq;
import share.sh4re.dto.res.RefreshTokenRes;
import share.sh4re.dto.res.SignInRes;
import share.sh4re.dto.req.SignUpReq;
import share.sh4re.dto.res.SignUpRes;
import share.sh4re.dto.res.MyInfoRes;
import share.sh4re.dto.res.UserInfoRes;
import share.sh4re.service.UserService;

@RestController
@AllArgsConstructor
public class UserController {
  public UserService userService;
  public JwtConfig jwtConfig;

  @PostMapping("/api/auth/signup")
  public ResponseEntity<SignUpRes> signUp(@Valid @RequestBody SignUpReq signUpReq){
    return userService.signUp(signUpReq);
  }

  @PostMapping("/api/auth/signin")
  public ResponseEntity<SignInRes> signIn(@Valid @RequestBody SignInReq signInReq){
    return userService.login(signInReq);
  }

  @PostMapping("/api/auth/refresh")
  public ResponseEntity<RefreshTokenRes> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
    return userService.refreshToken(authorizationHeader);
  }

  @GetMapping("/api/auth/info")
  public ResponseEntity<MyInfoRes> getMyInfo(@RequestHeader("Authorization") String authorizationHeader) {
    return userService.getMyInfo(authorizationHeader);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<UserInfoRes> getUserInfo(@PathVariable Long userId) {
    return userService.getUserInfo(userId);
  }
}