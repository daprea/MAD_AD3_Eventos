package eventos.modelo.dao;

import eventos.modelo.entity.Evento;
import eventos.modelo.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventoDaoImpl implements EventoDao{

	@Autowired
	EventoRepository eventoRepository;

	@Override
	public List<Evento> buscarTodos() {
		return eventoRepository.findAll();
	}

	@Override
	public Evento buscarEvento(int idEvento) {
		return eventoRepository.findById(idEvento).get();
	}

	@Override
	public List<Evento> verActivos() {
		return eventoRepository.verActivos();
	}

	@Override
	public List<Evento> verDestacados() {
		return eventoRepository.verDestacados();
	}

	@Override
	public List<Evento> mostrarTipo(String nombre) {
		return null;
	}

	@Override
	public int reservarEvento(int idEvento, int cantidad) {
		return 0;
	}

	@Override
	public List<Evento> filtrarPorTipo(String tipo) {
		return eventoRepository.filtrarPorTipo(tipo);
	}
}
