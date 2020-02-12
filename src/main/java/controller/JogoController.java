package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Classificacao;
import model.Desenvolvedora;
import model.Diretor;
import model.Genero;
import model.Jogo;
import model.ModoJogo;
import model.Plataforma;
import model.Usuario;
import repository.ClassificacaoDAO;
import repository.DesenvolvedoraDAO;
import repository.DiretorDAO;
import repository.GeneroDAO;
import repository.JogoDAO;
import repository.ModoJogoDAO;
import repository.PlataformaDAO;
import repository.ReviewDAO;
import repository.UsuarioDAO;

@Controller
@SessionAttributes("usuarioLogado")
public class JogoController {
	@Autowired(required = true)
	public JogoDAO jd;
	@Autowired(required = true)
	public PlataformaDAO pd;
	@Autowired(required = true)
	public DiretorDAO dd;
	@Autowired(required = true)
	public DesenvolvedoraDAO ddao;
	@Autowired(required = true)
	public GeneroDAO gd;
	@Autowired(required = true)
	public ModoJogoDAO mj;
	@Autowired(required = true)
	public ClassificacaoDAO cd;
	@Autowired(required = true)
	public ReviewDAO rd;
	@Autowired(required = true)
	public UsuarioDAO ud;

	@ResponseBody
	@GetMapping(value = "/cadastrarJogo")
	public ModelAndView formAdicionarJogo(Model model,ModelMap modelM) {
		ModelAndView mv;
		Usuario u = (Usuario) modelM.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
		mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("cadastroJogo");
		Iterable<Genero> generos = gd.findAll();
		Iterable<Plataforma> plataformas = pd.findAll();
		Iterable<Diretor> diretores = dd.findAll();
		Iterable<Desenvolvedora> desenvolvedoras = ddao.findAll();
		Iterable<ModoJogo> modoJogos = mj.findAll();
		Iterable<Classificacao> classificacoes = cd.findAll();
		model.addAttribute("classificacao", new Classificacao());
		model.addAttribute("diretor", new Diretor());
		model.addAttribute("desenvolvedora", new Desenvolvedora());
		model.addAttribute("modoJogo", new ModoJogo());
		mv.addObject("classificacoes", classificacoes);
		mv.addObject("generos", generos);
		mv.addObject("plataformas", plataformas);
		mv.addObject("diretores", diretores);
		mv.addObject("desenvolvedoras", desenvolvedoras);
		mv.addObject("modoJogos", modoJogos);
		return mv;
	}

	@PostMapping(value = "/cadastrarJogo")
	public String adicionarJogo(@Valid Jogo jogo,@ModelAttribute("classificacao") Classificacao classificacao,@ModelAttribute("diretor") Diretor diretor,
			@ModelAttribute("desenvolvedora") Desenvolvedora desenvolvedora, @ModelAttribute("modoJogo")ModoJogo modoJogo,@RequestParam List<Plataforma> plataformas
			,@RequestParam List<Genero> generos,@RequestParam("file") MultipartFile file,BindingResult result, RedirectAttributes atributos) {
		jogo.setDesenvolvedora(desenvolvedora);
		jogo.setDiretor(diretor);
		jogo.setModoJogo(modoJogo);
		jogo.setClassificacao(classificacao);
		jogo.setPlataformas(plataformas);
		jogo.setGeneros(generos);
		 try {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Random random = new Random();
	            /*Path path = Paths.get("/home/superior/eclipse-workspace/omg/src/main/resources/templates/" + 
	            		"" + file.getOriginalFilename());*/
	            String url = random.nextInt(20) + file.getOriginalFilename();
	            Path path = Paths.get("C:\\Users\\User\\Desktop\\tudo\\trabalhoespaço\\omg\\src\\main\\resources\\templates\\" + url);
	            Files.write(path, bytes);
	         
	            jogo.setFoto(url);
	            Calendar calendar = Calendar.getInstance();
	            jogo.setDataCadastro(calendar);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 if(result.hasErrors()) {
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar jogo. Verifique os campos");
			}
			else {
				try {
					jd.save(jogo);
					atributos.addFlashAttribute("mensagem","Jogo adicionado com sucesso");	
				}catch(Exception e){
					atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar jogo. Verifique os campos");	
					return "redirect:/cadastrarJogo";
				}
			}
			return "redirect:/cadastrarJogo";

	}
	
	@GetMapping(value = "detalharJogo/{id}")
	public ModelAndView detalheJogo(@PathVariable("id") int id,ModelMap model) {
		Jogo jogo = jd.findJogoById(id);
		Usuario u = (Usuario) model.get("usuarioLogado");
		Classificacao classificacao = cd.listarClassificacaoPorJogo(id);
		if (!classificacao.getStatus()) {
			classificacao.setNomeClassificacao("Excluido");
		}
		Desenvolvedora desenvolvedora = ddao.listarDesenvolvedoraPorJogo(id);
		if (!desenvolvedora.getStatus()) {
			desenvolvedora.setNomeDesenvolvedora("Excluido");
		}
		Diretor diretor = dd.listarDiretorPorJogo(id);
		if (!diretor.getStatus()) {
			diretor.setNomeDiretor("Excluido");
		}
		ModoJogo modoJogo = mj.listarModoPorJogo(id);
		if (!modoJogo.getStatus()) {
			modoJogo.setNomeModo("Excluido");
		}
		Iterable<Genero> generos = gd.listarGenerosPorJogo(id);
		Iterable<Plataforma> plataformas = pd.listarPlataformasPorJogo(id);
		Iterable<Usuario> usuarios = ud.findJogadoresJogo(id);
		ModelAndView mv = new ModelAndView("detalheJogo");
		Integer i = ud.verificarSeJaEstaNaLista(u.getIdUsuario(), id);
		Integer j = ud.verificarContagemReviews(u.getIdUsuario());
		mv.addObject("validador",i);
		mv.addObject("validadorR",j);
		mv.addObject("jogo",jogo);
		mv.addObject("usuarios",usuarios);
		mv.addObject("classificacao",classificacao);
		mv.addObject("desenvolvedora",desenvolvedora);
		mv.addObject("diretor",diretor);
		mv.addObject("modoJogo",modoJogo);
		mv.addObject("generos",generos);
		mv.addObject("plataformas",plataformas);
		mv.addObject("avg",rd.pegarAVGReview(id));
		mv.addObject("reviews",rd.pegarReviewsByJogo(id));
		mv.addObject("jogosJogados",jd.findJogosPorUsuario(u.getIdUsuario()));
		return mv;
	}

	@GetMapping(value = "/gerenciarJogos")
	public ModelAndView gerenciarJogos(ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
		mv = new ModelAndView("acessoProibido");
			return mv;
		}
		mv = new ModelAndView("gerenciarJogos");
		Iterable<Jogo> jogos = jd.findAll();
		mv.addObject("jogos", jogos);
		return mv;
	}
	
