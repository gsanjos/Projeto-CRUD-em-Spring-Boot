package br.com.nava.dtos;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.nava.entities.ProdutoEntity;
import br.com.nava.entities.VendaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

	private int id;
	private String nome;
	private String descricao;
	private float preco;
	private List<VendaEntity> vendas;
	
	public ProdutoEntity toEntity() {
		
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, ProdutoEntity.class);
	}
}
