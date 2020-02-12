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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Desenvolvedora;
import model.Genero;
import model.Jogo;
import model.Usuario;
import repository.GeneroDAO;
import repository.JogoDAO;

@Controller
@SessionAttributes("usuarioLogado")
public class GeneroController {
	@Autowired(required = true)
	private GeneroDAO gd;
	@Autowired(required = true)
	private JogoDAO jd;
	
	@GetMapping(value = "/cadastrarGenero" )
	public String cadastrarGenero(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroGenero";
	}
	
	@PostMapping(value = "/cadastrarGenero")
	public String cadastrarGenero(@Valid Genero genero,BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao adicionar gênero. Verifique os campos");
		}
		else {
			try {
				gd.save(genero);
				atributos.addFlashAttribute("mensagem","Gênero adicionado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao adicionar gênero. Verifique os campos");	
				return "redirect:/cadastrarGenero";
			}
			
		}
		return "redirect:/cadastrarGenero";
	}
	
	
	@RequestMapping("/gerenciarGeneros")
	public ModelAndView listarGeneros(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			 mv = new ModelAndView("acessoProibido");
			return mv;
		}
		 mv = new ModelAndView("gerenciarGeneros");
		Iterable<Genero> generos = gd.findAll();
		mv.addObject("generos",generos);
		return mv;
	}
	
	@GetMapping(value = "detalharGenero/{id}")
	public ModelAndView detalheGenero(@PathVariable("id") int id) {
		Genero genero = gd.findById(id);
		Iterable<Jogo> jogos = jd.findJogoByGenero(id);
		ModelAndView mv = new ModelAndView("detalheGenero");
		mv.addObject("genero", genero);
		mv.addObject("jogos",jogos);
		return mv;
	}
	
	@GetMapping(value = "editarGenero/{id}")
	public ModelAndView editarGenero(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Genero genero = gd.findById(id);
		mv = new ModelAndView("editarGenero");
		mv.addObject("genero", genero);
		return mv;
	}
	
	
	@PostMapping(value = "editarGenero/{id}")
	public String editarGenero(@PathVariable("id") int id,@Valid Genero genero,BindingResult result, RedirectAttributes atributos) {
		genero.setIdGenero(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar gênero. Verifique os campos");
		}
		else {
			try {
				gd.save(genero);
				atributos.addFlashAttribute("mensagem","Gênero editado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar gênero. Verifique os campos");	
				return "redirect:/gerenciarGeneros";
			}
			
		}
		return "redirect:/gerenciarGeneros";
	}
	
	@RequestMapping(value = "/excluirGenero/{id}", method = RequestMethod.GET)
	public String excluirGenero(@PathVariable("id") int id,ModelMap model, RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		try {
			gd.excluirGenero(id);
			atributos.addFlashAttribute("mensagem","Gênero excluído com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir gênero. Verifique os campos");	
			return "redirect:/gerenciarGeneros";
		}
		return "redirect:/gerenciarGeneros";
	}
	
	@GetMapping("buscarJogoPorGenero")
	public ModelAndView buscarJogoPorGenero(@RequestParam("id") int id) {
		Iterable<Jogo> jogos = jd.findJogoByGenero(id);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		mv.addObject("jogos",jogos);
		return mv;
	}
	
	@PostMapping("cadastrarGeneroAJAX")
	public String adicionarGeneroAJAX(Genero genero) {
		gd.save(genero);
		return "redirect:/cadastrarJogo";
	}
}
