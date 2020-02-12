package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import model.Genero;

@Repository
public interface GeneroDAO extends CrudRepository<Genero, Integer> {
	Genero findById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Genero g SET g.status = 0 WHERE g.idGenero = :id")
	void excluirGenero(@Param("id")int id);
	
	@Query(value = "SELECT * FROM genero INNER JOIN jogo_genero USING(id_genero) WHERE id_jogo = :id",nativeQuery = true)
	Iterable<Genero> listarGenerosPorJogo(@PathVariable("id") int id);
	
	@Query("SELECT g FROM Genero g WHERE g.status = true ORDER BY g.nomeGenero ASC")
	Iterable<Genero> findAll();
}
