package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Jogo;
import model.Plataforma;
import model.Usuario;
import repository.JogoDAO;
import repository.PlataformaDAO;

@Controller
@SessionAttributes("usuarioLogado")
public class PlataformaController {
	@Autowired(required = true)
	private PlataformaDAO pd;
	@Autowired(required = true)
	private JogoDAO jd;

	@GetMapping(value = "/cadastrarPlataforma")
	public String cadastrarPlataforma(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroPlataforma";
	}

	@PostMapping(value = "/cadastrarPlataforma")
	public String cadastrarPlataforma(@Valid Plataforma plataforma,BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar plataforma. Verifique os campos");
		}
		else {
			try {
				pd.save(plataforma);
				atributos.addFlashAttribute("mensagem","Plataforma cadastrada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar plataforma. Verifique os campos");	
				return "redirect:/cadastrarPlataforma";
			}
			
		}
		return "redirect:/cadastrarPlataforma";
	}

	@GetMapping(value = "detalharPlataforma/{id}")
	public ModelAndView detalhePlataforma(@PathVariable("id") int id) {
		Plataforma plataforma = pd.findById(id);
		Iterable<Jogo> jogos = jd.findJogoByPlataforma(id);
		ModelAndView mv = new ModelAndView("detalhePlataforma");
		mv.addObject("plataforma", plataforma);
		mv.addObject("jogos", jogos);
		return mv;
	}

	@GetMapping("/gerenciarPlataformas")
	public ModelAndView listarPlataformas(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("gerenciarPlataformas");
		Iterable<Plataforma> plataformas = pd.findAll();
		mv.addObject("plataformas", plataformas);
		return mv;
	}

	@GetMapping(value = "editarPlataforma/{id}")
	public ModelAndView editarPlataforma(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Plataforma plataforma = pd.findById(id);
		mv = new ModelAndView("editarPlataforma");
		mv.addObject("plataforma", plataforma);
		return mv;
	}

	@PostMapping(value = "editarPlataforma/{id}")
	public String editarPlataforma(@PathVariable("id") int id, @Valid Plataforma plataforma,BindingResult result, RedirectAttributes atributos) {
		plataforma.setIdPlataforma(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar plataforma. Verifique os campos");
		}
		else {
			try {
				pd.save(plataforma);
				atributos.addFlashAttribute("mensagem","Plataforma editada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar plataforma. Verifique os campos");	
				return "redirect:/gerenciarPlataformas";
			}
			
		}
		return "redirect:/gerenciarPlataformas";
	}

	@RequestMapping(value = "/excluirPlataforma/{id}", method = RequestMethod.GET)
	public String excluirPlataforma(@PathVariable("id") int id,ModelMap model,RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if (u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		try {
			pd.excluirPlataforma(id);
			atributos.addFlashAttribute("mensagem","Plataforma excluída com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir plataforma");	
			return "redirect:/gerenciarPlataformas";
		}
		return "redirect:/gerenciarPlataformas";
	}
	
	@GetMapping("buscarJogoPorPlataforma")
	public ModelAndView buscarJogoPorPlataforma(@RequestParam("id") int id) {
		Iterable<Jogo> jogos = jd.findJogoByPlataforma(id);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		if(jogos != null) {
			mv.addObject("jogos",jogos);
		}
		else {
			jogos = null;
			mv.addObject("jogos",jogos);

		}
		return mv;
	}
	
	
	@PostMapping("cadastrarPlataformaAJAX")
	public String adicionarPlataformaAJAX(Plataforma plataforma) {
		pd.save(plataforma);
		return "redirect:/cadastrarJogo";
	}
	

}
