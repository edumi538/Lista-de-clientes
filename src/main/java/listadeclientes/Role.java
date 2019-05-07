/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listadeclientes;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 *
 * @author veritas-estagio
 */
@javax.persistence.Entity
public class Role implements org.springframework.security.core.GrantedAuthority{
    
    @javax.persistence.Id
    private String nomeRole;
    
    @ManyToMany(mappedBy = "roles")
    private List<Cliente> cliente;
    
    

    @Override
    public String getAuthority() {
        return this.nomeRole;
    }

    /**
     * @return the nomeRole
     */
    public String getNomeRole() {
        return nomeRole;
    }

    /**
     * @param nomeRole the nomeRole to set
     */
    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }

    /**
     * @return the cliente
     */
    public List<Cliente> getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(List<Cliente> cliente) {
        this.cliente = cliente;
    }
    
}
