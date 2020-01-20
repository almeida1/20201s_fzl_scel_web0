package com.fatec.scel.security;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import com.fatec.scel.model.Usuario;


@Entity
public class Perfil implements GrantedAuthority{
	@Id
	private String perfil;
	@ManyToMany
	private List<Usuario> usuarios;

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		return perfil;
	}

}
