package com.fatec.scel.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.scel.model.Usuario;
import com.fatec.scel.model.UsuarioService;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
	 private Logger logger = LogManager.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService service;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarUsuario");
		modelAndView.addObject("usuarios", service.buscarTodos());
		return modelAndView;
	}

	@GetMapping("/cadastrar")
	public ModelAndView cadastraUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView("cadastrarUsuario");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@GetMapping("/edit/{ra}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView mostraFormAdd(@PathVariable("ra") String ra) {
		ModelAndView modelAndView = new ModelAndView("AtualizaUsuario");
		modelAndView.addObject("usuario", service.buscarPorRa(ra)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		service.excluir(id);
		ModelAndView modelAndView = new ModelAndView("ConsultarUsuario");
		modelAndView.addObject("usuarios", service.buscarTodos());
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Usuario usuario, BindingResult result) {
		ModelAndView mv = new ModelAndView("CadastrarUsuario");
		if (result.hasErrors()) {
			mv.addObject("fail", "Dados inválidos"); // quando fail nao eh nulo a msg aparece na tela
			return mv;
		}
		try {
			
			Usuario umUsuario = service.salvar(usuario);
			if (umUsuario != null) {
				mv.addObject("success", "Usuario cadastrado com sucesso"); // quando success nao eh nulo
				return mv;
			} else {
				mv.addObject("fail", "Usuario já cadastrado."); // quando fail nao eh nulo a msg aparece na tela
				return mv;
			}
		} catch (Exception e) {
			mv.addObject("fail", "erro usuario controller ===> " + e.getMessage());
			return mv;
		}
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualiza(@PathVariable("id") Long id, @Valid Usuario usuario, BindingResult result) {
		ModelAndView mv = null;
		if (result.hasErrors()) {
			
			System.out.println("tem erro volta para atualiza usuario");
			mv = new ModelAndView("AtualizaUsuario");
			mv.addObject("fail", "Dados inválidos" + result.getFieldError().toString());
			usuario.setId(id);
			return mv;
		}
		Usuario umUsuario = service.findById(id);
		umUsuario.setRa(usuario.getRa());
		umUsuario.setNome(usuario.getNome());
		umUsuario.setEmail(usuario.getEmail());
		umUsuario.setCep(usuario.getCep());
		umUsuario.setSenha(usuario.getSenha());
		
		try {
			String endereco = service.obtemEndereco(usuario.getCep());
			umUsuario.setEndereco(endereco);
			service.salvar(umUsuario);
			mv = new ModelAndView("ConsultarUsuario");
		} catch (Exception e) {
			mv = new ModelAndView("AtualizaUsuario");
			umUsuario.setEndereco("");
			mv.addObject("fail", "CEP não localizado.");
			return mv;
		}

		mv.addObject("usuarios", service.buscarTodos());
		return mv;
	}
}