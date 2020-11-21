package pe.com.smart.util.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import pe.com.smart.util.commons.UtilCollections;
import pe.com.smart.util.jwt.UtilJwt;
import pe.com.smart.util.models.jwt.GrantedAuthorityCustom;
import pe.com.smart.util.models.jwt.UsuarioCredentialsApps;
import pe.com.smart.util.models.jwt.UsuarioDetailCustom;

import javax.servlet.ServletException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class BasicAuthentiFilterApps {

    private static final String SelfSigned2048_SHA256_Public = "MIIDgzCCAmugAwIBAgIEQhZZATANBgkqhkiG9w0BAQsFADByMQswCQYDVQQGEwJQRTERMA8GA1UECBMIQXJlcXVpcGExETAPBgNVBAcTCEFyZXF1aXBhMRYwFAYDVQQKEw1TbWFydCBSZWFzb25zMQswCQYDVQQLEwJUSTEYMBYGA1UEAxMPUm95IFBlcmV6IFBpbnRvMB4XDTE4MDEyMDA1NTQyNloXDTE5MDEyMDA1NTQyNlowcjELMAkGA1UEBhMCUEUxETAPBgNVBAgTCEFyZXF1aXBhMREwDwYDVQQHEwhBcmVxdWlwYTEWMBQGA1UEChMNU21hcnQgUmVhc29uczELMAkGA1UECxMCVEkxGDAWBgNVBAMTD1JveSBQZXJleiBQaW50bzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIQsKmVDf9dMipHbELq6cXQOaYiuvWLmDW8nFrptFP2DTnsndsBCeyfhlYowJSAwFcKoxWUH0kyvPGU5gUOKfeBfzdZ6bGJpFqrb+DQsQho+aROC5PqUzZNIawhGf/9aKUwbpGrjOD7itL2mtABrOaBHA+YEJnyHdwvAAM8edYCzuxKlwsEutEIw2vRwrFYWHCi2nDxYAsPU22Y8LDYhfQAZ3e1Su5T2JTtbf3r+ZyOr/WWzCQOUiUzIpD6XuNDfcOdj1nwt5sA33L/UOYQA76vr6MfCsXCVsx8SLn/GCjT+Owm9SXY2aOOE44g7eNuUIOVbmATgI59G6RWxThiVPTcCAwEAAaMhMB8wHQYDVR0OBBYEFH5OLWa/piDHphWoUqCNPSmiycD9MA0GCSqGSIb3DQEBCwUAA4IBAQBuzX24FbpiKq3icjM/3Lld3PLFWHqOzBJLF5EU0XznJQk40V2IZFp4ffxuojJiNNnn3gE8fx/VsdiWA5u47242QZ3F2UJPCBorj+an+Y1XPKM8AgBQLCfEUhEUX6xP0XYFF7PuIUYLfx+a53Gu1lBGIZ1ikYXty+msTcBwLGkXOF8Wf/9Y69oF4Wx1Xabx8ZxZhfWgXxfAC5euslqUfswPDshiST0y8OOT0S/oPmFxi7jGY9oPzuIFnM+IOBdhUP5Li52IlDxEWNUXv4xd+BkrZxIB2LNo12s2L+bsiE/02nwOn9EIFhrpQJvtdrLFJvzuHxlgoN9vJUMIzQBfbDjh";
    private static final String issuer = "msseguridadapps";

    public static UsuarioCredentialsApps getUserDetailsFromToken(String authToken) {
        UsuarioCredentialsApps userInfo = null;
        if (authToken != null) {
            userInfo = getInfoToken(authToken);
        }
        return userInfo;
    }

    public static UsuarioCredentialsApps getInfoToken(String token) {
        UsuarioCredentialsApps userInfo = null;
        try {
            userInfo = (UsuarioCredentialsApps)
                    UtilJwt.deserialize(token, "scopes", UsuarioCredentialsApps.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(UsuarioCredentialsApps userInfo)
            throws IOException, ServletException {
        List<GrantedAuthorityCustom> lista = new ArrayList<>();
        boolean aux = UtilCollections.isListEmpty(userInfo.Servicios);
        userInfo.Servicios.forEach( x -> {
            lista.add(new GrantedAuthorityCustom(x.toString()));
        });
        return new UsernamePasswordAuthenticationToken(new UsuarioDetailCustom(userInfo), null, lista);
    }

    public static void validateToken(String token, boolean validateExpiration) throws CertificateException {

        byte encodedCert[] = Base64.getDecoder().decode(SelfSigned2048_SHA256_Public);
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
