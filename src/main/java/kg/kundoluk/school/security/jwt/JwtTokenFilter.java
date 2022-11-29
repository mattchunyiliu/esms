package kg.kundoluk.school.security.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import kg.kundoluk.school.service.user.UserTrackPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * JWT token filter that handles all HTTP requests to application.
 */

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserTrackPageService service;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(httpServletRequest);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                UsernamePasswordAuthenticationToken auth = jwtTokenProvider.getAuthentication(token);
                /*auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(auth);*/

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
                // SAVE EVERY API REQUEST
                saveTrack(auth, httpServletRequest);
            }
        } catch (ExpiredJwtException ex) {

            String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
            String requestURL = httpServletRequest.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refresh-token")) {
                allowForRefreshToken(ex, httpServletRequest);
            } else
                httpServletRequest.setAttribute("exception", ex);

        } catch (Exception e) {
            httpServletRequest.setAttribute("exception", e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        request.setAttribute("claims", ex.getClaims());

    }

    private void saveTrack(@NotNull Authentication auth, HttpServletRequest request) {

        String remoteAdr = "";
        if (request != null) {
            remoteAdr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAdr == null || "".equals(remoteAdr)) {
                remoteAdr = request.getRemoteAddr();
            }
        }
        assert request != null;
        String url = request.getRequestURL().toString();
        int device = 1; //DEFAULT BROWSER
        if(request.getHeader("User-Agent").contains("Mobile")) {
            device = 2;
        }
        service.action(auth, remoteAdr, url, device);
    }
}
