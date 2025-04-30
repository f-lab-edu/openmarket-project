package oort.cloud.openmarket.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.auth.utils.jwt.JwtManager;
import oort.cloud.openmarket.common.enums.CommonEnums;
import oort.cloud.openmarket.exception.auth.AuthenticationException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthFilter extends AuthorizationBaseFilter {
    private final JwtManager jwtManager;
    private static final String BEARER = "Bearer ";

    public JwtAuthFilter(JwtManager jwtManager, ObjectMapper objectMapper) {
        super(objectMapper);
        this.jwtManager = jwtManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearer == null || !bearer.startsWith(BEARER)){
            writeUnauthorizedResponse(response, ErrorType.UNAUTHORIZED_ACCESS);
            return;
        }

        try {
            String accessToken = bearer.substring(BEARER.length());
            AccessTokenPayload accessTokenPayload = jwtManager.getAccessTokenPayload(accessToken);
            request.setAttribute(CommonEnums.ACCESS_TOKEN_PAYLOAD.getValue(), accessTokenPayload);
        }catch (AuthenticationException e){
            writeUnauthorizedResponse(response, e.getErrorType());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
