package eventos.modelo.repository;

import eventos.modelo.entity.Reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT r FROM Reserva r WHERE r.usuario.username= :username")
    List<Reserva> consultarReservas(String username);

    @Query("SELECT r FROM Reserva r WHERE r.evento.idEvento= ?1 AND r.usuario.username= ?2")
    Reserva buscarReserva(String idEvento, String username);
}
