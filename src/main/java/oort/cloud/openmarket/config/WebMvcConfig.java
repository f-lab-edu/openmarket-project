package oort.cloud.openmarket.config;

import oort.cloud.openmarket.config.resolver.AccessTokenArgumentResolver;
import oort.cloud.openmarket.config.resolver.CustomPageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessTokenArgumentResolver());
        resolvers.add(new CustomPageableHandlerMethodArgumentResolver());
    }
}
