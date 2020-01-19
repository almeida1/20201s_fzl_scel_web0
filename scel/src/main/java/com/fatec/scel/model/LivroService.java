package com.fatec.scel.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false) // false eh default nao seria necessario definir
                                 // ou seja, ela deve ser usada tanto em métodos de consulta como de escrita.
public class LivroService implements ILivroService {
	@Autowired
	private LivroRepository livroRepository;

	@Override
	public Livro salvar(Livro livro) {
		Livro jaExiste = null;
		jaExiste = buscarPorIsbn(livro.getIsbn());
		if (jaExiste == null) {
			return livroRepository.save(livro);
		} else
			return null;
	}

	@Override
	@Transactional(readOnly = true)//Nesse caso, os métodos serão específicos para leitura.
	public Livro buscarPorIsbn(String isbn) {
		return livroRepository.findByIsbn(isbn);
	}

	@Override
	public void editar(Livro livro) {
		livroRepository.findAll();
	}

	@Override
	public void excluir(Long id) {
		livroRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)//Nesse caso, os métodos serão específicos para leitura.
	public List<Livro> buscarTodos() {
		Iterable<Livro> iteratorDeLivros = livroRepository.findAll();
		// Cria um list vazio para armazenar o resultado
		List<Livro> resultado = new ArrayList<Livro>();
		// Usa iterable.forEach() para
		// Percorrer o vetor e adicionar cada elemento na colecao do tipo List
		iteratorDeLivros.forEach(resultado::add);

		return resultado;
	}

	@Override
	public Livro findById(Long id) {
		return livroRepository.findById(id).get();
	}

}