package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.auth.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface AuthEndpoint {
    ResponseEntity<?> loginPassword(@RequestBody AuthenticationRequestDto requestDto, HttpServletRequest request);
    ResponseEntity<?> loginPhone(String mobile, String code, HttpServletRequest request);
    ResponseEntity<?> refreshToken(HttpServletRequest request);
    ResponseEntity<?> logout(Authentication auth);
}
