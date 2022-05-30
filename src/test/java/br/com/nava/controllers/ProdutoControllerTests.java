package br.com.nava.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nava.dtos.ProdutoDTO;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllTest() throws Exception {	
		
		// armazena o objeto que fará o teste e colher o resultado
		ResultActions response = mockMvc.perform( get("/produtosteste").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		// converte o resultado de String em um Array de Objetos de ProfessorDTO
		ProdutoDTO[] lista = mapper.readValue(responseStr, ProdutoDTO[].class);
		
		// verificando se a lista de retorno não é vazia
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	void getOneTest() throws Exception {	
		
		// armazena o objeto que fará o teste e colher o resultado
		ResultActions response = mockMvc.perform( get("/produtosteste/1").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ProdutoDTO produto = mapper.readValue(responseStr, ProdutoDTO.class);
		
		
		assertThat( produto.getId() ).isEqualTo(1);
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
	
	@Test
	void saveTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo ProfessorDTO para enviarmos junto com a requisição
		ProdutoDTO produto = new ProdutoDTO();
		produto.setDescricao("Este é meu produto teste");
		produto.setNome("Produto teste");
		produto.setPreco(250);
		//produto.setVendas();
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				post("/produtosteste")
				.content( mapper.writeValueAsString(produto) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ProdutoDTO produtoSalvo = mapper.readValue(responseStr, ProdutoDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( produtoSalvo.getId() ).isPositive();
		assertThat( produtoSalvo.getDescricao() ).isEqualTo( produto.getDescricao() );
		assertThat( produtoSalvo.getNome() ).isEqualTo( produto.getNome() );
		assertThat( produtoSalvo.getPreco() ).isEqualTo( produto.getPreco() );
		assertThat( produtoSalvo.getVendas() ).isEqualTo( produto.getVendas() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );	
	}
	
	@Test
	void updateTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo ProdutoDTO para enviarmos junto com a requisição
		ProdutoDTO produto = new ProdutoDTO();
		produto.setDescricao("Este é meu produto teste");
		produto.setNome("Produto teste");
		produto.setPreco(250);
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				patch("/produtosteste/1")
				.content( mapper.writeValueAsString(produto) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ProdutoDTO produtoSalvo = mapper.readValue(responseStr, ProdutoDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( produtoSalvo.getId() ).isPositive();
		assertThat( produtoSalvo.getDescricao() ).isEqualTo( produto.getDescricao() );
		assertThat( produtoSalvo.getNome() ).isEqualTo( produto.getNome() );
		assertThat( produtoSalvo.getPreco() ).isEqualTo( produto.getPreco() );
		assertThat( produtoSalvo.getVendas() ).isEqualTo( produto.getVendas() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
	}
	
	@Test
	void deleteTest() throws Exception{
		// para enviar a requisição
		ResultActions response = mockMvc.perform( delete("/produtosteste/2").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
}
