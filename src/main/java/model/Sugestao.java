package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sugestao")
public class Sugestao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSugestao;
	@Column(name = "titulo", nullable = true,length = 50)
	private String titulo;
	@Column(name = "texto_sugestao", nullable = false, length = 200)
	private String textoSugestao;
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	@Column(name = "status", nullable = true)
	private boolean status = true;



	public Sugestao(Integer idSugestao, String titulo, String textoSugestao, Usuario usuario, boolean status) {
		super();
		this.idSugestao = idSugestao;
		this.titulo = titulo;
		this.textoSugestao = textoSugestao;
		this.usuario = usuario;
		this.status = status;
	}

	public Sugestao() {

	}

	public Integer getIdSugestao() {
		return idSugestao;
	}

	public void setIdSugestao(Integer idSugestao) {
		this.idSugestao = idSugestao;
	}

	public String getTextoSugestao() {
		return textoSugestao;
	}

	public void setTextoSugestao(String textoSugestao) {
		this.textoSugestao = textoSugestao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	

}
