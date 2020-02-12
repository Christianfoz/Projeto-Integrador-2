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
@Table(name = "diretor")
public class Diretor {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer idDiretor;
	@Column(name = "nome_diretor",nullable = false, length = 30)
	private String nomeDiretor;
	@Column(name = "status", nullable = true, columnDefinition = "boolean DEFAULT true")
	private Boolean status  = true;
	@OneToMany(mappedBy = "diretor")
	private List<Jogo> jogos;

	

	public Diretor(Integer idDiretor, String nomeDiretor, Boolean status, List<Jogo> jogos) {
		super();
		this.idDiretor = idDiretor;
		this.nomeDiretor = nomeDiretor;
		this.status = status;
		this.jogos = jogos;
	}

	public Diretor() {

	}

	public Integer getIdDiretor() {
		return idDiretor;
	}

	public void setIdDiretor(Integer idDiretor) {
		this.idDiretor = idDiretor;
	}

	public String getNomeDiretor() {
		return nomeDiretor;
	}

	public void setNomeDiretor(String nomeDiretor) {
		this.nomeDiretor = nomeDiretor;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
