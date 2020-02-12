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

import model.Desenvolvedora;
import model.Diretor;
import model.Jogo;
import model.Usuario;
import repository.DesenvolvedoraDAO;
import repository.JogoDAO;
@SessionAttributes("usuarioLogado")
@Controller
public class DesenvolvedoraController {
	@Autowired(required = true)
	private DesenvolvedoraDAO dd;
	@Autowired(required = true)
	private JogoDAO jd;

	@GetMapping(value = "/cadastrarDesenvolvedora")
	public String cadastrarDesenvolvedora(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroDesenvolvedora";
	}

	@PostMapping(value = "/cadastrarDesenvolvedora")
	public String cadastrarGenero(@Valid Desenvolvedora desenvolvedora,BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao adicionar Desenvolvedora. Verifique os campos");
		}
		else {
			try {
				dd.save(desenvolvedora);
				atributos.addFlashAttribute("mensagem","Desenvolvedora adicionada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao adicionar Desenvolvedora. Verifique os campos");	
				return "redirect:/cadastrarDesenvolvedora";
			}
			
		}
		return "redirect:/cadastrarDesenvolvedora";
	}
	
	@GetMapping(value = "detalharDesenvolvedora/{id}")
	public ModelAndView detalheDesenvolvedora(@PathVariable("id") int id) {
		Desenvolvedora desenvolvedora = dd.findById(id);
		Iterable<Jogo> jogos = jd.findJogoByDesenvolvedora(id);
		ModelAndView mv = new ModelAndView("detalheDesenvolvedora");
		mv.addObject("desenvolvedora", desenvolvedora);
		mv.addObject("jogos",jogos);
		return mv;
	}
	

	@GetMapping("/gerenciarDesenvolvedoras")
	public ModelAndView listarDesenvolvedoras(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("gerenciarDesenvolvedoras");
		Iterable<Desenvolvedora> desenvolvedoras = dd.findAll();
		mv.addObject("desenvolvedoras", desenvolvedoras);
		return mv;
	}


	@GetMapping(value = "editarDesenvolvedora/{id}")
	public ModelAndView editarDesenvolvedora(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Desenvolvedora desenvolvedora = dd.findById(id);
		mv = new ModelAndView("editarDesenvolvedora");
		mv.addObject("desenvolvedora", desenvolvedora);
		return mv;
	}
	
	
	@PostMapping(value = "editarDesenvolvedora/{id}")
	public String editarDesenvolvedora(@PathVariable("id") int id,@Valid Desenvolvedora desenvolvedora,BindingResult result, RedirectAttributes atributos) {
		desenvolvedora.setIdDesenvolvedora(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar Desenvolvedora. Verifique os campos");
		}
		else {
			try {
				dd.save(desenvolvedora);
				atributos.addFlashAttribute("mensagem","Desenvolvedora editada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar Desenvolvedora. Verifique os campos");	
				return "redirect:/gerenciarDesenvolvedoras";
			}
			
		}
		return "redirect:/gerenciarDesenvolvedoras";
	}


	@GetMapping(value = "/excluirDesenvolvedora/{id}")
	public String excluirDesenvolvedora(@PathVariable("id")int id,ModelMap model,RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
			try {
				dd.excluirDesenvolvedora(id);
				atributos.addFlashAttribute("mensagem","Desenvolvedora excluída com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao excluir Desenvolvedora.");	
				return "redirect:/gerenciarDesenvolvedoras";
			}
			
		return "redirect:/gerenciarDesenvolvedoras";
	}
	
	@GetMapping("buscarJogoPorDesenvolvedora")
	public ModelAndView buscarJogoPorDesenvolvedora(@RequestParam("id") int id) {
		Iterable<Jogo> jogos = jd.findJogoByDesenvolvedora(id);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		mv.addObject("jogos",jogos);
		return mv;
	}
	
	@PostMapping("adicionarDesenvolvedoraAJAX")
	public String adicionarDesenvolvedoraAJAX(Desenvolvedora desenvolvedora) {
		dd.save(desenvolvedora);
		return "redirect:/cadastrarJogo";
	}
	
	
}
