package com.fatec.scel.model;

import java.util.List;

public interface IUsuarioService {
	public Usuario salvar(Usuario usuario) ;
	public Usuario buscarPorRa(String ra);
	public List<Usuario> buscarTodos();
	public void excluir(Long id) ;
	public Usuario findById(Long id);
}