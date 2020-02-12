package model;

import java.util.Calendar;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "jogo")
public class Jogo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idJogo;
	@Column(name = "titulo", nullable = false, length = 70)
	private String titulo;
	@Column(name = "ano", nullable = false)
	private Integer ano;
	@Column(name = "info", nullable = false)
	private String info;
	@Column(name = "status", nullable = true)
	private Boolean status = true;
	@Column(name = "foto", nullable = true)
	private String foto;
	@ManyToOne
	@JoinColumn(name = "id_classificacao")
	private Classificacao classificacao;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_desenvolvedora")
	private Desenvolvedora desenvolvedora;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_diretor")
	private Diretor diretor;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_modo")
	private ModoJogo modoJogo;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "jogo_plataforma", joinColumns = { @JoinColumn(name = "id_jogo") }, inverseJoinColumns = {
			@JoinColumn(name = "id_plataforma") })
	private List<Plataforma> plataformas;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "jogo_genero", joinColumns = { @JoinColumn(name = "id_jogo") }, inverseJoinColumns = {
			@JoinColumn(name = "id_genero") })
	private List<Genero> generos;
	@ManyToMany(mappedBy = "jogosJogados")
	private List<Usuario> usuarios;
	@OneToMany(mappedBy = "jogo")
	private List<Review> reviews;
	@Temporal(TemporalType.DATE)
	private Calendar dataCadastro = Calendar.getInstance();


	public Jogo(Integer idJogo, String titulo, Integer ano, String info, Classificacao classificacao,
			Desenvolvedora desenvolvedora, Diretor diretor, ModoJogo modoJogo, List<Plataforma> plataformas,
			List<Genero> generos) {
		super();
		this.idJogo = idJogo;
		this.titulo = titulo;
		this.ano = ano;
		this.info = info;
		this.classificacao = classificacao;
		this.desenvolvedora = desenvolvedora;
		this.diretor = diretor;
		this.modoJogo = modoJogo;
		this.plataformas = plataformas;
		this.generos = generos;
	}

	public Jogo() {
		super();
	}

	public Integer getIdJogo() {
		return idJogo;
	}

	public void setIdJogo(Integer idJogo) {
		this.idJogo = idJogo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Classificacao getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}

	public Desenvolvedora getDesenvolvedora() {
		return desenvolvedora;
	}

	public void setDesenvolvedora(Desenvolvedora desenvolvedora) {
		this.desenvolvedora = desenvolvedora;
	}

	public Diretor getDiretor() {
		return diretor;
	}

	public void setDiretor(Diretor diretor) {
		this.diretor = diretor;
	}

	public ModoJogo getModoJogo() {
		return modoJogo;
	}

	public void setModoJogo(ModoJogo modoJogo) {
		this.modoJogo = modoJogo;
	}

	public List<Plataforma> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(List<Plataforma> plataformas) {
		this.plataformas = plataformas;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
