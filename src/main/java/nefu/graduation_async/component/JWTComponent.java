package nefu.graduation_async.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import nefu.graduation_async.dto.Code;
import nefu.graduation_async.exception.XException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
public class JWTComponent {
    @Value("${my.key}")
    private String key;
    private Algorithm algorithm;

    @PostConstruct
    private void init()
    {
        algorithm=Algorithm.HMAC256(key);
    }
    public String encode(Map<String,Object> map){
        LocalDateTime time=LocalDateTime.now().plusMonths(1);
        //过期时间
        var str=JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
        return encodePos(str);
    }
    public Mono<DecodedJWT> decode(String token){
        try{
            DecodedJWT decodedJWT=JWT.require(algorithm).build().verify(decodePos(token));
           return Mono.just(decodedJWT);
        }catch (TokenExpiredException | SignatureVerificationException | JWTDecodeException e) {
            Code code = Code.FORBIDDEN;
            if (e instanceof TokenExpiredException) {
                code = Code.TOKEN_EXPIRED;
            }
            return Mono.error(XException.builder().code(code).build());
        }
    }
    private final int POS = 37;
    private String encodePos(String str) {
        return new StringBuilder(str).insert(POS, "W").toString();
    }
    private String decodePos(String str) {
        return new StringBuilder(str).deleteCharAt(POS).toString();
    }

}
