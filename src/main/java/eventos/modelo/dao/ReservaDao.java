package eventos.modelo.dao;

import java.util.List;

import eventos.modelo.entity.Evento;
import eventos.modelo.entity.Reserva;

public interface ReservaDao {

	public Reserva buscarReserva(int idReserva);
	
	List<Reserva> consultarReservas(String username);

	public void cancelar(Reserva reserva);

    	public void save(Reserva reserva);

    	public int reservarEvento(Reserva reserva, Evento evento, int numeroEntradas);

	public Reserva buscarReserva(String idEvento, String username);
	
}
