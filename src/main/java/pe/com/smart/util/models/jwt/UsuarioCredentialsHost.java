package pe.com.smart.util.models.jwt;

public class UsuarioCredentialsHost extends UsuarioCredentials {

    public long IdInvitado;
    public String DocumentoInvitado;

    public long getIdInvitado() {
        return IdInvitado;
    }

    public void setIdInvitado(long idInvitado) {
        IdInvitado = idInvitado;
    }

    public String getDocumentoInvitado() {
        return DocumentoInvitado;
    }

    public void setDocumentoInvitado(String documentoInvitado) {
        DocumentoInvitado = documentoInvitado;
    }
}
