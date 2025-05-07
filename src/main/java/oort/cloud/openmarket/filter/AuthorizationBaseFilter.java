package oort.cloud.openmarket.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.common.enums.CommonEnums;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.exception.response.ApiExceptionResponse;
import oort.cloud.openmarket.user.enums.UserRole;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public abstract class AuthorizationBaseFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    protected AuthorizationBaseFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private Optional<AccessTokenPayload> getAccessPayload(HttpServletRequest request){
        return Optional.ofNullable(
                (AccessTokenPayload) request.getAttribute(CommonEnums.ACCESS_TOKEN_PAYLOAD.getValue())
        );
    }

    protected Optional<UserRole> getUserRole(HttpServletRequest request){
        return getAccessPayload(request).map(AccessTokenPayload::getUserRole);
    }

    protected void writeUnauthorizedResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                objectMapper.writeValueAsString(ApiExceptionResponse.of(errorType))
        );
    }
}
