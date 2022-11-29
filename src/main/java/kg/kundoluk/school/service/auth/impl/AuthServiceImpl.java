package kg.kundoluk.school.service.auth.impl;

import kg.kundoluk.school.dto.auth.AuthenticationRequestDto;
import kg.kundoluk.school.security.sms.SmsAuthenticationToken;
import kg.kundoluk.school.service.auth.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(AuthenticationRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername().trim(), requestDto.getPassword().trim()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public Authentication authenticateByCode(String mobile) {
        Authentication authentication = authenticationManager.authenticate(new SmsAuthenticationToken(mobile));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
