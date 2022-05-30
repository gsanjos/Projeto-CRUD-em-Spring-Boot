package br.com.nava.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nava.entities.UsuarioEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTests {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// para manipular o banco, usado para salvar a entidade no banco e 
	// depoisvamos procurar no banco temporário se existe esse objeto
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void findByIdWhenFoundTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// vai persistir a entidade no banco de dados para testar o findById
		// ao final do testes, esta entidade será deletada
		testEntityManager.persist(usuarioEntidade);
		
		// buscar a entidade no banco de dados para testar o findById
		
		// execução do findById
		Optional<UsuarioEntity> usuario = usuarioRepository.findById( usuarioEntidade.getId() );
		
		// validando a respota - se o objeto encontrado não é nulo
		assertThat( usuario ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		// buscar uma entidade na qual não existe no banco de dados
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(1);
		
		// temos que verificar se o opcional não possui valores, ou seja, isPresent() possui valor falso
		assertThat( usuario.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// salvando temporariamente o professor no banco de dados
		testEntityManager.persist(usuarioEntidade);
		
		//execução		
		List<UsuarioEntity> usuario = this.usuarioRepository.findAll();
		
		// verificar
		assertThat( usuario.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// salvando temporariamente o professor no banco de dados
		testEntityManager.persist(usuarioEntidade);
		
		//execução
		UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntidade);
		
		//validação
		assertThat ( usuarioSalvo.getId() ).isEqualTo( usuarioEntidade.getId());
		assertThat( usuarioSalvo.getEmail() ).isEqualTo( usuarioEntidade.getEmail() );
		assertThat( usuarioSalvo.getEndereco() ).isEqualTo( usuarioEntidade.getEndereco() );
		assertThat( usuarioSalvo.getNome() ).isEqualTo( usuarioEntidade.getNome() );
		assertThat( usuarioSalvo.getVendas() ).isEqualTo( usuarioEntidade.getVendas() );
	}
	
	@Test
	void deleteById() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// salvando temporariamente o usuario no banco de dados
		UsuarioEntity usuarioTemporario = testEntityManager.persist(usuarioEntidade);
		
		//execução
		usuarioRepository.deleteById( usuarioTemporario.getId() );
		
		// verificação
		//busquei o professor deletado e comparei a resposta 
		Optional<UsuarioEntity> deletado = usuarioRepository.findById( usuarioTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO
	private UsuarioEntity createValidUsuario() {
		
		// instanciando o novo objeto do tipo ProfessorEntity
		UsuarioEntity usuarioEntidade = new UsuarioEntity();
		
		// colocando valores nos atributos de ProfessorEntity
		usuarioEntidade.setEmail("gabriela@teste.com");
		//usuarioEntidade.setEndereco("Rua Floario Peixoto Lisboa");
		usuarioEntidade.setNome("Gabriela");
		//usuarioEntidade.setVendas(200.00);
		
		// retornando este novo objeto criado
		return usuarioEntidade;
	}
}
