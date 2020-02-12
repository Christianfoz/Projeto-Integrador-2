package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import model.Jogo;


public interface JogoDAO extends CrudRepository<Jogo, Integer> {
	@Query("SELECT j FROM Jogo j WHERE j.status = true ORDER BY j.titulo ASC")
	Iterable<Jogo> findAll();
	
	@Transactional
	@Modifying
	@Query("UPDATE Jogo j SET j.status = 0 WHERE j.idJogo = :id")
	void excluirJogo(@Param("id")int id);
	
	@Query(value = "SELECT * FROM jogo INNER JOIN lista_jogado USING(id_jogo) WHERE id_usuario = :id",nativeQuery = true)
	Iterable<Jogo> findJogosPorUsuario(@Param("id") int id);
	
	@Query("SELECT j FROM Jogo j WHERE j.idJogo = :id")
	Jogo findJogoById(@Param("id")Integer id);
	
	@Query("SELECT j FROM Jogo j WHERE j.status = true AND j.classificacao.idClassificacao = :id ORDER BY j.titulo ASC")
	Iterable<Jogo> findJogoByClassificacao(@Param("id")int id);
	
	@Query("SELECT j FROM Jogo j WHERE j.status = true AND j.diretor.idDiretor = :id ORDER BY j.titulo ASC")
	Iterable<Jogo> findJogoByDiretor(@Param("id") int id);
 	
	@Query(value = "SELECT * FROM jogo j INNER JOIN jogo_genero USING(id_jogo) WHERE j.status = true AND id_genero = :id ORDER BY titulo ASC",nativeQuery = true)
	Iterable<Jogo> findJogoByGenero(@Param("id") int id);
	
	@Query(value = "SELECT * FROM jogo j WHERE j.status = true AND id_modo = :id ORDER BY titulo ASC",nativeQuery = true)
	Iterable<Jogo> findJogoByModo(@Param("id") int id);
	
	@Query(value = "SELECT * FROM jogo j INNER JOIN jogo_plataforma USING(id_jogo) WHERE j.status = true AND id_plataforma = :id ORDER BY titulo ASC",nativeQuery = true)
	Iterable<Jogo> findJogoByPlataforma(@Param("id") int id);
	
	@Query("SELECT j FROM Jogo j WHERE j.status = true AND j.desenvolvedora.idDesenvolvedora = :id ORDER BY j.titulo ASC")
	Iterable<Jogo> findJogoByDesenvolvedora(@Param("id") int id);
	
	@Query("SELECT j FROM Jogo j WHERE status = true AND j.titulo LIKE %:palavra%")
	Iterable<Jogo> findJogoByPalavra(@Param("palavra") String palavra);
	
	@Query(value = "SELECT * FROM jogo WHERE status = true ORDER BY data_cadastro DESC LIMIT 10",nativeQuery = true)
	Iterable<Jogo> findDezUltimosJogos();
	

}
