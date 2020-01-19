package com.fatec.scel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;


import org.hibernate.annotations.NaturalId;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Usuario {
	
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
	private String senha;
	
	private String cep;
	
	private String endereco;
	
	private int perfil;
	
    public Usuario() {
    	this.perfil=2;
    }
	public Usuario(String ra, String nome, String email, String senha, String cep) {
		
		this.ra = ra;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cep = cep;
	    this.perfil=2;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
	public int getPerfil() {
		return perfil;
	}
	
	public void setPerfil (int p) {
		this.perfil = p;
	}

}
