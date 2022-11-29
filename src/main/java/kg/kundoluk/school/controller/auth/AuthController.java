package kg.kundoluk.school.controller.auth;

import kg.kundoluk.school.dto.auth.AuthenticationRequestDto;
import kg.kundoluk.school.endpoint.AuthEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {
    private final AuthEndpoint authEndpoint;

    public AuthController(AuthEndpoint authEndpoint) {
        this.authEndpoint = authEndpoint;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto, HttpServletRequest request) {
        return authEndpoint.loginPassword(requestDto, request);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return authEndpoint.refreshToken(request);
    }

    @PostMapping("login/mobile")
    public ResponseEntity<?> loginMobile(@RequestParam("mobile") String mobile, @RequestParam("smsCode") String smsCode, HttpServletRequest request) {
        return authEndpoint.loginPhone(mobile, smsCode, request);
    }

    @PostMapping("logout")
    public ResponseEntity<?> login(Authentication authentication) {
        return authEndpoint.logout(authentication);
    }
}
