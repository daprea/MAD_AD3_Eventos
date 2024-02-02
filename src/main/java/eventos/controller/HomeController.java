package eventos.controller;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.entity.Evento;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
	@Autowired
	EventoDao edao;

	@GetMapping({"", "/", "/home"})
	public String verHome(Model model) {
		List<Evento> evento = edao.buscarTodos();
		model.addAttribute("evento", evento);
		return "home";
	}

	@GetMapping("/login")
	public String ingresoUsuario(Model model, HttpSession sesion, Authentication aut) {
		
		return "login";
	}



}
