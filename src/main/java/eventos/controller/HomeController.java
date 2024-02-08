package eventos.controller;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.UsuarioDao;
import eventos.modelo.entity.Evento;
import eventos.modelo.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	EventoDao eventoDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping({"", "/",})
	public String verHome(Model model, Authentication aut) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		model.addAttribute("username", username);

		List<Evento> eventos = eventoDao.buscarTodos();
		model.addAttribute("eventos", eventos);
		return "home";
	}
	

	@GetMapping("/rest/encriptar/{word}")
    @ResponseBody
    public String encryptWord(@PathVariable String word) {
        // Encripta la palabra utilizando BCryptPasswordEncoder
        String encryptedWord = passwordEncoder.encode(word);
        return "Palabra encriptada: " + encryptedWord;
    }

	@GetMapping("/login")
	public String login(Model model, HttpSession sesion, Authentication aut) {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(Model model, HttpSession sesion, Authentication aut) {
		sesion.removeAttribute("usuario");
		List<Evento> eventos = eventoDao.buscarTodos();
		model.addAttribute("eventos", eventos);
		return "home";
	}

	@GetMapping("/registro")
	public String registro(Model model, HttpSession sesion, Authentication aut) {
		return "registro";
	}

	
	@GetMapping("/login/registro")
	public String registro(Model model, HttpSession sesion, 
		@RequestParam (name="username") String username, @RequestParam (name="password") String password,
		@RequestParam (name="nombre") String nombre, @RequestParam (name="apellidos") String apellidos,
		@RequestParam (name="dirección") String dirección) {

		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(password);
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setDireccion(dirección);
		usuario.setEnabled(1);
		usuario.setFechaRegistro(new Date());
		usuarioDao.save(usuario);

		sesion.setAttribute("usuario", usuario);
		List<Evento> eventos = eventoDao.buscarTodos();
		model.addAttribute("eventos", eventos);
		model.addAttribute("username", username);
	
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROL_CLIENTE"));

		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		return "home";
	}

	@GetMapping("/login/acceder")
	public String ingresoUsuario(Model model, HttpSession sesion, Authentication aut, @RequestParam (name="username") String username,
		@RequestParam (name="password") String password) {
		
		Usuario usuario = usuarioDao.buscarUsername(username);
		if(usuario!=null){
			sesion.setAttribute("usuario", usuario);
			List<Evento> eventos = eventoDao.buscarTodos();
			model.addAttribute("eventos", eventos);
			model.addAttribute("username", username);
        
			List<GrantedAuthority> authorities = new ArrayList<>();
        	authorities.add(new SimpleGrantedAuthority("ROL_CLIENTE"));

			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
			return "home";
		}else{
			return "login";
		}

	}



}
