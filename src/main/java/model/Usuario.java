package model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	@Column(name = "apelido", nullable = false, length = 20, unique = true)
	private String apelido;
	@Column(name = "email", nullable = false, length = 50, unique = true)
	private String email;
	@Size(message = "A senha deve ter no mínimo 6 e no máximo 10 caracteres")
	@Column(name = "senha", nullable = false, length = 100)
	private String senha;
	@Column(name = "status", nullable = true, columnDefinition = " boolean DEFAULT true")
	private Boolean status = true;
	@Column(name = "foto", nullable = true)
	private String foto;
	@Temporal(TemporalType.DATE)
	private Calendar dataCadastro = Calendar.getInstance();
	@ManyToOne
	@JoinColumn(name = "id_tipo")
	private TipoUsuario tipoUsuario;
	@OneToMany(mappedBy = "usuario")
	private List<Review> reviews;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "lista_jogado", joinColumns = { @JoinColumn(name = "id_jogo") }, inverseJoinColumns = {
			@JoinColumn(name = "id_usuario") })
	private List<Jogo> jogosJogados;
	@OneToMany(mappedBy = "usuario")
	private List<Sugestao> sugestoes;
	public Usuario(Integer idUsuario, String apelido, String email, String senha, Boolean status, String foto,
			TipoUsuario tipoUsuario, List<Review> reviews, List<Jogo> jogosJogados, List<Sugestao> sugestoes) {
		super();
		this.idUsuario = idUsuario;
		this.apelido = apelido;
		this.email = email;
		this.senha = senha;
		this.status = status;
		this.foto = foto;
		this.tipoUsuario = tipoUsuario;
		this.reviews = reviews;
		this.jogosJogados = jogosJogados;
		this.sugestoes = sugestoes;
	}

	public Usuario() {

	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String nick) {
		this.apelido = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Jogo> getJogosJogados() {
		return jogosJogados;
	}

	public void setJogosJogados(List<Jogo> jogosJogados) {
		this.jogosJogados = jogosJogados;
	}

	public List<Sugestao> getSugestoes() {
		return sugestoes;
	}

	public void setSugestoes(List<Sugestao> sugestoes) {
		this.sugestoes = sugestoes;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	

}
