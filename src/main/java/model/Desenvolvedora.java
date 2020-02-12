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
@Table(name = "desenvolvedora")
public class Desenvolvedora {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDesenvolvedora;
	@Column(name = "nome_desenvolvedora", nullable = false, length = 30)
	private String nomeDesenvolvedora;
	@OneToMany(mappedBy = "desenvolvedora")
	private List<Jogo> jogos;
	@Column(name = "status", nullable = true, columnDefinition = " boolean DEFAULT true")
	private Boolean status = true;

	public Desenvolvedora(Integer idDesenvolvedora, String nomeDesenvolvedora, List<Jogo> jogos, Boolean status) {
		super();
		this.idDesenvolvedora = idDesenvolvedora;
		this.nomeDesenvolvedora = nomeDesenvolvedora;
		this.jogos = jogos;
		this.status = status;
	}

	public Desenvolvedora() {

	}

	public Integer getIdDesenvolvedora() {
		return idDesenvolvedora;
	}

	public void setIdDesenvolvedora(Integer idDesenvolvedora) {
		this.idDesenvolvedora = idDesenvolvedora;
	}

	public String getNomeDesenvolvedora() {
		return nomeDesenvolvedora;
	}

	public void setNomeDesenvolvedora(String nomeDesenvolvedora) {
		this.nomeDesenvolvedora = nomeDesenvolvedora;
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
