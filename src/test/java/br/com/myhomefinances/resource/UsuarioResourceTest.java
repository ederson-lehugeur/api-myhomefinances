package br.com.myhomefinances.resource;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioResourceTest {

    private final String BASE_URL = "/usuarios";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testarCriacaoDeUsuarioComTodosCamposPreenchidosCorretamente201() throws Exception {
		URI uri = new URI(BASE_URL);
		String json = "{\"nome\":\"Alan\", \"sobrenome\":\"Turing\", \"email\":\"alanturing@gmail.com\", \"senha\":\"Q1W2E3R4\"}";

		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(201));
	}

	@Test
	void testarCriacaoDeUsuarioSemSobrenome422() throws Exception {
		URI uri = new URI(BASE_URL);
		String json = "{\"nome\":\"Alan\", \"sobrenome\":\"\", \"email\":\"alanturing@gmail.com\", \"senha\":\"Q1W2E3R4\"}";

		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(422));
	}

	@Test
	void testarCriacaoDeUsuarioComEmailInvalido422() throws Exception {
		URI uri = new URI(BASE_URL);
		String json = "{\"nome\":\"Alan\", \"sobrenome\":\"Turing\", \"email\":\"alanturing.gmail.com\", \"senha\":\"Q1W2E3R4\"}";

		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(422));
	}

}
