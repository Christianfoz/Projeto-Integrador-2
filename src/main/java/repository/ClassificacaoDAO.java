package repository;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import model.Classificacao;

public interface ClassificacaoDAO extends CrudRepository<Classificacao, Integer> {
	Classificacao findById(int id);
	@Transactional
	@Modifying
	@Query("UPDATE Classificacao c SET c.status = 0 WHERE c.idClassificacao = :id")
	void excluirClassificacao(@Param("id")int id);
	
	@Query(value = "SELECT * FROM classificacao INNER JOIN jogo USING(id_classificacao) WHERE id_jogo = :id",nativeQuery = true)
	Classificacao listarClassificacaoPorJogo(@PathVariable("id") int id);
	
	@Query("SELECT c FROM Classificacao c WHERE c.status = true ")
	Iterable<Classificacao> findAll();
	
	
}
