package pe.com.smart.util.models.jwt;

import java.util.List;

public class UsuarioCredentials {

    public long IdUsuario;
    public String Usuario;
    public short TipoToken;
    public long IdEntidadPersona;
    public long IdEntidadEmpresa;
    public String DocumentoEmpresa;
    public String DocumentoPersona;
    public List<Integer> Sucursales;
    public List<Integer> Servicios;

}
