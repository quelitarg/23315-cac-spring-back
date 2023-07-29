package ar.com.codoacodo.services.impl;

import ar.com.codoacodo.domain.Usuario;
import ar.com.codoacodo.services.UsuarioService;

import java.util.List;

import org.springframework.stereotype.Service;
import ar.com.codoacodo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	//D.I.
	private final UsuarioRepository repository;
	
	/*public UsuarioServiceImpl(UsuarioRepository repository) {
		
		this.repository = repository;
	}*/
	
	@Override
	public void crearUsuario(Usuario usuario) {
		
		//acá va la logica de insert!
		this.repository.save(usuario);
		//insert into tabla (c1,c2..cn) values(v1,v2....vn)

	}
	

	@Override
	public Usuario buscarUsuario(Long id) {
		//select * from tabla where id = id
		//mapper! mapstruct!
		return this.repository.findById(id).get();
	}
	
	@Override
	public void eliminarUsuario(Long id) {
		this.repository.deleteById(id);
	}
	
	@Override
	public List<Usuario> buscarTodos(){
		
		return this.repository.findAll();
	}
	
	@Override
	public Usuario buscarUsuarioPorUsername(String username) {
		
		return this.repository.findByUsername(username);
		
	}
	
	@Override
	public void actualizar(Usuario user) {
		this.repository.save(user);
	}
}
