package pe.com.smart.util.commons;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import pe.com.smart.util.constants.UtilConstantes;
import pe.com.smart.util.models.response.ResponseJwt;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UtilResponse {

    public static HttpServletResponse settingHttpServletResponse(int HttpStatus, HttpServletResponse res) throws IOException {
        res.setStatus(HttpStatus);
        res.addHeader(HttpHeaders.CONTENT_TYPE, UtilConstantes.APPLICATION_JSON_CHARSET_UTF_8);
        return res;
    }

    public static HttpServletResponse settingHttpServletResponseWithBody(String body, int HttpStatus, HttpServletResponse res) throws IOException {
        res.setStatus(HttpStatus);
        res.addHeader(HttpHeaders.CONTENT_TYPE, UtilConstantes.APPLICATION_JSON_CHARSET_UTF_8);
        res.getWriter().print(body);
        res.getWriter().flush();
        return res;
    }
}
