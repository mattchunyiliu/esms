package kg.kundoluk.school.security.sms;

import kg.kundoluk.school.exception.ValidateCodeException;
import kg.kundoluk.school.service.phoneVerification.PhoneVerificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SmsCodeFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private PhoneVerificationService phoneVerificationService;

    private static final String PHONE_LOGIN_ENDPOINT = "/api/v1/auth/login/mobile";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (StringUtils.equalsIgnoreCase(PHONE_LOGIN_ENDPOINT, httpServletRequest.getRequestURI()) && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
            try {
                validateSmsCode(new ServletWebRequest(httpServletRequest), httpServletRequest);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validateSmsCode(ServletWebRequest servletWebRequest, HttpServletRequest httpServletRequest) throws ServletRequestBindingException, ValidateCodeException {

        String smsCodeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");
        String mobile = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "mobile");

        if (!validateCode(mobile, smsCodeInRequest)){
            throw new ValidateCodeException("The verification code has expired, please resend!");
        }

    }

    private Boolean validateCode(String phone, String code){
        return phoneVerificationService.isValid(phone, code);
    }
}
