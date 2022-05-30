package br.com.nava.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nava.dtos.UsuarioDTO;
import br.com.nava.entities.UsuarioEntity;
import br.com.nava.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTests {

	@Autowired
	private UsuarioService usuarioService;
	
	// a anotação @MockBean serve para sinalizarmos que iremos "MOCKAR (Simular) a camada Repository"
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Test
	void getAllTest() {
		
		// vamos criar uma lista de entidade de professor com o objeto
		// de retornar a mesma quando o professorRepository.findAll() 
		// for acionado
		List<UsuarioEntity> listaMockada = new ArrayList<UsuarioEntity>();

		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		listaMockada.add(usuarioEntidade);
		
		// quando o repository for acionado, retorno a lista Mockada
		when( usuarioRepository.findAll() ).thenReturn( listaMockada );
		
		List<UsuarioDTO> retorno = usuarioService.getAll();
		
		// validar a resposta
		isUsuarioValid(retorno.get(0), listaMockada.get(0));
				
	}
	
	// quando o objeto é achado no banco de dados
	@Test
	void getOneWhenFoundObjectTest() {
		
		UsuarioEntity enderecoEntidade = createValidUsuario();
		
		Optional<UsuarioEntity> optional = Optional.of(enderecoEntidade);
		
		when ( usuarioRepository.findById(1) ).thenReturn( optional );
		
		// execução
		UsuarioDTO obj = usuarioService.getOne(1);
		
		// validar a resposta
		isUsuarioValid(obj, enderecoEntidade);
	}
	
	// quando o objeto NÃO é  achado no banco de dados
	@Test
	void getOneWhenNotFoundObjectTest() {
		
		// Optional.empty() -> simulando o caso de NÃO achar o registro no banco de dados
		Optional<UsuarioEntity> optional = Optional.empty();
		
		when ( usuarioRepository.findById(1) ).thenReturn( optional );
		
		// execução
		UsuarioDTO obj = usuarioService.getOne(1);
		
		// objeto com valores "padrão"
		UsuarioEntity usuarioEntidade = new UsuarioEntity();
		
		// validar a resposta
		isUsuarioValid(obj, usuarioEntidade);
	}
	
	@Test
	void saveTest() {
		
		// 1-) Cenário
		//objeto com dados válidos de um professor
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// quando o professorRepository.save for acionado, retornaremos um objeto de professor com dados válidos
		when( usuarioRepository.save(usuarioEntidade) ).thenReturn(usuarioEntidade);
		
		UsuarioDTO enderecoSalvo = usuarioService.save(usuarioEntidade);
		
		// validar a resposta
		isUsuarioValid(enderecoSalvo, usuarioEntidade);
		
	}
	
	@Test
	void updateWhenFoundObj() {
		
		//Cenário
		//objeto chamando o createValidEndereco()
		UsuarioEntity enderecoEntidade = createValidUsuario();
		Optional<UsuarioEntity> optional = Optional.of(enderecoEntidade);
		
		//mocks
		when (usuarioRepository.findById( enderecoEntidade.getId() ) ).thenReturn(optional);
		when ( usuarioRepository.save(enderecoEntidade) ).thenReturn(enderecoEntidade);
		
		// execução
		UsuarioDTO enderecoAlterado = usuarioService.
					update(enderecoEntidade.getId(), enderecoEntidade);
		
		// validar a resposta
		isUsuarioValid(enderecoAlterado, enderecoEntidade);
	}
	
	@Test
	void updateWhenNotFoundObj() {
				
		// Cenário
		// Optional.empty() por conta que não achou o objeto a ser alterado
		Optional<UsuarioEntity> optional = Optional.empty();
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// mocks
		when ( usuarioRepository.findById(1) ).thenReturn(optional);
		
		// execução
		// estamos passando como argumento o professorEntidade pois 
		// em suposição ele não estará no banco de dadaos neste cenário
		UsuarioDTO usuarioAlterado = usuarioService.
							update(1, usuarioEntidade );
		
		// validar a resposta
		isUsuarioValid(usuarioAlterado, new UsuarioEntity() );
	}
	
	@Test
	void deleteTest() {
		
		// execução
		// assertDoesNotThrow espera uma lambda (método sem nome) e verifica se a lambda executa sem erro
		assertDoesNotThrow( () -> usuarioService.delete(1) );
		
		// verifico se o professorRepository.deleteById foi executado somente uma vez 
		verify( usuarioRepository, times(1) ).deleteById(1);
	}
		
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO PARA VALIDAR A RESPOSTA
	private void isUsuarioValid( UsuarioDTO obj, UsuarioEntity usuarioEntidade ) {
			assertThat( obj.getEmail() ).isEqualTo( usuarioEntidade.getEmail() );
			assertThat( obj.getEndereco() ).isEqualTo( usuarioEntidade.getEndereco() );
			assertThat( obj.getNome() ).isEqualTo( usuarioEntidade.getNome() );
			assertThat( obj.getVendas() ).isEqualTo( usuarioEntidade.getVendas() );
			
			assertThat( obj.getId() ).isEqualTo( usuarioEntidade.getId() );
	}
		
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO COLOCA OS VALORES NOS ATRIBUTOS
	private UsuarioEntity createValidUsuario() {
		
		// instanciando o novo objeto do tipo ProfessorEntity
		UsuarioEntity usuarioEntidade = new UsuarioEntity();
		
		// colocando valores nos atributos de ProfessorEntity
		usuarioEntidade.setEmail("gabriela@teste.com");
		//usuarioEntidade.setEndereco("Rua Floario Peixoto Lisboa");
		usuarioEntidade.setNome("Gabriela");
		//usuarioEntidade.setVendas(200.00);
		usuarioEntidade.setId(1);
		
		// retornando este novo objeto criado
		return usuarioEntidade;
	}

}
