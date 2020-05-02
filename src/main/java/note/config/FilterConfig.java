package note.config;

import note.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestFilter> tokenFilter() {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns(
                "/user/*", "/note/*", "/record/*", "/share/*", "/sync/*"
        );
        registration.setName("tokenFilter");
        registration.setOrder(1);
        return registration;
    }
}