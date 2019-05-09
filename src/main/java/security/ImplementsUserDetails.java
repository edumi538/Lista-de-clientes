/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import listadeclientes.Usuario;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class ImplementsUserDetails implements UserDetailsService{
    
    @Autowired
   private UsuarioRepository ur; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = ur.findByUsername(username);
        
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return usuario;
    }
    
}
