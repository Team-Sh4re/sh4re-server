package share.sh4re.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import share.sh4re.config.JwtConfig;
import share.sh4re.domain.User;
import share.sh4re.dto.req.SignInReq;
import share.sh4re.dto.res.RefreshTokenRes;
import share.sh4re.dto.res.SignInRes;
import share.sh4re.dto.req.SignUpReq;
import share.sh4re.dto.res.SignUpRes;
import share.sh4re.dto.res.UserInfoRes;
import share.sh4re.dto.res.UserInfoRes.UserInfoResData;
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
        true,
        new UserInfoResData(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getClassNumber(),
            user.getStudentNumber()
        )
    );
    return new ResponseEntity<>(userInfoRes, HttpStatus.OK);
  }
}