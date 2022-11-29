package kg.kundoluk.school.config;

import kg.kundoluk.school.handler.LoginFailHandler;
import kg.kundoluk.school.handler.LoginSuccessHandler;
import kg.kundoluk.school.security.jwt.JwtTokenFilter;
import kg.kundoluk.school.security.jwt.JwtUserDetailsService;
import kg.kundoluk.school.security.sms.SmsAuthenticationConfig;
import kg.kundoluk.school.security.sms.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Security configuration class for JWT based Spring Security application.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_ENDPOINT = "/api/v1/auth/**";
    private static final String SOCKET_ENDPOINT = "/socket/**";
    private static final String CARD_ENDPOINT = "/api/v1/card-attendance/**";
    private static final String ACTUATOR_ENDPOINT = "/actuator/**";
    private static final String LANGUAGE_ENDPOINT = "/api/web/v1/language";
    private static final String REGION_ENDPOINT = "/api/web/v1/region";
    private static final String RAYON_ENDPOINT = "/api/web/v1/rayon";
    private static final String SCHOOL_ENDPOINT = "/api/web/v1/school/base-info";
    private static final String SCHOOL_CLASS_ENDPOINT = "/api/web/v1/class/interface/school/*";
    private static final String SMS_CODE_ENDPOINT = "/api/v1/sms/**";
    private static final String SMS_LOGIN_ENDPOINT = "/login/mobile/**";
    private static final String APPLICATION_CREATE_ENDPOINT = "/api/web/v1/application/form/create";
    private static final String APPLICATION_CHECK_ENDPOINT = "/api/web/v1/application/form/check";
    private static final String WHITELIST_ENDPOINT = "/api/white-list/**";
    private static final String SCHEDULER_IP = "62.75.138.199";

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtTokenFilter tokenAuthenticationFilter;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .permitAll()
                .antMatchers(
                        AUTH_ENDPOINT,
                        SOCKET_ENDPOINT,
                        CARD_ENDPOINT,
                        ACTUATOR_ENDPOINT,
                        LANGUAGE_ENDPOINT,
                        SMS_CODE_ENDPOINT,
                        SMS_LOGIN_ENDPOINT,
                        APPLICATION_CREATE_ENDPOINT,
                        APPLICATION_CHECK_ENDPOINT,
                        REGION_ENDPOINT, RAYON_ENDPOINT,
                        SCHOOL_ENDPOINT,
                        SCHOOL_CLASS_ENDPOINT, WHITELIST_ENDPOINT)
                .permitAll()
                //.antMatchers("/api/white-list/**").hasIpAddress(SCHEDULER_IP)
                .anyRequest()
                .authenticated()
                .and()
                .apply(smsAuthenticationConfig);

        // Add our custom Token based authentication filter
        http
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
