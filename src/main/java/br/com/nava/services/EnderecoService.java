package br.com.nava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nava.dtos.EnderecoDTO;
import br.com.nava.entities.EnderecoEntity;
import br.com.nava.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public List<EnderecoDTO> getAll(){
		
		List<EnderecoEntity> lista = enderecoRepository.findAll();
		List<EnderecoDTO> listaDTO = new ArrayList<>();
	
		for(EnderecoEntity enderecoEntity : lista) {
			listaDTO.add( enderecoEntity.toDTO() );
		}
		
		return listaDTO;	
	}
	
	
	public EnderecoDTO getOne(int id) {
		
		Optional<EnderecoEntity> optional = enderecoRepository.findById(id);
		
		EnderecoEntity endereco = optional.orElse(new EnderecoEntity());
		
		return endereco.toDTO();
	}
	
	
	public EnderecoDTO save(EnderecoEntity endereco) {
		return enderecoRepository.save(endereco).toDTO();
	}
	
	
	public EnderecoDTO update(int id, EnderecoEntity novoEndereco) {
		
		
		Optional<EnderecoEntity> optional = enderecoRepository.findById(id);
		
		if(optional.isPresent() == true) {
			
			EnderecoEntity enderecoBD = optional.get();
			enderecoBD.setRua(novoEndereco.getRua());
			enderecoBD.setNumero(novoEndereco.getNumero());
			enderecoBD.setCep(novoEndereco.getCep());
			enderecoBD.setCidade(novoEndereco.getCidade());
			enderecoBD.setEstado(novoEndereco.getEstado());
			
			
			return enderecoRepository.save(enderecoBD).toDTO();
		}
		else {
			return new EnderecoEntity().toDTO();
		}
		
	}
	
	public void delete(int id) {
		enderecoRepository.deleteById(id);
	}
}
