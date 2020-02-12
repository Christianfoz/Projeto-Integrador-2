package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import model.Diretor;

public interface DiretorDAO extends CrudRepository<Diretor, Integer> {
	Diretor findById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Diretor d SET d.status = 0 WHERE d.idDiretor = :id")
	void excluirDiretor(@Param("id")int id);
	
	@Query(value = "SELECT * FROM diretor INNER JOIN jogo USING(id_diretor) WHERE id_jogo = :id",nativeQuery = true)
	Diretor listarDiretorPorJogo(@PathVariable("id") int id);
	
	@Query("SELECT d FROM Diretor d WHERE d.status = 1 ORDER BY d.nomeDiretor ASC")
	Iterable<Diretor> findAll();

}
