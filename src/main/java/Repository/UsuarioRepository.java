/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;
import java.util.Optional;
import listadeclientes.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author root
 */
public interface UsuarioRepository extends CrudRepository<Usuario,String> {

    
    Usuario findByUsername(String username);
  
    
}
