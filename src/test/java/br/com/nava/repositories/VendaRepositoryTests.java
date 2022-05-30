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

import br.com.nava.entities.VendaEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class VendaRepositoryTests {
	
	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void findByIdWhenFoundTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
	
		testEntityManager.persist(vendaEntidade);
		
		Optional<VendaEntity> venda = vendaRepository.findById( vendaEntidade.getId() );
		
		assertThat( venda ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		Optional<VendaEntity> venda = vendaRepository.findById(1);
		
		assertThat( venda.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// salvando temporariamente o professor no banco de dados
		testEntityManager.persist(vendaEntidade);
		
		//execução		
		List<VendaEntity> vendas = this.vendaRepository.findAll();
		
		// verificar
		assertThat( vendas.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// salvando temporariamente a venda no banco de dados
		testEntityManager.persist(vendaEntidade);
		
		//execução
		VendaEntity vendaSalvo = vendaRepository.save(vendaEntidade);
		
		//validação
		assertThat( vendaSalvo.getId() ).isEqualTo( vendaEntidade.getId() );
		assertThat( vendaSalvo.getProdutos() ).isEqualTo( vendaEntidade.getProdutos() );
		assertThat( vendaSalvo.getUsuario() ).isEqualTo( vendaEntidade.getUsuario() );
		assertThat( vendaSalvo.getValorTotal() ).isEqualTo( vendaEntidade.getValorTotal() );
			
	}
	
	@Test
	void deleteById() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// salvando temporariamente o professor no banco de dados
		VendaEntity vendaTemporario = testEntityManager.persist(vendaEntidade);
		
		//execução
		vendaRepository.deleteById( vendaTemporario.getId() );
		
		// verificação
		//busquei o professor deletado e comparei a resposta 
		Optional<VendaEntity> deletado = vendaRepository.findById( vendaTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO
	private VendaEntity createValidVenda() {
		
		// instanciando o novo objeto do tipo ProfessorEntity
		VendaEntity vendaEntidade = new VendaEntity();
		
		// colocando valores nos atributos de ProfessorEntity
		vendaEntidade.setValorTotal(1500);	
		
		// retornando este novo objeto criado
		return vendaEntidade;
	}
}