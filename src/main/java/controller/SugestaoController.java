package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Sugestao;
import model.Usuario;
import repository.SugestaoDAO;

@Controller
@SessionAttributes("usuarioLogado")
public class SugestaoController {
	@Autowired(required = true)
	public SugestaoDAO sd;

	@GetMapping(value = "cadastrarSugestao")
	public ModelAndView cadastrarSugestao(ModelMap model) {
		ModelAndView mv;
		if (model.get("usuarioLogado") == null) {
			mv = new ModelAndView("formLogin");
			return mv;
		} else {
			mv = new ModelAndView("cadastroSugestao");
			return mv;
		}
	}

	@PostMapping(value = "cadastrarSugestao")
	public String cadastrarSugestao(@Valid Sugestao sugestao, ModelMap model, BindingResult result, RedirectAttributes atributos) {
		sugestao.setUsuario((Usuario) model.get("usuarioLogado"));
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar Sugestão. Verifique os campos");
		}
		else {
			try {
				sd.save(sugestao);
				atributos.addFlashAttribute("mensagem","Sugestão cadastrada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar sugestão.Verifique os campos");	
				return "redirect:/cadastrarSugestao";
			}
			
		}
		return "redirect:/cadastrarSugestao";
	}

	@GetMapping(value = "gerenciarSugestoes")
	public ModelAndView gerenciarSugestoes(ModelMap model) {
		ModelAndView mv;
		Usuario u = new Usuario();
		u = (Usuario) model.get("usuarioLogado");
		if (model.get("usuarioLogado") == null) {
			mv = new ModelAndView("formLogin");
			return mv;
		} else {
			if (u.getTipoUsuario().getIdTipoUsuario() == 1) {
				mv = new ModelAndView("gerenciarSugestoes");
				mv.addObject("sugestoesF", sd.listarSugestoesInativas());
				return mv;
			} else {
				mv = new ModelAndView("acessoProibido");
				return mv;
			}

		}
	}

	@GetMapping(value = "aprovarSugestao/{id}")
	public String aprovarSugestao(ModelMap model, @PathVariable("id") int id,RedirectAttributes atributos) {
		try {
			sd.aprovarSugestao(id);
			atributos.addFlashAttribute("mensagem","Sugestão aprovada com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao aprovar sugestão.");	
			return "redirect:/gerenciarSugestoes";
		}
		return "redirect:/gerenciarSugestoes";
		
	}
		
		@GetMapping(value = "detalharSugestao/{id}")
		public ModelAndView detalharSugestao(ModelMap model, @PathVariable("id") int id) {
			ModelAndView mv;
			Usuario u = new Usuario();
			u = (Usuario) model.get("usuarioLogado");
			if (model.get("usuarioLogado") == null) {
				mv = new ModelAndView("formLogin");
				return mv;
			} else {
				if (u.getTipoUsuario().getIdTipoUsuario() == 1) {
					mv = new ModelAndView("detalheSugestao");
					mv.addObject("sugestao",sd.findById(id));
					return mv;
				} else {
					mv = new ModelAndView("acessoProibido");
					return mv;
				}
			}

	}

}
