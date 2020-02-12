package controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Classificacao;
import model.Jogo;
import model.Review;
import model.Usuario;
import repository.JogoDAO;
import repository.ReviewDAO;


@Controller
@SessionAttributes("usuarioLogado")
public class ReviewController {
	@Autowired
	public ReviewDAO rd;
	@Autowired
	public JogoDAO jd;
	
	@GetMapping(value = "gerenciarReviews")
	public ModelAndView gerenciarReviewsFalse(ModelMap model) {
		//função para adm moderar as views(TANTO AS APROVADAS QUANTO AS NAO APROVADAS)
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
		mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Iterable<Review> reviewsF= rd.findReviewsFalse();
		Iterable<Review> reviewsT= rd.findReviewsFalse();
		mv = new ModelAndView("gerenciarReviews");
		mv.addObject("reviewsF",reviewsF);
		mv.addObject("reviewsT",reviewsT);
		return mv;
	}
	
	@GetMapping(value = "cadastrarReview/{id}")
	public ModelAndView formReview(ModelMap model,@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("cadastrarReview");
		Jogo j = jd.findJogoById(id);
		mv.addObject("jogo", j);
		Integer notas[] = {0,1,2,3,4,5,6,7,8,9,10};
		mv.addObject("notas",notas);
		return mv;
	}
	
	
	@PostMapping(value = "cadastrarReview/{id}")
	public String escreverReview(@Valid Review review, @ModelAttribute("nota") Integer nota, ModelMap model,@PathVariable("id") int id,BindingResult result, RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u == null) {
			return "formLogin";
		}
		else {
			review.setUsuario(u);
			Jogo j = jd.findJogoById(id) ;
			review.setJogo(j);
			review.setNota(nota);
			Date date = new Date();
            review.setDataCadastroReview(date);	
		}
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar review. Verifique os campos");
		}
		else {
			try {
				rd.save(review);
				atributos.addFlashAttribute("mensagem","Review cadastrada com sucesso. Aguarde análise");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar Review. Verifique os campos");	
				return "redirect:/detalharJogo/{id}";
			}
			
		}
		return "redirect:/detalharJogo/{id}";
	}
	
	
	@ResponseBody
	@GetMapping(value = "editarReview/{id}")
	public ModelAndView editarReview(@PathVariable("id") int id) {
		Review review = rd.findById(id);
		ModelAndView mv = new ModelAndView("editarReview");
		mv.addObject("review", review);
		return mv;
	}
	
	@GetMapping(value = "aprovarReview/{id}")
	public String aprovarReview(@PathVariable("id") int id,RedirectAttributes atributos) {
		try {
			rd.aprovarReview(id);
			atributos.addFlashAttribute("mensagem","Review aprovada com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao aprovar Review.");	
			return "redirect:/gerenciarReviews";
		}
		return "redirect:/gerenciarReviews";
	}
	
	@GetMapping(value = "editarReview")
	public ModelAndView editarReview(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv = new ModelAndView("editarReview");
		Review review = rd.findById(id);
		mv.addObject("review", review);
		return mv;
	}

	@PostMapping(value = "editarReview/{id}")
	public String editarReview(@Valid Review review, @ModelAttribute("nota") Integer nota, ModelMap model,@PathVariable("id") int id,BindingResult result, RedirectAttributes atributos) {
		review.setIdReview(id);
		Jogo j = rd.findJogoByReviewId(id);
		review.setJogo(j);
		review.setStatus(true);
		review.setDataCadastroReview(new Date());
		Usuario u = (Usuario) model.get("usuarioLogado");
		review.setNota(nota);
		review.setUsuario(u);
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao editar review. Verifique os campos");
		}
		else {
			try {
				rd.save(review);
				atributos.addFlashAttribute("mensagem","Review editada com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao editar Review. Verifique os campos");	
				return "redirect:/index";
			}
			
		}
		return "redirect:/index";
	}

	@GetMapping("excluirReview/{id}")
	public String excluirReview(@PathVariable("id") int id,RedirectAttributes atributos) {
		try {
			rd.excluirReview(id);
			atributos.addFlashAttribute("mensagem","Review excluída com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir Review.");	
			return "redirect:/index";
		}
		return "redirect:/index";

	}
	
	@GetMapping(value = "detalharReview/{id}")
	public ModelAndView detalheReview(@PathVariable("id") int id) {
		Review review = rd.findById(id);
		ModelAndView mv = new ModelAndView("detalheReview");
		mv.addObject("review", review);
		return mv;
	}
	

}
