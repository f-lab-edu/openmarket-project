package oort.cloud.openmarket.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oort.cloud.openmarket.auth.jwt.JwtComponent;
import oort.cloud.openmarket.exception.AuthServiceException;
import oort.cloud.openmarket.exception.ErrorType;
import oort.cloud.openmarket.exception.response.AuthExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TokenFilter extends GenericFilter {
    private final JwtComponent jwtComponent;
    private final ObjectMapper objectMapper;
    private static final String BEARER = "Bearer ";

    public TokenFilter(JwtComponent jwtComponent, ObjectMapper objectMapper) {
        this.jwtComponent = jwtComponent;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String bearer = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearer == null || !bearer.startsWith(BEARER)){
            writeUnauthorizedResponse((HttpServletResponse) response, ErrorType.UNAUTHORIZED_ACCESS);
            return;
        }

        try {
            String accessToken = bearer.substring(BEARER.length());
            jwtComponent.validateToken(accessToken);
        }catch (AuthServiceException e){
            writeUnauthorizedResponse((HttpServletResponse) response, e.getErrorType());
            return;
        }
        chain.doFilter(request, response);
    }

    private void writeUnauthorizedResponse(HttpServletResponse response, ErrorType e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                objectMapper.writeValueAsString(new AuthExceptionResponse(e))
        );
    }
}
