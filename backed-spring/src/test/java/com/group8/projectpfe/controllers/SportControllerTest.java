//package com.group8.projectpfe.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.group8.projectpfe.controllers.SportifController;
//import com.group8.projectpfe.domain.dto.SportifDTO;
//import com.group8.projectpfe.entities.User;
//import com.group8.projectpfe.services.SportifService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SportifControllerTest {
//
//    @Mock
//    private SportifService sportifService;
//
//    @InjectMocks
//    private SportifController sportifController;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(sportifController).build();
//    }
//
////    @Test
////    public void testGetSportifs() throws Exception {
////        // Mock the behavior of sportifService.getSportifs()
////        List<SportifDTO> mockedSportifDTOs = Arrays.asList(
////                new SportifDTO(1, "John", "Doe", "john@example.com", "Address 1", 30, 180, 75, null),
////                new SportifDTO(2, "Jane", "Smith", "jane@example.com", "Address 2", 25, 170, 65, null)
////                // Add more mocked SportifDTO objects as needed
////        );
////        when(sportifService.getSportifs()).thenReturn(mockedSportifDTOs);
////
////        // Perform the GET request using mockMvc
////        MvcResult result = mockMvc.perform(get("/api/v1/sportifs"))
////                .andExpect(status().isOk())
////                .andReturn();
////
////        // Verify the response content
////        String responseContent = result.getResponse().getContentAsString();
////        // You can use ObjectMapper to convert the response content to a list of SportifDTOs
////        ObjectMapper objectMapper = new ObjectMapper();
////        List<SportifDTO> returnedSportifDTOs = objectMapper.readValue(responseContent, new TypeReference<List<SportifDTO>>() {});
////
////        // Perform assertions or verifications based on the returned SportifDTOs
////        assertThat(returnedSportifDTOs).hasSize(2); // Assuming 2 mocked SportifDTO objects were returned
////        // Add more assertions based on your specific requirements and the content of the returnedSportifDTOs list
////    }
//
//    @Test
//    public void testGetSportifById() throws Exception {
//        when(sportifService.getSportifById(eq(1L))).thenReturn(new SportifDTO());
//        mockMvc.perform(get("/api/v1/sportifs/{id}", 1L))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testDeleteUser() throws Exception {
//        // Mock the behavior of sportifService.deleteSportif() - assuming it returns void
//        doNothing().when(sportifService).deleteSportif(any(Integer.class), any(Integer.class));
//
//        mockMvc.perform(delete("/api/v1/sportifs/{id}", 1))
//                .andExpect(status().isOk());
//        // Add assertions for content or other verifications
//    }
//
////    @Test
////    @WithUserDetails("manager@company.com")
////    @WithMockUser(roles = "COACH")
////    public void testUpdateSportif() throws Exception {
////        when(sportifService.getSportifById(any(Long.class))).thenReturn(new SportifDTO());
////
////        // Create a SportifDTO object
////        SportifDTO sportifDTO = new SportifDTO();
////        sportifDTO.setFirstName("yassine");
////        sportifDTO.setLastName("oussi");
////        sportifDTO.setAge(19);
////        sportifDTO.setEmail("Oussiyassine1@gmail.com");
////        sportifDTO.setAddress(null);
////        sportifDTO.setPicture(null);
////        sportifDTO.setPoids(69);
////        sportifDTO.setTaille(170);
////
////        // Convert your object to JSON format
////        ObjectMapper objectMapper = new ObjectMapper();
////        String sportifDTOJson = objectMapper.writeValueAsString(sportifDTO);
////
////
////
////        MockHttpServletRequestBuilder builder =
////                MockMvcRequestBuilders.put("/api/v1/sportifs/{id}", 1L)
////                        .contentType(MediaType.APPLICATION_JSON_VALUE)
////                        .accept(MediaType.APPLICATION_JSON)
////                        .characterEncoding("UTF-8")
////                        .content(sportifDTOJson);
////        // Perform the PUT request using mockMvc
////        mockMvc.perform(builder)
////                .andExpect(status().isOk());
////        // Add assertions for content or other verifications
////    }
//
//
//    @Test
//    @WithMockUser(username = "yassine", roles = {"COACH"})
//    public void testUpdateSportif() throws Exception {
//        // Mock authenticated user details
//        User authenticatedUser = new User();
//        authenticatedUser.setId(1);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null);
//
//        // Simulate authenticated context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Mock behavior of sportifService.getSportifById() to return an existing sportif
//        SportifDTO existingSportif = new SportifDTO();
//        existingSportif.setId(1);
//        existingSportif.setFirstName("yassine");
//        existingSportif.setLastName("oussi");
//        existingSportif.setAge(19);
//        existingSportif.setEmail("Oussiyassine1@gmail.com");
//        existingSportif.setAddress(null);
//        existingSportif.setPicturePath(null);
//        existingSportif.setPoids(69);
//        existingSportif.setTaille(170);
//
//        when(sportifService.getSportifById(1L)).thenReturn(existingSportif);
//
//        // Create a SportifDTO object to update
//        SportifDTO updatedSportifDTO = new SportifDTO();
//        updatedSportifDTO.setId(1);
//        updatedSportifDTO.setFirstName("yassine");
//        updatedSportifDTO.setLastName("oussi");
//        updatedSportifDTO.setAge(19);
//        updatedSportifDTO.setEmail("Oussiyassine1@gmail.com");
//        updatedSportifDTO.setAddress(null);
//        updatedSportifDTO.setPicturePath(null);
//        updatedSportifDTO.setPoids(69);
//        updatedSportifDTO.setTaille(170);
//
//        // Convert your object to JSON format
//        ObjectMapper objectMapper = new ObjectMapper();
//        String updatedSportifDTOJson = objectMapper.writeValueAsString(updatedSportifDTO);
//
//        // Perform the PUT request using mockMvc
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/sportifs/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedSportifDTOJson))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().string("Sportif updated successfully"));
//        // Add assertions for content or other verifications
//    }  // Add similar tests for other scenarios and edge cases
//}
