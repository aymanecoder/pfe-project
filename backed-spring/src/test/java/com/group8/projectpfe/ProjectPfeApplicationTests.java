package com.group8.projectpfe;

import com.group8.projectpfe.controllers.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TestController.class)
@AutoConfigureMockMvc
class ProjectPfeApplicationTests {


	@Autowired
	MockMvc mockMvc;

	@Test
	public void testGetName() throws Exception {

		mockMvc.perform(get("/api/v1"))
				.andExpect(status().isOk())
				.andExpect(content().string("hello world"));

	}

}
