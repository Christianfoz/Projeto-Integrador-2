package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import model.ModoJogo;

@Repository
public interface ModoJogoDAO extends CrudRepository<ModoJogo, Integer> {
	ModoJogo findById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE ModoJogo mj SET mj.status = 0 WHERE mj.idModo = :id")
	void excluirModoJogo(@Param("id")int id);

	@Query(value = "SELECT * FROM modo_jogo INNER JOIN jogo USING(id_modo) WHERE id_jogo = :id",nativeQuery = true)
	ModoJogo listarModoPorJogo(@PathVariable("id") int id);
	
	@Query("SELECT mj FROM ModoJogo mj WHERE mj.status = true ORDER BY mj.nomeModo ASC")
	Iterable<ModoJogo> findAll();
}
