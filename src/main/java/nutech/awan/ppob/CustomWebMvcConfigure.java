package nutech.awan.ppob;

import nutech.awan.ppob.resolver.MemberArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public class CustomWebMvcConfigure implements WebMvcConfigurer {

    @Autowired
    MemberArgumentResolver memberArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
