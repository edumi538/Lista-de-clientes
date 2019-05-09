package listadeclientes;

import java.io.Console;
import java.util.Collection;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.jfree.util.Log;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;


@Entity
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String nomecompleto;
    private String email;
    private String password;
    
    public Usuario() {
    }

    public Usuario(String nomecompleto,String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nomecompleto = nomecompleto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomecompleto() {
        return nomecompleto;
    }

    public void setNomecompleto(String nomecompleto) {
        this.nomecompleto = nomecompleto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    
        return null;
    }

    @Override
    public String getPassword() {   
        return this.password;
    }

    @Override
    public String getUsername() {
        
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
        
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
        
    }

}
