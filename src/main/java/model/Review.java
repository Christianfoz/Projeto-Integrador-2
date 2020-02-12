package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idReview;
	@Column(name = "texto_review", length = 1000, nullable = false,columnDefinition = "TEXT")
	@Size(max = 1000)
	private String textoReview;
	@Column(name = "nota",nullable = false)
	private Integer nota;
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	@Column(name = "status",nullable = true)
	private Boolean status = false;
	@Temporal(TemporalType.DATE)
	private Date dataCadastroReview;
	@ManyToOne
	@JoinColumn(name = "id_jogo")
	private Jogo jogo;

	public Review(Integer idReview, String textoReview, Integer nota, Usuario usuario, Jogo jogo) {
		super();
		this.idReview = idReview;
		this.textoReview = textoReview;
		this.nota = nota;
		this.usuario = usuario;
		this.jogo = jogo;
	}

	public Review() {

	}

	public Integer getIdReview() {
		return idReview;
	}

	public void setIdReview(Integer idReview) {
		this.idReview = idReview;
	}

	public String getTextoReview() {
		return textoReview;
	}

	public void setTextoReview(String textoReview) {
		this.textoReview = textoReview;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getDataCadastroReview() {
		return dataCadastroReview;
	}

	public void setDataCadastroReview(Date dataCadastro) {
		this.dataCadastroReview = dataCadastro;
	}
	
	
	
	

}
