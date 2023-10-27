package nutech.awan.ppob.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("jwt.key")
    private String key;

    public String createToken(String subject, Long validTime) {

        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        Date tokenValidity = new Date(System.currentTimeMillis() + validTime);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims parseToken(String token) {

        JwtParser jwtParser = Jwts.parser().setSigningKey(key);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws.getBody();
    }

    public boolean validateTokenExpired(Claims claims) {
        return claims.getExpiration()
                .after(new Date(
                        System.currentTimeMillis()
                ));
    }

    public boolean validateTokenExpired(Claims claims, Long nowMillis) {
        return claims.getExpiration()
                .after(new Date(
                        nowMillis
                ));
    }

}
