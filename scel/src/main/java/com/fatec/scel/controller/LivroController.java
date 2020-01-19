package com.fatec.scel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.scel.model.Livro;

import com.fatec.scel.model.LivroService;

@RestController
@RequestMapping(path = "/livros")
public class LivroController {
	@Autowired
	private LivroService service;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarLivros");
		modelAndView.addObject("livros", service.buscarTodos());
		return modelAndView;
	}

	/**
	 * quando o usuario digita localhost:8080/livros/cadastrar
	 * 
	 * @param livro
	 * @return o html /CadastraLivro
	 */
	@GetMapping("/cadastrar")
	public ModelAndView cadastraLivro(Livro livro) {
		ModelAndView mv = new ModelAndView("cadastrarLivro");
		mv.addObject("livro", livro);
		return mv;
	}

	@GetMapping("/edit/{isbn}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView mostraFormAdd(@PathVariable("isbn") String isbn) {
		ModelAndView modelAndView = new ModelAndView("AtualizaLivro");
		modelAndView.addObject("livro", service.buscarPorIsbn(isbn)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view

	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		service.excluir(id);
		ModelAndView modelAndView = new ModelAndView("ConsultarLivros");
		modelAndView.addObject("livros", service.buscarTodos());
		return modelAndView;

	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Livro livro, BindingResult result) {
		ModelAndView mv = new ModelAndView("CadastrarLivro");
		if (result.hasErrors()) {
			mv.addObject("fail", "Dados inválidos"); // quando fail nao eh nulo a msg aparece na tela
			return mv;
		}
		try {

			Livro umLivro = service.salvar(livro);
			if (umLivro != null) {
				mv.addObject("success", "Livro cadastrado com sucesso"); // quando success nao eh nulo
				return mv;
			} else {
				mv.addObject("fail", "Livro já cadastrado."); // quando fail nao eh nulo a msg aparece na tela
				return mv;
			}
		} catch (Exception e) {
			mv.addObject("fail", "erro ===> " + e.getMessage());
			return mv;
		}
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualiza(@PathVariable("id") Long id, @Valid Livro livro, BindingResult result) {
		if (result.hasErrors()) {
			livro.setId(id);
			return new ModelAndView("AtualizaLivro");
		}
		Livro umLivro = service.findById(id);
		umLivro.setAutor(livro.getAutor());
		umLivro.setIsbn(livro.getIsbn());
		umLivro.setTitulo(livro.getTitulo());
		service.salvar(umLivro);
		ModelAndView modelAndView = new ModelAndView("ConsultarLivros");
		modelAndView.addObject("livros", service.buscarTodos());
		return modelAndView;
	}
}