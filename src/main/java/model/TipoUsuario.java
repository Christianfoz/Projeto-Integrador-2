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
@Table(name = "tipo_usuario")
public class TipoUsuario {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer idTipoUsuario;
	@Column(name = "tipo", nullable = false, length = 15)
	private String tipo;
	@OneToMany(mappedBy = "tipoUsuario")
	private List<Usuario> usuarios;
	@Column(name = "status", nullable = true, columnDefinition = " boolean DEFAULT true")
	private Boolean status = true;

	public TipoUsuario(Integer idTipoUsuario, String tipoUsuario, Boolean status) {
		super();
		this.idTipoUsuario = idTipoUsuario;
		this.tipo = tipoUsuario;
		this.status = status;
	};

	public TipoUsuario() {

	}

	public Integer getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(Integer idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipoUsuario) {
		this.tipo = tipoUsuario;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
