package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import model.Sugestao;

@Repository
public interface SugestaoDAO extends CrudRepository<Sugestao, Integer>{
	@Query("SELECT s FROM Sugestao s WHERE s.status = true")
	Iterable<Sugestao> listarSugestoesInativas();
	
	@Transactional
	@Modifying
	@Query("UPDATE Sugestao s SET s.status = FALSE WHERE s.idSugestao = :id")
	void aprovarSugestao(@PathVariable("id") int id);
	
	@Query("SELECT s FROM Sugestao s WHERE s.idSugestao = :id")
	Sugestao findById(@PathVariable("id") int id);
	
	@Query("SELECT s FROM Sugestao s WHERE s.status = FALSE AND s.usuario.idUsuario = :id")
	Iterable<Sugestao> findSugestoesAprovadasPorUsuario(@Param("id") int id);
}
