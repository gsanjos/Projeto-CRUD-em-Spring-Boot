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

import br.com.nava.dtos.VendaDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VendaControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllTest() throws Exception {	
		
		// armazena o objeto que fará o teste e colher o resultado
		ResultActions response = mockMvc.perform( get("/vendasteste").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		// converte o resultado de String em um Array de Objetos de ProfessorDTO
		VendaDTO[] lista = mapper.readValue(responseStr, VendaDTO[].class);
		
		// verificando se a lista de retorno não é vazia
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	void getOneTest() throws Exception {	
		
		// armazena o objeto que fará o teste e colher o resultado
		ResultActions response = mockMvc.perform( get("/vendasteste/1").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		VendaDTO venda = mapper.readValue(responseStr, VendaDTO.class);
		
		
		assertThat( venda.getId() ).isEqualTo(1);
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
	
	@Test
	void saveTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo VendaDTO para enviarmos junto com a requisição
		VendaDTO venda = new VendaDTO();
		//venda.setProdutos(null);
		//venda.setUsuario(null);
		venda.setValorTotal(270);
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				post("/vendasteste")
				.content( mapper.writeValueAsString(venda) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		VendaDTO vendaSalvo = mapper.readValue(responseStr, VendaDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( vendaSalvo.getId() ).isPositive();
		assertThat( vendaSalvo.getProdutos() ).isEqualTo( venda.getProdutos() );
		assertThat( venda.getUsuario()).isEqualTo( venda.getUsuario() );
		assertThat( venda.getValorTotal() ).isEqualTo( venda.getValorTotal() );
		
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );	
	}
	
	@Test
	void updateTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo ProfessorDTO para enviarmos junto com a requisição
		VendaDTO venda = new VendaDTO();
		//venda.setProdutos(null);
		//venda.setUsuario(null);
		venda.setValorTotal(270);
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				patch("/vendasteste/1")
				.content( mapper.writeValueAsString(venda) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		VendaDTO vendaSalvo = mapper.readValue(responseStr, VendaDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( vendaSalvo.getId() ).isPositive();
		assertThat( vendaSalvo.getProdutos() ).isEqualTo( venda.getProdutos() );
		assertThat( venda.getUsuario()).isEqualTo( venda.getUsuario() );
		assertThat( venda.getValorTotal() ).isEqualTo( venda.getValorTotal() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
	}
	
	@Test
	void deleteTest() throws Exception{
		// para enviar a requisição
		ResultActions response = mockMvc.perform( delete("/vendasteste/2").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
}





