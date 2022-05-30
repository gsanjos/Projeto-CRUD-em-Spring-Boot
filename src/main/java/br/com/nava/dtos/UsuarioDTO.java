package br.com.nava.dtos;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.nava.entities.EnderecoEntity;
import br.com.nava.entities.UsuarioEntity;
import br.com.nava.entities.VendaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private int id;
	private String nome;
	private String email;
	private EnderecoEntity endereco;
	private List<VendaEntity> vendas;
	
	public UsuarioEntity toEntity() {
		
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, UsuarioEntity.class);
	}

}
