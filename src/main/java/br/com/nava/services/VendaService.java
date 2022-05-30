package br.com.nava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nava.dtos.VendaDTO;
import br.com.nava.entities.VendaEntity;
import br.com.nava.repositories.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	// Método getAll
	public List<VendaDTO> getAll(){
		
		List<VendaEntity> lista = vendaRepository.findAll();
		
		List<VendaDTO> listaDTO = new ArrayList<>();
	
		for(VendaEntity vendaEntity : lista) {
			
			listaDTO.add( vendaEntity.toDTO() );
		}
		
		return listaDTO;	
	}
	
	// Método getOne
	public VendaDTO getOne(int id) {
		
		Optional<VendaEntity> optional = vendaRepository.findById(id);
		
		VendaEntity venda = optional.orElse(new VendaEntity());
		
		return venda.toDTO();
	}
	
	// Método save
	public VendaDTO save(VendaEntity venda) {
		
		return vendaRepository.save(venda).toDTO();
	}
	
	// Método update
	// Variável professor contém os dados vindo da requisição REST
	public VendaDTO update(int id, VendaEntity venda) {
		
		// primeiro passo é verificar se o registro existe no banco de dados
		Optional<VendaEntity> optional = vendaRepository.findById(id);
		
		if(optional.isPresent() == true) {
			// atualiza o objeto existente
			VendaEntity vendaBD = optional.get();
			vendaBD.setProdutos(venda.getProdutos());
			vendaBD.setUsuario(venda.getUsuario());
			vendaBD.setValorTotal(venda.getValorTotal());
			
			// atualizando o banco com os novos dados
			return vendaRepository.save(vendaBD).toDTO();
		}
		else {
			return new VendaEntity().toDTO();
		}
		
	}
	
	// Método delete
	public void delete(int id) {
		vendaRepository.deleteById(id);
	}
	
}