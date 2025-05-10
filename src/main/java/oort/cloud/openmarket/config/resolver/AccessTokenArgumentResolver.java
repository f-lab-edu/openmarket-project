package oort.cloud.openmarket.config.resolver;

import jakarta.servlet.http.HttpServletRequest;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.auth.annotations.AccessToken;
import oort.cloud.openmarket.common.enums.CommonEnums;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AccessTokenPayload.class)
                && parameter.hasParameterAnnotation(AccessToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute(CommonEnums.ACCESS_TOKEN_PAYLOAD.getValue());
    }
}
