package com.fatec.scel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fatec.scel.model.Usuario;
import com.fatec.scel.model.UsuarioRepository;
@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repo;
	
    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
 		Usuario umUsuario = repo.findByEmail(email);
		if (umUsuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}
		return umUsuario ;
	}

}
