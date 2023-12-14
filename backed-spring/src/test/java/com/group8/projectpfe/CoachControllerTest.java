package com.group8.projectpfe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group8.projectpfe.controllers.CoachController;
import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.services.CoachService;
import com.group8.projectpfe.services.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CoachControllerTest {

    @Mock
    private CoachService coachService;

    @InjectMocks
    private CoachController coachController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(coachController).build();
    }

//    @Test
//    public void testGetCoachs() throws Exception {
//        // Mock the behavior of coachService.getCoachs()
//        List<CoachDTO> mockedCoachDTOs = Arrays.asList(
//                new CoachDTO(1, "John", "Doe", "john@example.com", "Address 1", 30, 180, 75, null),
//                new CoachDTO(2, "Jane", "Smith", "jane@example.com", "Address 2", 25, 170, 65, null)
//                // Add more mocked CoachDTO objects as needed
//        );
//        when(coachService.getCoachs()).thenReturn(mockedCoachDTOs);
//
//        // Perform the GET request using mockMvc
//        MvcResult result = mockMvc.perform(get("/api/v1/coachs"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // Verify the response content
//        String responseContent = result.getResponse().getContentAsString();
//        // You can use ObjectMapper to convert the response content to a list of CoachDTOs
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<CoachDTO> returnedCoachDTOs = objectMapper.readValue(responseContent, new TypeReference<List<CoachDTO>>() {});
//
//        // Perform assertions or verifications based on the returned CoachDTOs
//        assertThat(returnedCoachDTOs).hasSize(2); // Assuming 2 mocked CoachDTO objects were returned
//        // Add more assertions based on your specific requirements and the content of the returnedCoachDTOs list
//    }

    @Test
    public void testGetCoachById() throws Exception {
        when(coachService.getCoachById(eq(1L))).thenReturn(new CoachDTO());
        mockMvc.perform(get("/api/v1/coachs/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "yassine", roles = {"COACH"})
    public void testDeleteUser() throws Exception {
        // Mock the authenticated user
        User authenticatedUser = new User();
        authenticatedUser.setId(1);

        // Create a GrantedAuthority list (assuming ROLE_USER)
        Role role = Role.COACH;
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    authenticatedUser.setRole(role);
        // Create an authentication token with authorities and set its authentication to true
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        authentication.setAuthenticated(true);

        // Create a security context and set the authentication
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock the behavior of coachService.deleteCoach() - assuming it returns void
        doNothing().when(coachService).deleteCoach(any(Integer.class), any(Integer.class));

        mockMvc.perform(delete("/api/v1/coachs/{id}", 1L))
                .andExpect(status().isOk());
        // Add assertions for content or other verifications
    }


//    @Test
//    @WithUserDetails("manager@company.com")
//    @WithMockUser(roles = "COACH")
//    public void testUpdateCoach() throws Exception {
//        when(coachService.getCoachById(any(Long.class))).thenReturn(new CoachDTO());
//
//        // Create a CoachDTO object
//        CoachDTO coachDTO = new CoachDTO();
//        coachDTO.setFirstName("yassine");
//        coachDTO.setLastName("oussi");
//        coachDTO.setAge(19);
//        coachDTO.setEmail("Oussiyassine1@gmail.com");
//        coachDTO.setAddress(null);
//        coachDTO.setPicture(null);
//        coachDTO.setPoids(69);
//        coachDTO.setTaille(170);
//
//        // Convert your object to JSON format
//        ObjectMapper objectMapper = new ObjectMapper();
//        String coachDTOJson = objectMapper.writeValueAsString(coachDTO);
//
//
//
//        MockHttpServletRequestBuilder builder =
//                MockMvcRequestBuilders.put("/api/v1/coachs/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                        .content(coachDTOJson);
//        // Perform the PUT request using mockMvc
//        mockMvc.perform(builder)
//                .andExpect(status().isOk());
//        // Add assertions for content or other verifications
//    }


    @Test
    @WithMockUser(username = "oussiyassine1@gmail.com", roles = {"COACH"})
    public void testUpdateCoach() throws Exception {
        // Mock authenticated user details
        User authenticatedUser = new User();
        authenticatedUser.setId(1);
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null);

        // Simulate authenticated context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mock behavior of coachService.getCoachById() to return an existing coach
        CoachDTO existingCoach = new CoachDTO();
        existingCoach.setId(1);
        existingCoach.setFirstName("yassine");
        existingCoach.setLastName("oussi");
        existingCoach.setAge(19);
        existingCoach.setEmail("oussiyassine1@gmail.com");
        existingCoach.setAddress(null);
        existingCoach.setPicture(null);
        existingCoach.setPoids(69);
        existingCoach.setTaille(170);

        when(coachService.getCoachById(1L)).thenReturn(existingCoach);

        // Create a CoachDTO object to update
        CoachDTO updatedCoachDTO = new CoachDTO();
        updatedCoachDTO.setId(1);
        updatedCoachDTO.setFirstName("yassine");
        updatedCoachDTO.setLastName("oussi");
        updatedCoachDTO.setAge(19);
        updatedCoachDTO.setEmail("oussiyassine1@gmail.com");
        updatedCoachDTO.setAddress(null);
        updatedCoachDTO.setPicture(null);
        updatedCoachDTO.setPoids(69);
        updatedCoachDTO.setTaille(170);

        // Convert your object to JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedCoachDTOJson = objectMapper.writeValueAsString(updatedCoachDTO);


        doNothing().when(coachService).updateCoach(any(CoachDTO.class));
        // Perform the PUT request using mockMvc
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/coachs/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCoachDTOJson))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Coach updated successfully"));
        // Add assertions for content or other verifications
    }  // Add similar tests for other scenarios and edge cases
}


