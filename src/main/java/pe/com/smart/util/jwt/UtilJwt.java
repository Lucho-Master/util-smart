package pe.com.smart.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UtilJwt {

    private static UtilJwt utilJwt;
    private static ObjectMapper mapper = null;

    private UtilJwt() {

    }

    public static UtilJwt getInstance() {
        if(utilJwt == null) {
            utilJwt = new UtilJwt();
            mapper = new ObjectMapper();
        }
        return utilJwt;
    }

    private static Map<String, Object> getClaim(String token, String key) {
        Map<String, Object> map = null;
        if(token != null && key != null) {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> payload = jwt.getClaims();
            Claim claim = payload.get(key);
            map = claim.asMap();
        }
        return map;
    }

    public static Object deserialize(String token, String key, Class classs){
        Object obj = null;
        if(token != null) {
            Map<String, Object> map = getClaim(token, key);
            if(map != null) {
                obj = mapper.convertValue(map, classs);
            }
        }
        return obj;
    }
}
