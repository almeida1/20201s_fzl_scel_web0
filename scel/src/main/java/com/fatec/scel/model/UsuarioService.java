package com.fatec.scel.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = false) 
public class UsuarioService implements IUsuarioService{
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@Override
	public Usuario salvar(Usuario usuario) {
		Usuario jaExiste = null;
		jaExiste = buscarPorRa(usuario.getRa());
		if (jaExiste == null) {
			String endereco = obtemEndereco(usuario.getCep());
			usuario.setEndereco(endereco);
			
			return usuarioRepository.save(usuario);
		} else
			return null;
	}
	@Override
	@Transactional(readOnly = true)//Nesse caso, os métodos serão específicos para leitura.
	public Usuario buscarPorRa(String ra) {
		return usuarioRepository.findByRa(ra);
	}
	@Override
	@Transactional(readOnly = true)//Nesse caso, os métodos serão específicos para leitura.
	public List<Usuario> buscarTodos() {
		Iterable<Usuario> iteratorDeUsuarios = usuarioRepository.findAll();
		// Cria um list vazio para armazenar o resultado
		List<Usuario> resultado = new ArrayList<Usuario>();
		// Usa iterable.forEach() para
		// Percorrer o vetor e adicionar cada elemento na colecao do tipo List
		iteratorDeUsuarios.forEach(resultado::add);

		return resultado;
	}
	@Override
	public void excluir(Long id) {
		usuarioRepository.deleteById(id);
	}
	@Override
	public Usuario findById(Long id) {
		return usuarioRepository.findById(id).get();
	}
  	public String obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		Endereco endereco = template.getForObject(url,Endereco.class,cep);
		return endereco.getLogradouro();
	}
  	
	
}

