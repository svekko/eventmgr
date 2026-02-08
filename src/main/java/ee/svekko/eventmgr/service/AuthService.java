package ee.svekko.eventmgr.service;

import ee.svekko.eventmgr.error.ApiError;
import ee.svekko.eventmgr.exception.ApiException;
import ee.svekko.eventmgr.web.dto.request.LoginRequest;
import ee.svekko.eventmgr.web.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        if (!authenticationResponse.isAuthenticated()) {
            throw ApiException.unauthorized(ApiError.UNAUTHORIZED);
        }

        return LoginResponse.builder()
            .token(jwtService.generateToken(loginRequest.getUsername()))
            .build();
    }
}
