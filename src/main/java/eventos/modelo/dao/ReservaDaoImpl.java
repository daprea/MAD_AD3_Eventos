package eventos.modelo.dao;

import eventos.modelo.entity.Reserva;
import org.springframework.stereotype.Repository;

@Repository
public class ReservaDaoImpl implements ReservaDao{


	@Override
	public int cancelarReserva(int idReserva) {
		return 0;
	}

	@Override
	public Reserva buscarUna(int idReserva) {
		return null;
	}

	@Override
	public boolean reservarEvento(Reserva reserva) {
		return false;
	}

	@Override
	public int reservarEventoV2(Reserva reserva) {
		return 0;
	}
}
