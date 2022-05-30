package br.com.nava.dtos;

import org.modelmapper.ModelMapper;

import br.com.nava.entities.EnderecoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

	private int id;
	private String rua;
	private int numero;
	private String cep;
	private String cidade;
	private String estado;
	
	public EnderecoEntity toEntity() {
		
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, EnderecoEntity.class);
	}
}
