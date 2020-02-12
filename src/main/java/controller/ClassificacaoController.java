package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Classificacao;
import model.Jogo;
import model.Usuario;
import repository.ClassificacaoDAO;
import repository.JogoDAO;

@SessionAttributes("usuarioLogado")
@Controller
public class ClassificacaoController {
	@Autowired(required = true)
	private ClassificacaoDAO cd;
	@Autowired(required = true)
	private JogoDAO jd;

	@GetMapping("/cadastrarClassificacao")
	public String cadastrarClassificacao(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroClassificacao";
	}

	@PostMapping("/cadastrarClassificacao")
	public String cadastrarClassificacao(@Valid Classificacao classificacao, BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao adicionar classificação. Verifique os campos");
		}
		else {
			try {
				cd.save(classificacao);
				atributos.addFlashAttribute("mensagem","Classificação adicionada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao adicionar classificação. Verifique os campos");	
				return "redirect:/cadastrarClassificacao";
			}
			
		}
		return "redirect:/cadastrarClassificacao";
	}
	
	@GetMapping(value = "detalharClassificacao/{id}")
	public ModelAndView detalheClassificacao(@PathVariable("id") int id) {
		Classificacao classificacao = cd.findById(id);
		Iterable<Jogo> jogos = jd.findJogoByClassificacao(id);
		ModelAndView mv = new ModelAndView("detalheClassificacao");
		mv.addObject("classificacao", classificacao);
		mv.addObject("jogos",jogos);
		return mv;
	}
	
	

	@GetMapping("/gerenciarClassificacoes")
	public ModelAndView listarClassificacoes(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		 mv = new ModelAndView("gerenciarClassificacoes");
		Iterable<Classificacao> classificacoes = cd.findAll();
		mv.addObject("classificacoes", classificacoes);
		return mv;
	}

	/*
	 * @RequestMapping("detalhesClassificacao/{id}") public ModelAndView
	 * detalhesClassificacao(@PathVariable("id") int id) { Classificacao
	 * classificacao = cd.findById(id); ModelAndView mv = new
	 * ModelAndView("detalhesClassificacao"); mv.addObject("classificacao",
	 * classificacao); return mv; }
	 * 
	 */

	@GetMapping(value = "editarClassificacao/{id}")
	public ModelAndView editarClassificacao(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv = new ModelAndView("editarClassificacao");
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Classificacao classificacao = cd.findById(id);
		mv.addObject("classificacao", classificacao);
		return mv;
	}

	
	@PostMapping(value = "editarClassificacao/{id}")
	public String editarClassificacao(@PathVariable("id") int id,@Valid Classificacao classificacao, BindingResult result, RedirectAttributes atributos) {
		classificacao.setIdClassificacao(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar classificação. Verifique os campos");
		}
		else {
			try {
				cd.save(classificacao);
				atributos.addFlashAttribute("mensagem","Classificação editada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar classificação. Verifique os campos");	
				return "redirect:/gerenciarClassificacoes";
			}
			
		}
		return "redirect:/gerenciarClassificacoes";
	}

	@GetMapping("excluirClassificacao/{id}")
	public String excluirClassificacao(@PathVariable("id")int id,ModelMap model,RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
			try {
				cd.excluirClassificacao(id);
				atributos.addFlashAttribute("mensagem","Classificação excluída com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao excluir classificação.");	
				return "redirect:/gerenciarClassificacoes";
			}
			
		return "redirect:/gerenciarClassificacoes";

	}
	
	@GetMapping("buscarJogoPorClassificacao")
	public ModelAndView buscarJogoPorPlataforma(@RequestParam("id") int id) {
		Iterable<Jogo> jogos = jd.findJogoByClassificacao(id);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		mv.addObject("jogos",jogos);
		return mv;
	}
	
	@PostMapping("adicionarClassificacaoAJAX")
	public String adicionarClassificacaoAJAX(Classificacao classificacao) {
		cd.save(classificacao);
		return "redirect:/cadastrarJogo";
	}
}
	


