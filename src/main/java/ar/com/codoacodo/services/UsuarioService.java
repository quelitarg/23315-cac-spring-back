package ar.com.codoacodo.services;

import java.util.List;

import ar.com.codoacodo.domain.Usuario;

public interface UsuarioService {
	
	public void crearUsuario(Usuario usuario);
	public Usuario buscarUsuario(Long id);
	public void eliminarUsuario(Long id);
	public List<Usuario> buscarTodos();
	public Usuario buscarUsuarioPorUsername(String username);
	public void actualizar(Usuario user);
}
