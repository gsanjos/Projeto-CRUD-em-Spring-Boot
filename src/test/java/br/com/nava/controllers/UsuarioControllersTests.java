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

import br.com.nava.dtos.UsuarioDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllersTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllTest() throws Exception {	
		
		ResultActions response = mockMvc.perform( get("/usuariosteste").contentType("aplication/json"));
		
		MvcResult result = response.andReturn();
		
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		UsuarioDTO[] lista = mapper.readValue(responseStr, UsuarioDTO[].class);
		
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	void getOneTest() throws Exception {	
		
		ResultActions response = mockMvc.perform( get("/usuariosteste/1").contentType("aplication/json"));
		
		MvcResult result = response.andReturn();
		
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		ObjectMapper mapper = new ObjectMapper();
		
		UsuarioDTO usuario = mapper.readValue(responseStr, UsuarioDTO.class);
		
		
		assertThat( usuario.getId() ).isEqualTo(1);
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
	
	@Test
	void saveTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setEmail("gabriela@teste.com");
		//usuario.setEndereco("Rua Floario Peixoto Lisboa");
		usuario.setNome("Gabriela");
		//usuario.setVendas(200.00);
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				post("/usuariosteste")
				.content( mapper.writeValueAsString(usuario) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		UsuarioDTO usuarioSalvo = mapper.readValue(responseStr, UsuarioDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( usuarioSalvo.getId() ).isPositive();
		assertThat( usuarioSalvo.getEmail() ).isEqualTo( usuario.getEmail() );
		assertThat( usuarioSalvo.getEndereco() ).isEqualTo( usuario.getEndereco() );
		assertThat( usuarioSalvo.getNome() ).isEqualTo( usuario.getNome() );
		assertThat( usuarioSalvo.getVendas() ).isEqualTo( usuario.getVendas() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );	
	}
	
	@Test
	void updateTest() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setEmail("gabriela@teste.com");
		//usuario.setEndereco("Rua Floario Peixoto Lisboa");
		usuario.setNome("Gabriela");
		//usuario.setVendas(200.00);
		
		// para enviar a requisição
		ResultActions response = mockMvc.perform(
				patch("/usuariosteste/1")
				.content( mapper.writeValueAsString(usuario) )
				.contentType("application/json")
			);

		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		// pegando o resultado no formato de String
		String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		System.out.println(responseStr);
		
		UsuarioDTO usuarioSalvo = mapper.readValue(responseStr, UsuarioDTO.class);
		
		// verificar se foi salvo corretamente
		assertThat ( usuarioSalvo.getId() ).isPositive();
		assertThat( usuarioSalvo.getEmail() ).isEqualTo( usuario.getEmail() );
		assertThat( usuarioSalvo.getEndereco() ).isEqualTo( usuario.getEndereco() );
		assertThat( usuarioSalvo.getNome() ).isEqualTo( usuario.getNome() );
		assertThat( usuarioSalvo.getVendas() ).isEqualTo( usuario.getVendas() );
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );	
	}
	
	@Test
	void deleteTest() throws Exception{
		// para enviar a requisição
		ResultActions response = mockMvc.perform( delete("/usuariosteste/3").contentType("aplication/json"));
		
		// pegando o resultado via MvcResult
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo(200);
	}
}