package oort.cloud.openmarket.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.auth.service.TokenService;
import oort.cloud.openmarket.common.enums.CommonEnums;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.user.enums.UserRole;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class SellerAuthorizationFilter extends AuthorizationBaseFilter {

    public SellerAuthorizationFilter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(getUserRole(request).filter(role -> role == UserRole.SELLER).isEmpty()){
            writeUnauthorizedResponse(response, ErrorType.UNAUTHORIZED_ACCESS);
        }

        filterChain.doFilter(request, response);
    }
}
