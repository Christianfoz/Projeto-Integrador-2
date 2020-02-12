package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import model.Desenvolvedora;

@Repository
public interface DesenvolvedoraDAO extends CrudRepository<Desenvolvedora, Integer> {
	Desenvolvedora findById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Desenvolvedora d SET d.status = 0 WHERE d.idDesenvolvedora = :id")
	void excluirDesenvolvedora(@Param("id")int id);
	
	@Query(value = "SELECT * FROM desenvolvedora INNER JOIN jogo USING(id_desenvolvedora) WHERE id_jogo = :id",nativeQuery = true)
	Desenvolvedora listarDesenvolvedoraPorJogo(@PathVariable("id") int id);
	
	@Query("SELECT d FROM Desenvolvedora d WHERE d.status = true ORDER BY d.nomeDesenvolvedora ASC")
	Iterable<Desenvolvedora> findAll();

}
