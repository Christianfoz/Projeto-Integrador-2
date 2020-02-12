package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "classificacao")
public class Classificacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idClassificacao;
	@Column(name = "nome_classificacao", nullable = false, length = 10)
	@NotEmpty
	private String nomeClassificacao;
	@OneToMany(mappedBy = "classificacao")
	private List<Jogo> jogos;
	@Column(name = "status", nullable = true, columnDefinition = " boolean DEFAULT true")
	private Boolean status = true;


	public Classificacao(Integer idClassificacao, String nomeClassificacao, List<Jogo> jogos, Boolean status) {
		super();
		this.idClassificacao = idClassificacao;
		this.nomeClassificacao = nomeClassificacao;
		this.jogos = jogos;
		this.status = status;
	}

	public Classificacao() {

	}

	public Integer getIdClassificacao() {
		return idClassificacao;
	}

	public void setIdClassificacao(Integer idClassificacao) {
		this.idClassificacao = idClassificacao;
	}

	public String getNomeClassificacao() {
		return nomeClassificacao;
	}

	public void setNomeClassificacao(String nomeClassificacao) {
		this.nomeClassificacao = nomeClassificacao;
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
