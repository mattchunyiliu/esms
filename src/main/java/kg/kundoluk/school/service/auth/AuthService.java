package kg.kundoluk.school.service.auth;

import kg.kundoluk.school.dto.auth.AuthenticationRequestDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    Authentication authenticate(AuthenticationRequestDto requestDto);
    Authentication authenticateByCode(String mobile);
}
