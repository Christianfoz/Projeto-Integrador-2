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

import model.Genero;
import model.Jogo;
import model.ModoJogo;
import model.Usuario;
import repository.JogoDAO;
import repository.ModoJogoDAO;

@Controller
@SessionAttributes("usuarioLogado")
public class ModoJogoController {
	@Autowired(required = true)
	private ModoJogoDAO md;
	@Autowired(required = true)
	private JogoDAO jd;

	@GetMapping(value = "/cadastrarModoJogo")
	public String cadastrarModoJogo(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroModo";
	}

	@PostMapping(value = "/cadastrarModoJogo")
	public String cadastrarModoJogo(@Valid ModoJogo modoJogo,BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar Modo de Jogo. Verifique os campos");
		}
		else {
			try {
				md.save(modoJogo);
				atributos.addFlashAttribute("mensagem","Modo de Jogo cadastrado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar modo de jogo. Verifique os campos");	
				return "redirect:/cadastrarModoJogo";
			}
			
		}
		md.save(modoJogo);
		return "redirect:/cadastrarModoJogo";
	}

	@GetMapping(value = "detalharModoJogo/{id}")
	public ModelAndView detalheModoJogo(@PathVariable("id") int id) {
		ModoJogo modoJogo = md.findById(id);
		Iterable<Jogo> jogos = jd.findJogoByModo(id);
		ModelAndView mv = new ModelAndView("detalheModo");
		mv.addObject("modojogo", modoJogo);
		mv.addObject("jogos", jogos);
		return mv;
	}

	@GetMapping(value = "/gerenciarModoJogos")
	public ModelAndView listarModosJogo(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("gerenciarModosJogos");
		Iterable<ModoJogo> modoJogo = md.findAll();
		mv.addObject("modoJogos", modoJogo);
		return mv;
	}

	@GetMapping(value = "editarModo/{id}")
	public ModelAndView editarModo(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		ModoJogo modoJogo = md.findById(id);
		mv = new ModelAndView("editarModo");
		mv.addObject("modoJogo", modoJogo);
		return mv;
	}

	@PostMapping(value = "editarModo/{id}")
	public String editarModo(@PathVariable("id") int id, @Valid ModoJogo modoJogo,BindingResult result, RedirectAttributes atributos) {
		modoJogo.setIdModo(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar Modo de Jogo. Verifique os campos");
		}
		else {
			try {
				md.save(modoJogo);
				atributos.addFlashAttribute("mensagem","Modo de Jogo editado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar modo de jogo. Verifique os campos");	
				return "redirect:/gerenciarModoJogos";
			}
			
		}
		return "redirect:/gerenciarModoJogos";
	}

	@GetMapping(value = "/excluirModo/{id}")
	public String excluirModoJogo(@PathVariable("id") int id,ModelMap model,RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		try {
			md.excluirModoJogo(id);
			atributos.addFlashAttribute("mensagem","Modo de Jogo excluído com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir modo de jogo. Verifique os campos");	
			return "redirect:/gerenciarModoJogos";
		}
		return "redirect:/gerenciarModoJogos";
	}
	
	@GetMapping("buscarJogoPorModo")
	public ModelAndView buscarJogoPorModo(@RequestParam("id") int id) {
		Iterable<Jogo> jogos = jd.findJogoByModo(id);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		mv.addObject("jogos",jogos);
		return mv;
	}
	
	@PostMapping("cadastrarModoJogoAJAX")
	public String adicionarGeneroAJAX(ModoJogo modoJogo) {
		md.save(modoJogo);
		return "redirect:/cadastrarJogo";
	}

}
