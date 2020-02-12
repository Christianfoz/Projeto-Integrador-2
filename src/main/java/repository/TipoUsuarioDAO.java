package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.TipoUsuario;

@Repository
public interface TipoUsuarioDAO extends CrudRepository<TipoUsuario, Integer> {
	TipoUsuario findById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE TipoUsuario tu SET tu.status = 0 WHERE tu.idTipoUsuario = :id")
	void excluirTipoUsuario(@Param("id")int id);
	
	@Query("SELECT tu FROM TipoUsuario tu WHERE tu.status = true")
	Iterable<TipoUsuario> findAll();
}
