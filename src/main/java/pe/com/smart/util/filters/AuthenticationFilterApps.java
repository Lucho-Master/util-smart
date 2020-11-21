package pe.com.smart.util.filters;

import com.auth0.jwt.exceptions.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pe.com.smart.util.commons.UtilGson;
import pe.com.smart.util.commons.UtilResponse;
import pe.com.smart.util.constants.UtilConstantes;
import pe.com.smart.util.jwt.UtilJwt;
import pe.com.smart.util.models.jwt.UsuarioCredentialsApps;
import pe.com.smart.util.models.response.ResponseJwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilterApps extends BasicAuthenticationFilter  {
    private String sHeaderAuthorizationKey ;
    private String sTokenBearerPreix;

    public AuthenticationFilterApps(AuthenticationManager authManager) {
        super(authManager);
        sHeaderAuthorizationKey = UtilConstantes.AUTHORIZATION;
        sTokenBearerPreix = UtilConstantes.BEARER;
        UtilGson.getInstance();
        UtilJwt.getInstance();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(sHeaderAuthorizationKey);
        UsernamePasswordAuthenticationToken userToken = null;

        if(header == null || header.isEmpty() ) {
           /*UtilResponse.settingHttpServletResponseWithBody(
                    UtilGson.objectToJson(new ResponseJwt(UtilConstantes.GENERICO_GENERAL,
                            UtilConstantes.NO_HEADER_AUTHORIZATION,
                            "")),
                    HttpServletResponse.SC_FORBIDDEN,
                    res);
            return;*/
        } else {
            String[] token = header.split(" ");
            if(token.length < 2) {
                UtilResponse.settingHttpServletResponseWithBody(
                        UtilGson.objectToJson(new ResponseJwt(UtilConstantes.GENERICO_GENERAL,
                                UtilConstantes.BAD_FORMAT_HEADER_AUTHORIZATION,
                                "")),
                        HttpServletResponse.SC_FORBIDDEN,
                        res);
                return;
            }

            if(!token[0].toLowerCase().equals(sTokenBearerPreix.toLowerCase())) {
                UtilResponse.settingHttpServletResponseWithBody(
                        UtilGson.objectToJson(new ResponseJwt(UtilConstantes.GENERICO_GENERAL,
                                UtilConstantes.NO_START_WITH_PREFIX_BEARER,
                                "")),
                        HttpServletResponse.SC_BAD_REQUEST,
                        res);
                return;
            }

            UsuarioCredentialsApps userInfo = BasicAuthentiFilterApps.getUserDetailsFromToken(token[1]);
            if(userInfo.Servicios == null || userInfo.Servicios.size() == 0) {
                userInfo.Servicios = new ArrayList<>();
                userInfo.Servicios.add(0);
            }
            if(userInfo == null || (userInfo.Servicios == null || userInfo.Servicios.size() == 0)) {
                UtilResponse.settingHttpServletResponseWithBody(
                        UtilGson.objectToJson(new ResponseJwt(UtilConstantes.GENERICO_GENERAL,
                                UtilConstantes.NO_EXISTS_SERVICES,
                                "")),
                        HttpServletResponse.SC_UNAUTHORIZED,
                        res);
                return;
            }
            try {
                if(userInfo.TipoToken == UtilConstantes.TIPO_TOKEN_TOKEN_APP_MOVIL) {
                    BasicAuthentiFilterApps.validateToken(token[1], false);
                } else {
                    BasicAuthentiFilterApps.validateToken(token[1], true);
                }
            }catch(Exception ex) {
                String msg = null;
                int idError = UtilConstantes.GENERAL_TOKEN_INVALIDO;
                if(ex instanceof AlgorithmMismatchException) {
                    msg = UtilConstantes.VALIDATE_TOKEN_ALGORITMO_INVALIDO;
                } else if(ex instanceof SignatureVerificationException) {
                    msg = UtilConstantes.VALIDATE_TOKEN_FIRMA_INVALIDA;
                } else if(ex instanceof TokenExpiredException) {
                    msg = UtilConstantes.VALIDATE_TOKEN_TOKEN_EXPIRADO;
                    idError = UtilConstantes.GENERAL_TOKEN_EXPIRADO;
                } else if(ex instanceof InvalidClaimException) {
                    msg = UtilConstantes.VALIDATE_TOKEN_CLAIM_INVALIDO;
                } else {
                    msg = ex.getMessage();
                }

                UtilResponse.settingHttpServletResponseWithBody(
                        UtilGson.objectToJson(new ResponseJwt(idError, msg, ex.getMessage())),
                        HttpServletResponse.SC_UNAUTHORIZED,
                        res);
                return;
            }

            req.setAttribute("userInfo", userInfo);
            userToken = BasicAuthentiFilterApps.getAuthentication(userInfo);
        }
        UsernamePasswordAuthenticationToken authentication = userToken;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

}
