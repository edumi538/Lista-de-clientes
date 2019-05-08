/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import listadeclientes.Cliente;
import listadeclientes.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 *
 * @author root
 */

public interface InterfaceGrupos extends JpaRepository<Grupo, Long> {
	
	List<Grupo> findByUsuariosIn(Cliente cliente);

}