	@ResponseBody
	@GetMapping(value = "editarJogo/{id}")
	public ModelAndView editarJogo(@PathVariable("id") int id,ModelMap model) {
		ModelAndView mv;
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
		mv = new ModelAndView("acessoProibido");
			return mv;
		}
		Jogo jogo = jd.findJogoById(id);
		mv = new ModelAndView("editarJogo");
		mv.addObject("jogo", jogo);
		Iterable<Plataforma> plataformasSelec = pd.listarPlataformasPorJogo(id);
		Iterable<Genero> generos = gd.findAll();
		Iterable<Plataforma> plataformas = pd.findAll();
		Iterable<Diretor> diretores = dd.findAll();
		Iterable<Desenvolvedora> desenvolvedoras = ddao.findAll();
		Iterable<ModoJogo> modoJogos = mj.findAll();
		Iterable<Classificacao> classificacoes = cd.findAll();
		model.addAttribute("classificacao", new Classificacao());
		model.addAttribute("diretor", new Diretor());
		model.addAttribute("desenvolvedora", new Desenvolvedora());
		model.addAttribute("modoJogo", new ModoJogo());
		mv.addObject("classificacoes", classificacoes);
		mv.addObject("plataformasSelecionadas", plataformasSelec);
		mv.addObject("generos", generos);
		mv.addObject("plataformas", plataformas);
		mv.addObject("diretores", diretores);
		mv.addObject("desenvolvedoras", desenvolvedoras);
		mv.addObject("modoJogos", modoJogos);
		return mv;
	}
	@ResponseBody
	@PostMapping(value = "editarJogo/{id}")
	public String editarJogo(@PathVariable("id") int id,@Valid Jogo jogo,@ModelAttribute("classificacao") Classificacao classificacao,@ModelAttribute("diretor") Diretor diretor,
			@ModelAttribute("desenvolvedora") Desenvolvedora desenvolvedora, @ModelAttribute("modoJogo")ModoJogo modoJogo,@RequestParam List<Plataforma> plataformas
			,@RequestParam List<Genero> generos,@RequestParam("file") MultipartFile file,RedirectAttributes atributos,BindingResult results) {
		jogo.setIdJogo(id);
		jogo.setDesenvolvedora(desenvolvedora);
		jogo.setDiretor(diretor);
		jogo.setModoJogo(modoJogo);
		jogo.setClassificacao(classificacao);
		jogo.setPlataformas(plataformas);
		jogo.setGeneros(generos);
		 try {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Random random = new Random();
	            /*Path path = Paths.get("/home/superior/eclipse-workspace/omg/src/main/resources/templates/" + 
	            		"" + file.getOriginalFilename());*/
	            String url = random.nextInt(20) + file.getOriginalFilename();
	            Path path = Paths.get("C:\\\\Users\\\\User\\\\Desktop\\\\tudo\\\\trabalhoespaço\\\\omg\\\\src\\\\main\\\\resources\\\\templates\\\\" + 
	            		"" + url);
	            Files.write(path, bytes);
	            jogo.setFoto(url);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
				try {
					jd.save(jogo);
					atributos.addFlashAttribute("mensagem","Jogo adicionado com sucesso");	
				}catch(Exception e){
					atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar jogo. Verifique os campos");	
					return "redirect:/cadastrarJogo";
				}
			
		return "redirect:/gerenciarJogos";
	}

	@GetMapping("excluirJogo/{id}")
	public String excluirJogo(@PathVariable("id") int id,ModelMap model,RedirectAttributes atributos) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u.getTipoUsuario().getIdTipoUsuario() == 2) {
			return "acessoProibido";
		}
		try {
			jd.excluirJogo(id);
			atributos.addFlashAttribute("mensagem","Jogo excluído com sucesso");	
		}catch(Exception e){
			atributos.addFlashAttribute("mensagemErro","Erro ao excluir jogo.");	
			return "redirect:/gerenciarJogos";
		}
		return "redirect:/gerenciarJogos";

	}
	
	@GetMapping("buscarJogo")
	public ModelAndView buscarJogo(@RequestParam("palavra") String palavra) {
		Iterable<Jogo> jogos = null;
		jogos = jd.findJogoByPalavra(palavra);
		ModelAndView mv = new ModelAndView("resultadoPesquisa");
		if(jogos != null) {
			mv.addObject("jogos",jogos);
		}
		else {
			jogos = null;
			mv.addObject("jogos",jogos);

		}
		mv.addObject("palavra",palavra);
		return mv;
	}

}
