package nutech.awan.ppob;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.RequiredTypeException;
import nutech.awan.ppob.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class JWTUtilTest {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    void testCreate() {

        String token = JWTUtil.BEARER_TOKEN_PREFIX + jwtUtil.createToken("yuyun", Duration.ofHours(2).toMillis());

        System.out.println(token);

        Claims claims = jwtUtil.parseBearerToken(token);

        Assertions.assertThrows(MalformedJwtException.class, () -> {
            jwtUtil.parseBearerToken("Bearer dasdwd");
        });

        Assertions.assertThrows((ExpiredJwtException.class), () -> {
            jwtUtil.parseBearerToken("Bearer " + jwtUtil.createToken("yuyun", -Duration.ofHours(2).toMillis()));
        });

        Assertions.assertThrows((RequiredTypeException.class), () -> {
            jwtUtil.parseBearerToken(jwtUtil.createToken("yuyun", Duration.ofHours(2).toMillis()));
        });


        System.out.println(claims.getSubject());

    }
}
