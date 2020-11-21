package pe.com.smart.util.models.response;

public class ResponseJwt {
    private int idError;
    private String mensaje;
    private String traza;

    public ResponseJwt() {

    }

    public ResponseJwt(int idError, String mensaje, String traza) {
        this.idError = idError;
        this.mensaje = mensaje;
        this.traza = traza;
    }

    public int getIdError() {
        return idError;
    }

    public void setIdError(int idError) {
        this.idError = idError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTraza() {
        return traza;
    }

    public void setTraza(String traza) {
        this.traza = traza;
    }
}
