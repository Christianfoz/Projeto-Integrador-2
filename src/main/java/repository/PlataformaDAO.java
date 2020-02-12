package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import model.Plataforma;

@Repository
public interface PlataformaDAO extends CrudRepository<Plataforma, Integer> {
	Plataforma findById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Plataforma p SET p.status = 0 WHERE p.idPlataforma = :id")
	void excluirPlataforma(@Param("id")int id);
	
	@Query(value = "SELECT id_plataforma, nome_plataforma, status FROM plataforma INNER JOIN jogo_plataforma USING(id_plataforma) WHERE id_jogo = :id",nativeQuery = true)
	Iterable<Plataforma> listarPlataformasPorJogo(@PathVariable("id") int id);
	
	@Query("SELECT p FROM Plataforma p WHERE p.status = true ORDER BY p.nomePlataforma ASC")
	Iterable<Plataforma> findAll();

}
