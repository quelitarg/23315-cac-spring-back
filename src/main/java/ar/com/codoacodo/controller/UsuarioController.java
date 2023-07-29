
package ar.com.codoacodo.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.codoacodo.domain.Role;
import ar.com.codoacodo.services.UsuarioService;
import ar.com.codoacodo.domain.Usuario;
import ar.com.codoacodo.dto.UsuarioDTO;
import ar.com.codoacodo.dto.UsuarioRequestDTO;
import ar.com.codoacodo.dto.UsuarioRequestPutDTO;
import ar.com.codoacodo.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;

//http:///localhost:8081/usuario
//GET|POST|PUT|DELETE


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
	
	//necesito el service
		private final UsuarioService usuarioService;
		
		/*public UsuarioController(UsuarioService usuarioService) {
		
		this.usuarioService = usuarioService;
	}*/

	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> m1(
			@PathVariable("id") Long id
			) {
		
		Usuario user = this.usuarioService.buscarUsuario(id);
		
		UsuarioDTO dto = UsuarioDTO.builder()
				.id(user.getId())
				.username(user.getUsername())
				.roles(user.getRoles()
						.stream()
						.map(x -> x.getRol())
						.collect(Collectors.toSet())
				).build();
		
		//http status code=200
		return ResponseEntity.ok(dto);
	}
	
	//GET
		@GetMapping()	
		public ResponseEntity<List<Usuario>> findAll() {
			
			List<Usuario> user = this.usuarioService.buscarTodos();
			
			//http status code=200
			return ResponseEntity.ok(user);
		}
	
	@PostMapping()
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UsuarioResponseDTO> creacionUsuario(
			@RequestBody UsuarioRequestDTO request
		)  {
		
		// verifico si existe
				Usuario user = this.usuarioService.buscarUsuarioPorUsername(request.getUsername());
				if (user != null) {
					UsuarioResponseDTO response = UsuarioResponseDTO.builder()
							.username(user.getUsername())
							.build();
					
					return ResponseEntity.ok(response);
				} 
				
				//si no lo crea
				//validacion!!!
				
				//["1","2","3"]
				Set<Role> rolesDelUsuario = request.getRoles()
					.stream()
					.map(r -> Role.builder().id(Long.parseLong(r)).build())
					.collect(Collectors.toSet());
				
				Usuario newUser = Usuario.builder()
		
						.username(request.getUsername())
					.password(new BCryptPasswordEncoder().encode(request.getPassword()))
					.roles(rolesDelUsuario)
					.build();
				
				this.usuarioService.crearUsuario(newUser);
		
				UsuarioResponseDTO response = UsuarioResponseDTO.builder()
					.username(newUser.getUsername())
					.build();
				
		
		        return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> actualizar(
			@PathVariable("id") Long id			
		) {
		
		
		this.usuarioService.eliminarUsuario(id);
		
		return ResponseEntity.ok().build();
	}
	
	/*Idempotentes
	 * usuario/1
	{
		alias: 'nuevoalias'
		id: 2
	}
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioRequestPutDTO> actualizarPorPut (
			@PathVariable(name="id", required = true) Long id,
			@Validated @RequestBody UsuarioRequestPutDTO request
			)	{
		//404 > no existe el recurso
		//200 > ok
		//500
		
		Usuario user = this.usuarioService.buscarUsuario(id);
		if (!user.getId().equals(request.getId())) {
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(request.getPassword());
		// otros atributos en base al request

		this.usuarioService.actualizar(user);

		return ResponseEntity.ok().build();
	}

	// path!
		

}