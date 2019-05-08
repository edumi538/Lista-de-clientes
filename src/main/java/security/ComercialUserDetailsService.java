/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;
import Repository.InterfaceCliente;
import Repository.InterfaceGrupos;
import Repository.InterfacePermissoes;
import java.util.ArrayList;
import java.util.Collection;

import listadeclientes.Cliente;
import listadeclientes.Permissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import listadeclientes.Grupo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author root
 */
public class ComercialUserDetailsService implements UserDetailsService{
    
    @Autowired
    private InterfaceCliente usuario;
    
    @Autowired
    private InterfaceGrupos grupos;
    
    @Autowired
    private InterfacePermissoes permissoes;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = usuario.findByUsername(username);
        
        if(cliente == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UsuarioSistema(cliente.getUsername(), cliente.getPassword(), authorities(cliente));
        }
    	public Collection<? extends GrantedAuthority> authorities(Cliente cliente) {
		return authorities(grupos.findByUsuariosIn(cliente));
	}
        public Collection<? extends GrantedAuthority> authorities(List<Grupo> grupos) {
		Collection<GrantedAuthority> auths = new ArrayList<>();
                
                 for (Grupo grupo: grupos) {
			List<Permissao> lista = permissoes.findByGruposIn(grupo);
			
			for (Permissao permissao: lista) {
				auths.add(new SimpleGrantedAuthority("ROLE_" + permissao.getNome()));
			}
                     }
                return auths; 
            }
        }