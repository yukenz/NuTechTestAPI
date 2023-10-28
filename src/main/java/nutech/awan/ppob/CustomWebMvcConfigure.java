package nutech.awan.ppob;

import nutech.awan.ppob.resolver.MemberArgumentResolver;
import nutech.awan.ppob.resolver.ServicePaymentArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebMvcConfigure implements WebMvcConfigurer {

    @Autowired
    MemberArgumentResolver memberArgumentResolver;

    @Autowired
    ServicePaymentArgumentResolver servicePaymentArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
        resolvers.add(servicePaymentArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
