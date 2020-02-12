package controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Jogo;
import model.TipoUsuario;
import model.Usuario;
import repository.ClassificacaoDAO;
import repository.DesenvolvedoraDAO;
import repository.DiretorDAO;
import repository.GeneroDAO;
import repository.JogoDAO;
import repository.ModoJogoDAO;
import repository.PlataformaDAO;
import repository.ReviewDAO;
import repository.SugestaoDAO;
import repository.UsuarioDAO;

@Controller
@SessionAttributes("usuarioLogado")

public class UsuarioController {
	@Autowired(required = true)
	private UsuarioDAO ud;
	@Autowired(required = true)
	private DiretorDAO dd;
	@Autowired(required = true)
	private DesenvolvedoraDAO ddao;
	@Autowired(required = true)
	private ClassificacaoDAO cd;
	@Autowired(required = true)
	private GeneroDAO gd;
	@Autowired(required = true)
	private JogoDAO jd;
	@Autowired(required = true)
	private ReviewDAO rd;
	@Autowired(required = true)
	private SugestaoDAO sd;
	@Autowired(required = true)
	private ModoJogoDAO md;
	@Autowired(required = true)
	private PlataformaDAO pd;
	
	
	
	@GetMapping(value = "/cadastroUsuario")
	public String formCadastro() {
		return "cadastroUsuario";
	}
	
	@PostMapping(value = "/cadastroUsuario")
	public String cadastroUsuario(Usuario usuario,@RequestParam("file") MultipartFile file,BindingResult result,RedirectAttributes atributos) {
		TipoUsuario tipoUsuario = new TipoUsuario();
		tipoUsuario.setIdTipoUsuario(2);
		usuario.setTipoUsuario(tipoUsuario);
		try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Random random = new Random();
            String url = random.nextInt(20) + file.getOriginalFilename();
            Path path = Paths.get("C:\\Users\\User\\Desktop\\tudo\\trabalhoespaço\\omg\\src\\main\\resources\\templates\\" + url);
            Files.write(path, bytes);
            usuario.setFoto(url);
            Calendar calendar = Calendar.getInstance();
            usuario.setDataCadastro(calendar);
        } catch (IOException e) {
            e.printStackTrace();
        }
		if(result.hasErrors()) {
			atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar usuário. Verifique os campos");
		}
		else {
			try {
				String senha = usuario.getSenha();
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte messageDigest[] = md.digest(senha.getBytes("UTF-8"));
				StringBuilder hexString = new StringBuilder();
				for (byte b : messageDigest) {
					  hexString.append(String.format("%02X", 0xFF & b));
					}
				usuario.setSenha(hexString.toString()); 
				ud.save(usuario);
				atributos.addFlashAttribute("mensagem","Usuário cadastrado com sucesso");	
			}catch(Exception e){
				atributos.addFlashAttribute("mensagemErro","Erro ao cadastrar usuário. Verifique os campos");	
				return "redirect:/cadastroUsuario";
			}
			
		}
		ud.save(usuario);
		return "cadastroUsuario";
	}
	
	@GetMapping(value = "/login")
	public String formLoginUsuario() {
		return "formLogin";
	}
	
	@PostMapping(value = "/login")
	public String loginUsuario(ModelMap model,Usuario u) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String senha = u.getSenha();
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte messageDigest[] = md.digest(senha.getBytes("UTF-8")); 
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			} 
		Usuario u1 = ud.loginUsuario(u.getEmail(), hexString.toString());
		System.out.println(messageDigest.toString());
		if(u1.equals(null)) {
			return "login";
		}
		else {
			model.put("usuarioLogado", u1);
			return "redirect:/index";
		}
		
	}
	
	@GetMapping("/index")
	public ModelAndView mostrarIndex(@SessionAttribute("usuarioLogado") Usuario usuario) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject(usuario);
		mv.addObject("jogosNovos",jd.findDezUltimosJogos());
		mv.addObject("usuariosNovos",ud.findDezUltimosUsuarios());
		return mv;
	}
	
	@GetMapping("/logout")
	public String fazerLogout(ModelMap model) {
		model.remove("usuarioLogado");
		return "redirect:/login";
	}
	
	@GetMapping("cadastrarJogoNaSuaLista/{id}")
	public String cadastrarJogoNaSuaLista(ModelMap model,@PathVariable("id") int id) {
		Usuario u = (Usuario) model.get("usuarioLogado");
		if(u == null) {
			return "redirect:/index";
		}
		else {
			ud.cadastrarNaLista(id, u.getIdUsuario());
			return "redirect:/index";
		}
	}
	
	@GetMapping("perfil/{id}")
	public ModelAndView mostrarPerfil(@PathVariable("id") int id) {
		Usuario u = ud.findById(id);
		ModelAndView mv = new ModelAndView("perfil");
		mv.addObject("usuarioPerfil",u);
		mv.addObject("jogos",jd.findJogosPorUsuario(id));
		mv.addObject("reviews",rd.findReviewPorUsuario(id));
		mv.addObject("sugestoes",sd.findSugestoesAprovadasPorUsuario(id));
		return mv;
	}
	
	@GetMapping("buscaAvancada")
	public ModelAndView buscaAvancada() {
		ModelAndView mv = new ModelAndView("buscaAvancada");
		mv.addObject("classificacoes",cd.findAll());
		mv.addObject("diretores",dd.findAll());
		mv.addObject("desenvolvedoras",ddao.findAll());
		mv.addObject("generos",gd.findAll());
		mv.addObject("modoJogos",md.findAll());
		mv.addObject("plataformas",pd.findAll());
		return mv;
	}

	
	

}
