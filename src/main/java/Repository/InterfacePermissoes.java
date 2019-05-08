/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import listadeclientes.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import listadeclientes.Grupo;

/**
 *
 * @author root
 */
public interface InterfacePermissoes extends JpaRepository<Permissao, Long> {
	
	List<Permissao> findByGruposIn(Grupo grupo);

}