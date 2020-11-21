package pe.com.smart.util.models.jwt;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityCustom implements GrantedAuthority {

    private String rol;

    public GrantedAuthorityCustom(String rol) {
        this.rol = rol;
    }

    @Override
    public String getAuthority() {
        return this.rol;
    }
}
