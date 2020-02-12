package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "modo_jogo")
public class ModoJogo {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer idModo;
	@Column(name = "modo",length = 20, nullable = false)
	private String nomeModo;
	@OneToMany(mappedBy = "modoJogo")
	private List<Jogo> jogos;
	@Column(name = "status", nullable = true, columnDefinition = " boolean DEFAULT true")
	private Boolean status  = true;

	public ModoJogo(Integer idModo, String nomeModo, Boolean status) {
		super();
		this.idModo = idModo;
		this.nomeModo = nomeModo;
		this.status = status;
	}

	public ModoJogo() {

	}

	public Integer getIdModo() {
		return idModo;
	}

	public void setIdModo(Integer idModo) {
		this.idModo = idModo;
	}

	public String getNomeModo() {
		return nomeModo;
	}

	public void setNomeModo(String nomeModo) {
		this.nomeModo = nomeModo;
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
