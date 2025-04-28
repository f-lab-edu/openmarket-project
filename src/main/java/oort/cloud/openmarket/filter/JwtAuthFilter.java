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
import oort.cloud.openmarket.exception.response.ApiExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtManager jwtManager;
    private final ObjectMapper objectMapper;
    private static final String BEARER = "Bearer ";

    public JwtAuthFilter(JwtManager jwtManager, ObjectMapper objectMapper) {
        this.jwtManager = jwtManager;
        this.objectMapper = objectMapper;
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

    private void writeUnauthorizedResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                objectMapper.writeValueAsString(ApiExceptionResponse.of(errorType))
        );
    }
}
