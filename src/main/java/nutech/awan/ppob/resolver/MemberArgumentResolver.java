package nutech.awan.ppob.resolver;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import nutech.awan.ppob.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("Authorization");

        if (Objects.isNull(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Kosong");
        }

        Claims claims = jwtUtil.parseBearerToken(token);
        String email = claims.getSubject();

        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Jwt benar namun di DB tidak ada email tersebut"));

        return member;
    }
}
