package br.com.nava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nava.dtos.ProfessorDTO;
import br.com.nava.entities.ProfessorEntity;
import br.com.nava.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	// Método getAll
	public List<ProfessorDTO> getAll(){
		
		List<ProfessorEntity> lista = professorRepository.findAll();
		
		List<ProfessorDTO> listaDTO = new ArrayList<>();
		
		// 1- Tipo de variável de cada elemento da lista
		// 2- Nome do local da variável 
		// 3- Lista com os elementos a ser pecorrido
		for(ProfessorEntity professorEntity : lista) {
			
			listaDTO.add( professorEntity.toDTO() );
			
		//Explicação: para cada elemento da lista de Entity, eu instâncio um novo elemento DTO
		// pego os valores do Entity e atribuo ao DTO, e adiciono na lista de DTOS
		}
		
		return listaDTO;	
	}
	
	// Método getOne
	public ProfessorDTO getOne(int id) {
		
		Optional<ProfessorEntity> optional = professorRepository.findById(id);
		
		ProfessorEntity professor = optional.orElse(new ProfessorEntity());
		
		return professor.toDTO();
	}
	
	// Método save
	public ProfessorDTO save(ProfessorEntity professor) {
		
		return professorRepository.save(professor).toDTO();
	}
	
	// Método update
	// Variável professor contém os dados vindo da requisição REST
	public ProfessorDTO update(int id, ProfessorEntity professor) {
		
		// primeiro passo é verificar se o registro existe no banco de dados
		Optional<ProfessorEntity> optional = professorRepository.findById(id);
		
		if(optional.isPresent() == true) {
			// atualiza o objeto existente
			ProfessorEntity professorBD = optional.get();
			professorBD.setNome(professor.getNome());
			professorBD.setCep(professor.getCep());
			professorBD.setCpf(professor.getCpf());
			professorBD.setNumero(professor.getNumero());
			professorBD.setRua(professor.getRua());
			
			// atualizando o banco com os novos dados
			return professorRepository.save(professorBD).toDTO();
		}
		else {
			return new ProfessorEntity().toDTO();
		}
		
	}
	
	// Método delete
	public void delete(int id) {
		professorRepository.deleteById(id);
	}
	
	// Método que faz consulta de nome do professor
	public List<ProfessorDTO> searchByName(String nome){

		List<ProfessorEntity> lista =  professorRepository.searchByNomeNativeSQL(nome);
				
		List<ProfessorDTO> dtos = new ArrayList<>();
		
		for (ProfessorEntity professorEntity : lista) {
			dtos.add( professorEntity.toDTO() );
		}
		
		return dtos;
	}
}
