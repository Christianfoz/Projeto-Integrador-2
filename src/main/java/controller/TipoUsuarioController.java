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

import model.TipoUsuario;
import model.Usuario;
import repository.TipoUsuarioDAO;

@Controller
@SessionAttributes("usuarioLogado")
public class TipoUsuarioController {
	@Autowired(required = true)
	private TipoUsuarioDAO tu;
	
	@GetMapping(value = "/cadastrarTipoUsuario")
	public String cadastrarTipoUsuario(ModelMap model) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		return "cadastroTipoUsuario";
	}
	
	@PostMapping(value = "/cadastrarTipoUsuario")
	public String cadastrarTipoUsuario(@Valid TipoUsuario tipoUsuario,BindingResult result, RedirectAttributes atributos) {
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar Tipo de Usuário. Verifique os campos");
		}
		else {
			try {
				tu.save(tipoUsuario);
				atributos.addFlashAttribute("mensagem","Tipo de Usuário cadastrado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar Tipo de Usuário.Verifique os campos");	
				return "redirect:/cadastrarTipoUsuario";
			}
			
		}
		return "redirect:/cadastrarTipoUsuario";
	}
	
	@GetMapping("/gerenciarTiposUsuarios")
	public ModelAndView listarTiposUsuarios(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("gerenciarTiposUsuarios");
		Iterable<TipoUsuario> tipos = tu.findAll();
		mv.addObject("tiposusuarios",tipos);
		return mv;
	}
	

	
	@GetMapping(value = "editarTipoUsuario/{id}")
	public ModelAndView editarTipoUsuario(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			mv = new ModelAndView("acessoProibido");
			return mv;
		}
		TipoUsuario tipoUsuario =  tu.findById(id);
		mv = new ModelAndView("editarTipo");
		mv.addObject("tipousuario",tipoUsuario);
		return mv;
	}
	
	@PostMapping(value = "/editarTipoUsuario/{id}")
	public String editarTipoUsuario(@PathVariable("id") int id,@Valid TipoUsuario tipoUsuario, BindingResult result, RedirectAttributes atributos) {
		tipoUsuario.setIdTipoUsuario(id);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar Tipo de Usuário. Verifique os campos");
		}
		else {
			try {
				tu.save(tipoUsuario);
				atributos.addFlashAttribute("mensagem","Tipo de Usuário editado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar Tipo de Usuário.Verifique os campos");	
				return "redirect:/gerenciarTiposUsuarios";
			}
			
		}
		return "redirect:/gerenciarTiposUsuarios";
	}
	
	@GetMapping(value = "/excluirTipoUsuario/{id}")
	public String excluirTipoUsuario(@PathVariable("id") int id,ModelMap model, RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		try {
			tu.excluirTipoUsuario(id);
			atributos.addFlashAttribute("mensagem","Tipo de Usuário excluído com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir Tipo de Usuário");	
			return "redirect:/gerenciarTiposUsuarios";
		}
		return "redirect:/gerenciarTiposUsuarios";
	}
}
