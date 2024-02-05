package eventos.modelo.dao;

import eventos.modelo.entity.Evento;
import eventos.modelo.entity.Reserva;
import eventos.modelo.repository.ReservaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReservaDaoImpl implements ReservaDao{

	@Autowired
	ReservaRepository reservaRepository; 

	@Override
	public Reserva buscarReserva(int idReserva) {
		return reservaRepository.findById(idReserva).get();
	}
	@Override
	public Reserva buscarReserva(String idEvento, String username) {
		return reservaRepository.buscarReserva(idEvento, username);
	}
	@Override
	public List<Reserva> consultarReservas(String username) {
		return reservaRepository.consultarReservas(username);
	}
	@Override
	public void cancelar(Reserva reserva) {
		reservaRepository.delete(reserva);
	}
	@Override
	public void save(Reserva reserva) {
		reservaRepository.save(reserva);
	}
	@Override
	public int reservarEvento(Reserva reserva, Evento evento, int numeroEntradas) {
		int opcion = 0;
		if (evento==null){
			opcion = 4;
		}else{
			if(numeroEntradas>evento.getAforoMaximo()){
				opcion = 1;
			}else{
				if(reserva!=null){
					opcion = 2;
				}else{
					if(numeroEntradas > 10){
						opcion = 3;
					}
				}
			}
		}

		return opcion;
	}

}
