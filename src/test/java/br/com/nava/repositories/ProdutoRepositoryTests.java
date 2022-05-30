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

import br.com.nava.entities.ProdutoEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ProdutoRepositoryTests {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void findByIdWhenFoundTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		testEntityManager.persist(produtoEntidade);
		
		Optional<ProdutoEntity> produto = produtoRepository.findById( produtoEntidade.getId() );
		
		assertThat( produto ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		Optional<ProdutoEntity> produto = produtoRepository.findById(1);
		
		assertThat( produto.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		testEntityManager.persist(produtoEntidade);
				
		List<ProdutoEntity> produtos = this.produtoRepository.findAll();
		
		assertThat( produtos.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		testEntityManager.persist(produtoEntidade);
		
		//execução
		ProdutoEntity produtoSalvo = produtoRepository.save(produtoEntidade);
		
		//validação
		assertThat( produtoSalvo.getId() ).isEqualTo( produtoEntidade.getId() );
		assertThat( produtoSalvo.getDescricao() ).isEqualTo( produtoEntidade.getDescricao() );
		assertThat( produtoSalvo.getNome() ).isEqualTo( produtoEntidade.getNome() );
		assertThat( produtoSalvo.getPreco() ).isEqualTo( produtoEntidade.getPreco() );	
	}
	
	@Test
	void deleteById() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		ProdutoEntity produtoTemporario = testEntityManager.persist(produtoEntidade);
		
		produtoRepository.deleteById( produtoTemporario.getId() );
		
		Optional<ProdutoEntity> deletado = produtoRepository.findById( produtoTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO
	private ProdutoEntity createValidProduto() {
		
		ProdutoEntity produtoEntidade = new ProdutoEntity();
		
		produtoEntidade.setDescricao("Este é meu produto teste");
		produtoEntidade.setNome("Produto teste");
		produtoEntidade.setPreco(250);
	
		return produtoEntidade;
	}
}

