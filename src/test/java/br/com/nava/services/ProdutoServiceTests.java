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

import br.com.nava.dtos.ProdutoDTO;
import br.com.nava.entities.ProdutoEntity;
import br.com.nava.repositories.ProdutoRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoServiceTests {

	@Autowired
	private ProdutoService produtoService;
	
	@MockBean
	private ProdutoRepository produtoRepository;
	

	@Test
	void getAllTest() {
		
		List<ProdutoEntity> listaMockada = new ArrayList<ProdutoEntity>();

		ProdutoEntity produtoEntidade = createValidProduto();
		
		listaMockada.add(produtoEntidade);
		
		when( produtoRepository.findAll() ).thenReturn( listaMockada );
		
		List<ProdutoDTO> retorno = produtoService.getAll();
	
		isProdutoValid(retorno.get(0), listaMockada.get(0));
				
	}
	
	@Test
	void getOneWhenFoundObjectTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		Optional<ProdutoEntity> optional = Optional.of(produtoEntidade);
		
		when ( produtoRepository.findById(1) ).thenReturn( optional );
		
		ProdutoDTO obj = produtoService.getOne(1);
		
		isProdutoValid(obj, produtoEntidade);
	}
	
	@Test
	void getOneWhenNotFoundObjectTest() {
		
		Optional<ProdutoEntity> optional = Optional.empty();
		
		when ( produtoRepository.findById(1) ).thenReturn( optional );
		
		ProdutoDTO obj = produtoService.getOne(1);
	
		ProdutoEntity produtoEntidade = new ProdutoEntity();
		
		isProdutoValid(obj, produtoEntidade);
	}
	
	@Test
	void saveTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		when( produtoRepository.save(produtoEntidade) ).thenReturn(produtoEntidade);
		
		ProdutoDTO produtoSalvo = produtoService.save(produtoEntidade);
		
		isProdutoValid(produtoSalvo, produtoEntidade);
		
	}
	
	@Test
	void updateWhenFoundObj() {

		ProdutoEntity produtoEntidade = createValidProduto();
		Optional<ProdutoEntity> optional = Optional.of(produtoEntidade);
		
		when (produtoRepository.findById( produtoEntidade.getId() ) ).thenReturn(optional);
		when ( produtoRepository.save(produtoEntidade) ).thenReturn(produtoEntidade);
		
		ProdutoDTO professorAlterado = produtoService.
					update(produtoEntidade.getId(), produtoEntidade);
		
		isProdutoValid(professorAlterado, produtoEntidade);
	}
	
	@Test
	void updateWhenNotFoundObj() {
				
		Optional<ProdutoEntity> optional = Optional.empty();
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		when ( produtoRepository.findById(1) ).thenReturn(optional);
		
		ProdutoDTO produtoAlterado = produtoService.
							update(1, produtoEntidade );
	
		isProdutoValid(produtoAlterado, new ProdutoEntity() );
	}
	
	@Test
	void deleteTest() {
	
		assertDoesNotThrow( () -> produtoService.delete(1) );
		
		verify( produtoRepository, times(1) ).deleteById(1);
	}
	
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO PARA VALIDAR A RESPOSTA
	private void isProdutoValid( ProdutoDTO obj, ProdutoEntity produtoEntidade ) {
		
		assertThat( obj.getId() ).isEqualTo( produtoEntidade.getId() );
		assertThat( obj.getDescricao() ).isEqualTo( produtoEntidade.getDescricao() );
		assertThat( obj.getNome() ).isEqualTo( produtoEntidade.getNome() );
		assertThat( obj.getPreco() ).isEqualTo( produtoEntidade.getPreco() );	
	}
	
	// MÉTODO UTILIZADO PARA REUTILIZAÇÃO COLOCA OS VALORES NOS ATRIBUTOS
	private ProdutoEntity createValidProduto() {
		
		ProdutoEntity produtoEntidade = new ProdutoEntity();
		
		produtoEntidade.setDescricao("Este é meu produto teste");
		produtoEntidade.setNome("Produto teste");
		produtoEntidade.setPreco(250);
		
		return produtoEntidade;
	}
} 



















