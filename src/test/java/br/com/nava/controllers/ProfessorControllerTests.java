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

import br.com.nava.dtos.ProfessorDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerTests {

	// temos que fazer as requisições para os endpoints do controle que queremos testar
	// responsável por criar as requisições REST para a camada de Controller
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllTest() throws Exception {	
		
		// armazena o objeto que fará o teste e colher o resultado
		ResultActions response = mockMvc.perform( get("/professoresteste").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		// converte o resultado de String em um Array de Objetos de ProfessorDTO
		ProfessorDTO[] lista = mapper.readValue(responseStr, ProfessorDTO[].class);
		
		// verificando se a lista de retorno não é vazia
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	void getOneTest() throws Exception {	
		
		// armazena o objeto que fará o teste e colher o resultado
		ResultActions response = mockMvc.perform( get("/professoresteste/1").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ProfessorDTO professor = mapper.readValue(responseStr, ProfessorDTO.class);
		
		
		assertThat( professor.getId() ).isEqualTo(1);
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
	
	@Test
	void saveTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo ProfessorDTO para enviarmos junto com a requisição
		ProfessorDTO professor = new ProfessorDTO();
		professor.setCep("04567895");
		professor.setNome("Professor Testé");
		professor.setNumero(3);
		professor.setRua("Rua de Teste");
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				post("/professoresteste")
				.content( mapper.writeValueAsString(professor) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ProfessorDTO professorSalvo = mapper.readValue(responseStr, ProfessorDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( professorSalvo.getId() ).isPositive();
		assertThat( professorSalvo.getCep() ).isEqualTo( professor.getCep() );
		assertThat( professorSalvo.getNome() ).isEqualTo( professor.getNome() );
		assertThat( professorSalvo.getNumero() ).isEqualTo( professor.getNumero() );
		assertThat( professorSalvo.getRua() ).isEqualTo( professor.getRua() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );	
	}
	
	@Test
	void updateTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		// criamos um objeto do tipo ProfessorDTO para enviarmos junto com a requisição
		ProfessorDTO professor = new ProfessorDTO();
		professor.setCep("04567895");
		professor.setNome("Professor Teste");
		professor.setNumero(3);
		professor.setRua("Rua de Teste");
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				patch("/professoresteste/1")
				.content( mapper.writeValueAsString(professor) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ProfessorDTO professorSalvo = mapper.readValue(responseStr, ProfessorDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( professorSalvo.getId() ).isPositive();
		assertThat( professorSalvo.getCep() ).isEqualTo( professor.getCep() );
		assertThat( professorSalvo.getNome() ).isEqualTo( professor.getNome() );
		assertThat( professorSalvo.getNumero() ).isEqualTo( professor.getNumero() );
		assertThat( professorSalvo.getRua() ).isEqualTo( professor.getRua() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
	}
	
	@Test
	void deleteTest() throws Exception{
		// para enviar a requisição
		ResultActions response = mockMvc.perform( delete("/professoresteste/2").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
}











