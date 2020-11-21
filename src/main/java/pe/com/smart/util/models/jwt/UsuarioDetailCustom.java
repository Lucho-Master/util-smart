package pe.com.smart.util.models.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetailCustom implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private UsuarioCredentialsApps usuarioCredentials;

    public UsuarioDetailCustom() {

    }

    public UsuarioDetailCustom(UsuarioCredentialsApps usuarioCredentials) {
        this.usuarioCredentials = usuarioCredentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioCredentialsApps getUsuarioCredentials() {
        return usuarioCredentials;
    }

    public void setUsuarioCredentials(UsuarioCredentialsApps usuarioCredentials) {
        this.usuarioCredentials = usuarioCredentials;
    }
}
