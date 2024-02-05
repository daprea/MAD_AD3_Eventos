package eventos.modelo.dao;

import java.util.List;

import eventos.modelo.entity.Evento;

public interface EventoDao {
	
	List<Evento> buscarTodos();
	Evento buscarEvento(int idEvento);
	List<Evento> verActivos();
	List<Evento> verDestacados();
	List<Evento> filtrarPorTipo(String tipo);
   	void save(Evento evento);
}
