package eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.ReservaDao;
import eventos.modelo.entity.Evento;
import eventos.modelo.entity.Reserva;
import eventos.modelo.entity.Usuario;

@Controller
@RequestMapping("/reservas")
public class ReservasController {
	
	@Autowired
	ReservaDao reservaDao;
	
	@Autowired
	EventoDao eventoDao;

	@GetMapping("/consultarReservas")
	public String consultarReservas(Model model, Authentication aut) {
		
		//String username = aut.getName();
		String username = "daprea";
		
		List<Reserva> reservas = reservaDao.consultarReservas(username);
		
		model.addAttribute("reservas", reservas);
		model.addAttribute("usuario", aut);
		return "reservas";
	}

	@GetMapping("/reservar")
	public String reservar( @RequestParam (name="idEvento") String idEvento, 
		@RequestParam (name="numeroEntradas") int numeroEntradas, 
		Authentication aut, Model model, RedirectAttributes ratt) {
		
		Evento evento =eventoDao.buscarEvento(Integer.parseInt(idEvento));
		//String usuario = aut.getName();
		String username = "daprea";

		Reserva reserva = reservaDao.buscarReserva(idEvento, username);
 
		int codigo = reservaDao.reservarEvento(reserva, evento, numeroEntradas);
		
		switch (codigo) {
		//	La reserva se puede realizar
		case 0:
			reserva = new Reserva();	        
			reserva.setEvento(evento);
			reserva.setCantidad(numeroEntradas);

			Usuario usuario = new Usuario();
			usuario.setUsername(username);
			reserva.setUsuario(usuario);

			evento.setAforoMaximo(evento.getAforoMaximo()-numeroEntradas);

			eventoDao.save(evento);
			reservaDao.save(reserva);
			break;
		case 1:
			model.addAttribute("mensaje", "No pudo realizarse la reserva: aforo maximo excedido");
			break;
		case 2:
			model.addAttribute("mensaje", "ya tenía una reserva para este evento");
			break;
		case 3:
			model.addAttribute("mensaje", "No pudo realizarse la reserva: No permitir más de 10 entradas en una reserva");
			break;
		case 4:
			model.addAttribute("mensaje", "No pudo realizarse la reserva: El evento selecionado no existe");
			break;

		}

		List<Reserva> reservas = reservaDao.consultarReservas(username);
		model.addAttribute("reservas", reservas);
		
		return "reservas";
	}

	@GetMapping("/cancelar/{idRFeserva}")
	public String cancelarReserva( Model model, @PathVariable (name="idRFeserva") String idRFeserva, Authentication aut) {
		Reserva reserva = reservaDao.buscarReserva(Integer.parseInt(idRFeserva));
		if(reserva!=null){
			reservaDao.cancelar(reserva);
		}

		//String username = aut.getName();
		String username = "daprea";
		
		List<Reserva> reservas = reservaDao.consultarReservas(username);
		
		model.addAttribute("reservas", reservas);
		model.addAttribute("usuario", aut);
		
		return "reservas";
	}
	
	

}
