package oort.cloud.openmarket.config;

import oort.cloud.openmarket.config.resolver.AccessTokenArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessTokenArgumentResolver());
        addPageableHandlerArgumentResolver(resolvers);
    }

    /**
     * Pageble 커스텀 설정
     * 페이지는 1부터 시작
     * 내부 페이지는 0부터 시작
     * 최대 페이지 개수 100개
     * @param resolvers
     */
    private void addPageableHandlerArgumentResolver(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        resolver.setFallbackPageable(PageRequest.of(0, 10));
        resolver.setMaxPageSize(100);
        resolvers.add(resolver);
    }
}
