package br.com.nava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nava.dtos.UsuarioDTO;
import br.com.nava.entities.UsuarioEntity;
import br.com.nava.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// Método getAll
	public List<UsuarioDTO> getAll(){
		
		List<UsuarioEntity> lista = usuarioRepository.findAll();
		
		List<UsuarioDTO> listaDTO = new ArrayList<>();
	
		for(UsuarioEntity usuarioEntity : lista) {
			
			listaDTO.add( usuarioEntity.toDTO() );
		}
		
		return listaDTO;	
	}
	
	// Método getOne
	public UsuarioDTO getOne(int id) {
		
		Optional<UsuarioEntity> optional = usuarioRepository.findById(id);
		
		UsuarioEntity usuario = optional.orElse(new UsuarioEntity());
		
		return usuario.toDTO();
	}
	
	// Método save
	public UsuarioDTO save(UsuarioEntity usuario) {
		
		return usuarioRepository.save(usuario).toDTO();
	}
	
	// Método update
	// Variável professor contém os dados vindo da requisição REST
	public UsuarioDTO update(int id, UsuarioEntity usuario) {
		
		// primeiro passo é verificar se o registro existe no banco de dados
		Optional<UsuarioEntity> optional = usuarioRepository.findById(id);
		
		if(optional.isPresent() == true) {
			// atualiza o objeto existente
			UsuarioEntity novoUsuario = optional.get();
			novoUsuario.setEmail(usuario.getEmail());
			novoUsuario.setEndereco(usuario.getEndereco());
			novoUsuario.setNome(usuario.getNome());
			novoUsuario.setVendas(usuario.getVendas());
			
			// atualizando o banco com os novos dados
			return usuarioRepository.save(novoUsuario).toDTO();
		}
		else {
			return new UsuarioEntity().toDTO();
		}
		
	}
	
	// Método delete
	public void delete(int id) {
		usuarioRepository.deleteById(id);
	}
}
