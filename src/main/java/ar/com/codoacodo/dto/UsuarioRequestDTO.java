package ar.com.codoacodo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UsuarioRequestDTO {

	private String username;
	private String password;
	private List<String> roles;
	
}
