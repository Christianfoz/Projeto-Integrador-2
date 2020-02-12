package repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import model.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Integer>{
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email AND senha = :senha")
	Usuario loginUsuario(@Param("email")String email, @Param("senha")String senha);
	
	@Query("SELECT u FROM Usuario u WHERE u.tipoUsuario.idTipoUsuario = :id")
	Iterable<Usuario> findByTipo(@Param("id")int id);
	
	@Query("SELECT u FROM Usuario u WHERE u.idUsuario = :id")
	Usuario findById(@Param("id") int id);
	
	@Query(value = "SELECT * FROM usuario INNER JOIN lista_jogado USING(id_usuario) WHERE id_jogo = :id",nativeQuery = true)
	Iterable<Usuario> findJogadoresJogo(@Param("id") int id);
	
	@Query(value = "SELECT * FROM usuario ORDER BY data_cadastro DESC LIMIT 10",nativeQuery = true)
	Iterable<Usuario> findDezUltimosUsuarios(); 
	
	@Query(value = "SELECT COUNT(id_jogo) FROM usuario INNER JOIN lista_jogado USING (id_usuario) WHERE id_usuario = :id AND id_jogo = :idJ",nativeQuery = true)
	Integer verificarSeJaEstaNaLista(@Param("id") int idUsuario, @Param("idJ") int idJogo);
	
	@Query(value = "SELECT cadastrarnalista(:idj,:idu)",nativeQuery = true)
	void cadastrarNaLista(@Param("idj") int id, @Param("idu") int idu);

	@Query(value = "SELECT COUNT(id_review) FROM review r WHERE r.id_usuario = :id AND r.status = 1 AND r.id_jogo = :idJ",nativeQuery = true)
	Integer verificarContagemReviews(@Param("id") int idUsuario,@Param("idJ") int idJogo);

}
