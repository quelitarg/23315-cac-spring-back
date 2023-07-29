package ar.com.codoacodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.codoacodo.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {

	Usuario findByUsername(String username);
		//crud	
}
