package com.fatec.scel.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;


import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fatec.scel.security.*;


@Entity
public class Usuario implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NaturalId
	@Column(nullable = false, length = 4)
	@NotEmpty(message="O ra deve ser preenchido")
	private String ra;
	
	@Column(nullable = false, length = 100)
	@NotEmpty(message="O nome deve ser preenchido")
	private String nome;
	
	@Column(nullable = false, length = 100)
	@NotEmpty(message="O e-mail deve ser preenchido")
	private String email;
	
	@JsonIgnore
	@NotEmpty(message="O campo senha é obrigatório")
	private String password;
	
	@ManyToMany
	@JoinTable(name="usuario_perfil", 
	           joinColumns = @JoinColumn(name = "email_id", referencedColumnName="email"),
	    inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName= "perfil"))
	
	private List<Perfil> perfis;
	
	private String cep;
	
	private String endereco;
	
	private String perfil;
	
    public Usuario() {
    	this.perfil="BIB";
    }
	public Usuario(String ra, String nome, String email, String senha, String cep) {
		
		this.ra = ra;
		this.nome = nome;
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(senha);
		this.cep = cep;
	    this.perfil="BIB";
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setUsername(String email) {
		this.email = email;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	public void setPassword(String senha) {
		this.password = new BCryptPasswordEncoder().encode(senha);
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public String toString() {
		return "Usuario [ra=" + ra + ", nome=" + nome + ", email=" + email + "]";
	}
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil (String p) {
		this.perfil = p;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}