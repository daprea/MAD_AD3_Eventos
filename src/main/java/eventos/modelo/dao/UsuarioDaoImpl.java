package eventos.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entity.Usuario;
import eventos.modelo.repository.UsuarioRepository;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	UsuarioRepository usuarioRepository;

	public Usuario buscarUsername(String username){
		return usuarioRepository.buscarUsername(username);
	}

	@Override
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	
}
