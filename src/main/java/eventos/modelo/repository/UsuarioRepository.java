package eventos.modelo.repository;

import eventos.modelo.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query("SELECT u FROM Usuario u WHERE u.username= :username")
    Usuario buscarUsername(String username);
}
