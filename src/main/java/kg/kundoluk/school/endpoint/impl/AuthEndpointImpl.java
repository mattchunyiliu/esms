package kg.kundoluk.school.endpoint.impl;

import io.jsonwebtoken.impl.DefaultClaims;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.auth.AuthenticatedUserDto;
import kg.kundoluk.school.dto.auth.AuthenticationRequestDto;
import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.endpoint.AuthEndpoint;
import kg.kundoluk.school.mapper.auth.AuthMapper;
import kg.kundoluk.school.model.enums.LoginType;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.security.jwt.JwtTokenProvider;
import kg.kundoluk.school.security.jwt.JwtUser;
import kg.kundoluk.school.service.auth.AuthService;
import kg.kundoluk.school.service.phoneVerification.PhoneVerificationService;
import kg.kundoluk.school.service.user.UserLoginHistoryService;
import kg.kundoluk.school.service.user.UserSchoolService;
import kg.kundoluk.school.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthEndpointImpl implements AuthEndpoint {
    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthMapper authMapper;
    private final PhoneVerificationService phoneVerificationService;
    private final UserSchoolService userSchoolService;
    private final UserLoginHistoryService userLoginHistoryService;

    public AuthEndpointImpl(AuthService authService, UserService userService, JwtTokenProvider jwtTokenProvider, AuthMapper authMapper, PhoneVerificationService phoneVerificationService, UserSchoolService userSchoolService, UserLoginHistoryService userLoginHistoryService) {
        this.authService = authService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authMapper = authMapper;
        this.phoneVerificationService = phoneVerificationService;
        this.userSchoolService = userSchoolService;
        this.userLoginHistoryService = userLoginHistoryService;
    }

    @Override
    public ResponseEntity<?> loginPassword(AuthenticationRequestDto requestDto, HttpServletRequest request) {
        try {

            String username = requestDto.getUsername().trim();
            // AUTHENTICATE BY LOGIN AND PASSWORD
            Authentication a = authService.authenticate(requestDto);

            User user = userService.findByUsername(username);

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse(false, "User with username: " + username + " NOT FOUND"), HttpStatus.NOT_FOUND);
            }

            // save history
            userLoginHistoryService.create(user, LoginType.LOGIN);
            return authenticatedResponse(user, a, request);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> loginPhone(String mobile, String code, HttpServletRequest request) {
        try {

            //Boolean flag = phoneVerificationService.isValid(mobile, code);
            // AUTHENTICATE BY PHONE NUMBER
            Authentication authentication = authService.authenticateByCode(mobile);

            User user = userService.findByUsername(authentication.getName());

            return authenticatedResponse(user, authentication, request);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        // From the HttpRequest get the claims
        DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenProvider.doRefreshGenerateToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new AuthenticatedUserDto(token));
    }

    @Override
    public ResponseEntity<?> logout(Authentication auth) {
        if (auth == null)
            return new ResponseEntity<>(new ApiResponse(false, "logout fail"), HttpStatus.BAD_REQUEST);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        User user = userService.findByUsername(jwtUser.getUsername());

        userLoginHistoryService.create(user, LoginType.LOGOUT);
        return new ResponseEntity<>(new ApiResponse(true, "logout successfully"), HttpStatus.CREATED);
    }

    private ResponseEntity<?> authenticatedResponse(User user, Authentication authentication,  HttpServletRequest request){

        String token = jwtTokenProvider.createToken(authentication);

        AuthenticatedUserDto authenticatedUserDto = authMapper.toAuthenticatedUserDto(user);
        authenticatedUserDto.setToken(token);
        authenticatedUserDto.setTokenType("Bearer");

        List<UserSchoolDTO> userSchool = userSchoolService.getUserSchool(user.getId());
        authenticatedUserDto.setSchools(userSchool.stream().map(this::schoolDTO).collect(Collectors.toList()));

        return ResponseEntity.ok(authenticatedUserDto);
    }

    private Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    private SchoolDTO schoolDTO(UserSchoolDTO userSchoolDTO){
        return new SchoolDTO() {
            @Override
            public Long getId() {
                return userSchoolDTO.getSchoolId();
            }

            @Override
            public String getName() {
                return userSchoolDTO.getSchoolName();
            }

            @Override
            public Integer getStudyDay() {
                return null;
            }

            @Override
            public String getAddress() {
                return userSchoolDTO.getSchoolAddress();
            }

            @Override
            public String getPhone() {
                return userSchoolDTO.getSchoolPhone();
            }

            @Override
            public Integer getOrganizationType() {
                return null;
            }
        };
    }
}
