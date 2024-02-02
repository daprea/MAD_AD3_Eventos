package eventos.modelo.repository;

import eventos.modelo.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  EventoRepository extends JpaRepository<Evento, Integer> {
    @Query("SELECT e FROM Evento e WHERE e.estado='ACTIVO'")
    List<Evento> verActivos();

    @Query("SELECT e FROM Evento e WHERE e.destacado='1'")
    List<Evento> verDestacados();

    @Query("SELECT e FROM Evento e WHERE e.tipo.nombre= :tipo")
    List<Evento> filtrarPorTipo(String tipo);
}
