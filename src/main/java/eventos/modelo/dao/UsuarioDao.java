package eventos.modelo.dao;

import eventos.modelo.entity.Usuario;

public interface UsuarioDao {

	public Usuario buscarUsername(String username);

    public void save(Usuario usuario);
	
	
}
