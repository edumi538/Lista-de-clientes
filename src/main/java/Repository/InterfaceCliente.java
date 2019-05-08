/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import listadeclientes.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author root
 */
public interface InterfaceCliente extends JpaRepository<Cliente, Long> {
    
    Cliente findByUsername(String username);
    
}
