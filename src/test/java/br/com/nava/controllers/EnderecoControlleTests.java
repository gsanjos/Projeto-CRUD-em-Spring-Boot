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

import br.com.nava.dtos.EnderecoDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoControlleTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllTest() throws Exception {	
		
		ResultActions response = mockMvc.perform( get("/enderecosteste").contentType("aplication/json"));
		
		MvcResult result = response.andReturn();
		
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		EnderecoDTO[] lista = mapper.readValue(responseStr, EnderecoDTO[].class);
	
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	void getOneTest() throws Exception {	
		
		ResultActions response = mockMvc.perform( get("/enderecosteste/1").contentType("aplication/json"));
		
		MvcResult result = response.andReturn();
		
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		EnderecoDTO endereco = mapper.readValue(responseStr, EnderecoDTO.class);
		
		assertThat( endereco.getId() ).isEqualTo(1);
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
	
	@Test
	void saveTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setCep("05888090");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setRua("Rua de Teste");
		endereco.setNumero(25);
		
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				post("/enderecosteste")
				.content( mapper.writeValueAsString(endereco) )
				.contentType("application/json")
			);

		MvcResult result = response.andReturn();
		
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		EnderecoDTO enderecoSalvo = mapper.readValue(responseStr, EnderecoDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( enderecoSalvo.getId() ).isPositive();
		assertThat( enderecoSalvo.getCep() ).isEqualTo( endereco.getCep() );
		assertThat( enderecoSalvo.getCidade() ).isEqualTo( endereco.getCidade() );
		assertThat( enderecoSalvo.getNumero() ).isEqualTo( endereco.getNumero() );
		assertThat( enderecoSalvo.getRua() ).isEqualTo( endereco.getRua() );
		assertThat( enderecoSalvo.getEstado() ).isEqualTo( endereco.getEstado() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );	
	}
	

	@Test
	void updateTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo ProfessorDTO para enviarmos junto com a requisição
		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setCep("05888090");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setRua("Rua de Teste");
		endereco.setNumero(25);
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				patch("/enderecosteste/1")
				.content( mapper.writeValueAsString(endereco) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		EnderecoDTO enderecoSalvo = mapper.readValue(responseStr, EnderecoDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( enderecoSalvo.getId() ).isPositive();
		assertThat( enderecoSalvo.getCep() ).isEqualTo( endereco.getCep() );
		assertThat( enderecoSalvo.getCidade() ).isEqualTo( endereco.getCidade() );
		assertThat( enderecoSalvo.getNumero() ).isEqualTo( endereco.getNumero() );
		assertThat( enderecoSalvo.getRua() ).isEqualTo( endereco.getRua() );
		assertThat( enderecoSalvo.getEstado() ).isEqualTo( endereco.getEstado() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
	}
	
	@Test
	void deleteTest() throws Exception{
		// para enviar a requisição
		ResultActions response = mockMvc.perform( delete("/enderecosteste/2").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
}

