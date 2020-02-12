package repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Jogo;
import model.Review;

@Repository
public interface ReviewDAO extends CrudRepository<Review, Integer> {
	@Query("SELECT r FROM Review r WHERE r.status = true")
	Iterable<Review> findReviewsTrue();

	@Query("SELECT r FROM Review r WHERE r.status = false")
	Iterable<Review> findReviewsFalse();

	@Query("SELECT r FROM Review r WHERE r.idReview = :id")
	Review findById(@Param("id") int id);
	
	@Query("SELECT r FROM Review r WHERE r.usuario.idUsuario = :id")
	Iterable<Review> findReviewPorUsuario(@Param("id") int id);

	@Transactional
	@Modifying
	@Query("UPDATE Review r SET r.status = 0 WHERE r.idReview = :id")
	void excluirReview(@Param("id") int id);

	@Query(value = "SELECT AVG(nota) FROM review r WHERE id_jogo = :id AND status = true", nativeQuery = true)
	Double pegarAVGReview(@Param("id") int id);

	@Query(value = "SELECT * FROM review r INNER JOIN jogo j USING(id_jogo) WHERE j.id_jogo = :id and r.status = true",nativeQuery = true)
	Iterable<Review> pegarReviewsByJogo(@Param("id") int id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Review r SET r.status = true WHERE r.idReview = :id")
	void aprovarReview(@Param("id") int id);
	
	@Query("SELECT r.jogo FROM Review r WHERE r.idReview = :id")
	Jogo findJogoByReviewId(@Param("id") int id);
}
