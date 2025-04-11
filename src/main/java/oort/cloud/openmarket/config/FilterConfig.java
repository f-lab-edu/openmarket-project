package oort.cloud.openmarket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import oort.cloud.openmarket.filter.TokenFilter;
import oort.cloud.openmarket.auth.jwt.JwtComponent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TokenFilter> authFilterFilterRegistrationBean(JwtComponent jwtComponent, ObjectMapper objectMapper){
        FilterRegistrationBean<TokenFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new TokenFilter(jwtComponent, objectMapper));
        frb.addUrlPatterns("/v1/user/*");
        frb.addUrlPatterns("/v1/auth/logout");
        frb.addUrlPatterns("/v1/auth/refresh-token");
        return frb;
    }
}
