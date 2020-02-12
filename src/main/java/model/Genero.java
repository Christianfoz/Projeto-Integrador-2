package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "genero")
public class Genero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idGenero;
	@Column(name = "nome_genero", nullable = false, length = 30)
	private String nomeGenero;
	@Column(name = "status", nullable = true, columnDefinition =  " boolean DEFAULT true")
	private Boolean status  = true;
	@ManyToMany(mappedBy = "generos")
	private List<Jogo> jogos;

	

	public Genero(Integer idGenero, String nomeGenero, Boolean status, List<Jogo> jogos) {
		super();
		this.idGenero = idGenero;
		this.nomeGenero = nomeGenero;
		this.status = status;
		this.jogos = jogos;
	}

	public Genero() {

	}

	public Integer getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}

	public String getNomeGenero() {
		return nomeGenero;
	}

	public void setNomeGenero(String nomeGenero) {
		this.nomeGenero = nomeGenero;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}
	
	

}
