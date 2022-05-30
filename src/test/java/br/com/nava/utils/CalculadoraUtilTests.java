package br.com.nava.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CalculadoraUtilTests {

	// criando uma instância da classe calculadora porque o método
	// que queresmo testar está dentro dessa classe, assim consigo chamar o método somar
	private CalculadoraUtil calculadoraUtil = new CalculadoraUtil();

	@Test
	void somarTest() {
		// primeira ideia: chamar o método que queresmos testar e
		// verificar se a resposta do método é a esperada
		
		int atual = calculadoraUtil.somar(5, 9);
		
		assertEquals(14, atual);
	}
}
