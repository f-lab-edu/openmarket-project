package oort.cloud.openmarket.config;

import oort.cloud.openmarket.filter.JwtAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> authFilterFilterRegistrationBean(JwtAuthFilter jwtAuthFilter){
        FilterRegistrationBean<JwtAuthFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(jwtAuthFilter);
        frb.addUrlPatterns("/v1/user/*");
        frb.addUrlPatterns("/v1/auth/logout");
        frb.addUrlPatterns("/v1/products/*");
        return frb;
    }
}
