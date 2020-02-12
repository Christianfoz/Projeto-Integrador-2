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
@Table(name = "plataforma")
public class Plataforma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPlataforma;
	@Column(name = "nome_plataforma", length = 30, nullable = false)
	private String nomePlataforma;
	@Column(name = "ano_lancamento", nullable = true)
	private Integer anoLancamento;
	@Column(name = "status", nullable = true, columnDefinition = " boolean DEFAULT true")
	private Boolean status = true;
	@ManyToMany(mappedBy = "plataformas")
	private List<Jogo> jogos;

	

	public Plataforma(Integer idPlataforma, String nomePlataforma, Integer anoLancamento, Boolean status,
			List<Jogo> jogos) {
		super();
		this.idPlataforma = idPlataforma;
		this.nomePlataforma = nomePlataforma;
		this.anoLancamento = anoLancamento;
		this.status = status;
		this.jogos = jogos;
	}

	public Plataforma() {
		super();
	}

	public Integer getIdPlataforma() {
		return idPlataforma;
	}

	public void setIdPlataforma(Integer idPlataforma) {
		this.idPlataforma = idPlataforma;
	}

	public String getNomePlataforma() {
		return nomePlataforma;
	}

	public void setNomePlataforma(String nomePlataforma) {
		this.nomePlataforma = nomePlataforma;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(Integer anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}
	
	

}
