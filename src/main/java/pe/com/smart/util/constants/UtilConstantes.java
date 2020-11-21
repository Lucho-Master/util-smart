package pe.com.smart.util.constants;

import org.springframework.stereotype.Component;

@Component
public class UtilConstantes {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json, charset=UTF-8";

    //Header Authorization
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";

    //Tipo token
    public static final short TIPO_TOKEN_SIN_EXPIRATION = 0;
    public static final short TIPO_TOKEN_EXPIRA_REDIRECCIONA = 1;
    public static final short TIPO_TOKEN_EXPIRA_NOTIFICA = 2;
    public static final short TIPO_TOKEN_TOKEN_APP_MOVIL = 3;

    //Mensajes filtro java
    public static final String VALIDATE_TOKEN_ALGORITMO_INVALIDO = "Algoritmo invalido";
    public static final String VALIDATE_TOKEN_FIRMA_INVALIDA = "Firma invalida";
    public static final String VALIDATE_TOKEN_TOKEN_EXPIRADO = "Token expirado";
    public static final String VALIDATE_TOKEN_CLAIM_INVALIDO = "Claim invalido";

    //Mensajes genericos
    public static final String NO_HEADER_AUTHORIZATION = "Header Authorization es obligatorio";
    public static final String NO_EXISTS_SERVICES = "El usuario no tiene servicios asignados";
    public static final String NO_START_WITH_PREFIX_BEARER = "El header Authorozation no empieza con el prefijo Bearer";
    public static final String BAD_FORMAT_HEADER_AUTHORIZATION = "El Header Authorization no cumple con el formato requerido";

    //errores generales - 1
    public static int GENERICO_GENERAL = 5001000;
    public static int GENERAL_TOKEN_EXPIRADO = 5001001;
    public static int GENERAL_TOKEN_INVALIDO = 5001002;
    public static int GENERAL_PARTICULAR_1 = 5001003;
    public static int GENERAL_PARTICULAR_2 = 5001004;

    //errores msseguridad - 5
    public static int GENERICO_SEGURIDAD = 5005000;
    public static int SEGURIDAD_USUARIO = 5005110;
    public static int SEGURIDAD_USUARIO_ROL = 5005120;

}
