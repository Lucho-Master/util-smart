package pe.com.smart.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
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

    public static void validate(String token, boolean validateExpiration, String certicate, String issuer) throws CertificateException {
        byte encodedCert[] = Base64.getDecoder().decode(certicate);
        ByteArrayInputStream inputStream  =  new ByteArrayInputStream(encodedCert);

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)certFactory.generateCertificate(inputStream);
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey)cert.getPublicKey(), null);
        JWTVerifier verifier = validateExpiration ? JWT.require(algorithm)
                .withIssuer(issuer)
                .build() : JWT.require(algorithm)
                .withIssuer(issuer).acceptExpiresAt(999999999)
                .build();
        verifier.verify(token);
    }
}
