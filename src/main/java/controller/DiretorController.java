package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Classificacao;
import model.Diretor;
import model.Jogo;
import model.Usuario;
import repository.DiretorDAO;
import repository.JogoDAO;

@SessionAttributes("usuarioLogado")
@Controller
public class DiretorController {
	@Autowired(required = true)
	private DiretorDAO dd;
	@Autowired(required = true)
	private JogoDAO jd;

	@GetMapping(value = "/cadastrarDiretor")
	public String cadastrarDiretor(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroDiretor";
	}

	@PostMapping(value = "/cadastrarDiretor")
	public String cadastrarDiretor(@Valid Diretor diretor,BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao adicionar Diretor. Verifique os campos");
		}
		else {
			try {
				dd.save(diretor);
				atributos.addFlashAttribute("mensagem","Diretor adicionado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao adicionar diretor. Verifique os campos");	
				return "redirect:/cadastrarDiretor";
			}
			
		}
		return "redirect:/cadastrarDiretor";
	}

	@GetMapping(value = "detalharDiretor/{id}")
	public ModelAndView detalheDiretor(@PathVariable("id") int id) {
		Diretor diretor = dd.findById(id);
		Iterable<Jogo> jogos = jd.findJogoByDiretor(id);
		ModelAndView mv = new ModelAndView("detalheDiretor");
		mv.addObject("diretor", diretor);
		mv.addObject("jogos", jogos);
		return mv;
	}

	@GetMapping("/gerenciarDiretores")
	public ModelAndView listarDiretores(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("gerenciarDiretores");
		Iterable<Diretor> diretores = dd.findAll();
		mv.addObject("diretores", diretores);
		return mv;
	}

	@GetMapping(value = "editarDiretor/{id}")
	public ModelAndView editarDiretor(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Diretor diretor = dd.findById(id);
		mv = new ModelAndView("editarDiretor");
		mv.addObject("diretor", diretor);
		return mv;
	}

	@PostMapping(value = "editarDiretor/{id}")
	public String editarDiretor(@PathVariable("id") int id, Diretor diretor,BindingResult result, RedirectAttributes atributos) {
		diretor.setIdDiretor(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar Diretor. Verifique os campos");
		}
		else {
			try {
				dd.save(diretor);
				atributos.addFlashAttribute("mensagem","Diretor editado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar diretor. Verifique os campos");	
				return "redirect:/gerenciarDiretores";
			}
			
		}
		return "redirect:/gerenciarDiretores";
	}

	@GetMapping(value = "excluirDiretor/{id}")
	public String excluirDiretor(@PathVariable("id") int id,ModelMap model, RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		try {
			dd.excluirDiretor(id);
			atributos.addFlashAttribute("mensagem","Diretor excluído com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir diretor. Verifique os campos");	
			return "redirect:/gerenciarDiretores";
		}
		return "redirect:/gerenciarDiretores";
	}
	
	@GetMapping("buscarJogoPorDiretor")
	public ModelAndView buscarJogoPorDiretor(@RequestParam("id") int id) {
		Iterable<Jogo> jogos = jd.findJogoByDiretor(id);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		mv.addObject("jogos",jogos);
		return mv;
	}
	

	@PostMapping("cadastrarDiretorAJAX")
	public String adicionarDiretorAJAX(Diretor diretor) {
		dd.save(diretor);
		return "redirect:/cadastrarJogo";
	}
}
