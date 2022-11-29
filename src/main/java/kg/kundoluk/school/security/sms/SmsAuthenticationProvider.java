package kg.kundoluk.school.security.sms;

import kg.kundoluk.school.security.jwt.JwtUserDetailsService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class SmsAuthenticationProvider implements AuthenticationProvider {

    private JwtUserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;

        // Determine phone
        String phone = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                : (String) authenticationToken.getPrincipal();

        UserDetails user = userDetailService.loadUserByPhone(phone);

        if (user == null) {
            throw new InternalAuthenticationServiceException("The user corresponding to the mobile number was not found");
        }

        check(user);

        return createSuccessSmsAuthentication(authenticationToken, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public JwtUserDetailsService getUserDetailService() {
        return userDetailService;
    }

    public void setUserDetailService(JwtUserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    protected Authentication createSuccessSmsAuthentication(SmsAuthenticationToken authenticationToken, UserDetails user){

        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }
    }
}
