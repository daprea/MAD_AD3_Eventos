package eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.ReservaDao;
import eventos.modelo.entity.Evento;
import eventos.modelo.entity.Reserva;
import eventos.modelo.entity.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	EventoDao eventodao;
	
	@Autowired
	ReservaDao redao;
	
	
	@GetMapping("/detalle/{idEvento}")
	public String verEvento(Model model, @PathVariable (name="idEvento") int idEvento) {
		Evento evento = eventodao.buscarEvento(idEvento);
		model.addAttribute("evento", evento);
		return "detalle";
	}
		 				
	
	@GetMapping("/verDetalle")
	public String verDetalles (Model model) {
		model.addAttribute("verDetalle");
	 		return "detalle";
	 }

	@GetMapping("/verActivos")
	public String verDetalleActivos (Model model) {		
		List<Evento> eventos = eventodao.verActivos();
		model.addAttribute("eventos", eventos);
		return "home";
	}
	
	@GetMapping("/verDestacados")
	public String verDestacados( Model model) {
		
		List<Evento> eventos = eventodao.verDestacados();
		model.addAttribute("eventos", eventos);
		return "home";
	}

	@GetMapping("/filtrarPorTipo/{tipo}")
	public String filtrarPorTipo( Model model, @PathVariable (name="tipo") String tipo) {

		List<Evento> eventos = null;
		if(tipo.equalsIgnoreCase("Todos") || tipo.equalsIgnoreCase("-1")){
			eventos = eventodao.buscarTodos();
		}else{
			eventos = eventodao.filtrarPorTipo(tipo);
		}
		model.addAttribute("eventos", eventos);
		return "home";
	}

	

}
	



