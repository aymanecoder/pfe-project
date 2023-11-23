package com.group8.projectpfe;

import com.group8.projectpfe.controllers.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(TestController.class)
class ProjectPfeApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private TestController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	@Test
	public void shouldReturnHelloWorld() throws Exception {
		mockMvc.perform(get("/api/v1"))
				.andExpect(status().isOk())
				.andExpect(content().string("hello world"));
	}

}
