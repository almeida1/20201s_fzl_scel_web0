package com.fatec.scel.model;

import java.util.List;

public interface ILivroService {
	Livro salvar(Livro livro);

	void editar(Livro livro);

	void excluir(Long id);

	Livro buscarPorIsbn(String isbn);

	List<Livro> buscarTodos();

	Livro findById(Long id);
}