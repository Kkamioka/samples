package to.msn.wings.quickmaster.configs;

//import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import to.msn.wings.quickmaster.common.LoggingInterceptor;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor())
          .addPathPatterns("/ctrl/**");
    }
}
