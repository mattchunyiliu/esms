package kg.kundoluk.school.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Util class that provides methods for generation, validation, etc. of JWT token.
 */
@Component
public class JwtTokenProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer_";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Value("${jwt.token.refresh-expired}")
    private long refreshInMilliseconds;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Authentication authentication) {

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = jwtUser.getAuthorities();

        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"))) {
            claims.put("isInstructor", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            claims.put("isStudent", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_PARENT"))) {
            claims.put("isParent", true);
        }

        return doGenerateToken(claims, jwtUser.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public String doRefreshGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        if (userDetails.isEnabled())
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return null;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER);
        if (bearerToken != null && bearerToken.startsWith(PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
        }

        return false;
    }
}
