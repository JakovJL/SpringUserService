package org.example.springuserservice.controller;

import org.example.springuserservice.dto.UserDTO;
import org.example.springuserservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    private final UserDTO mockUser = UserDTO.builder()
            .id(1L).name("Petr").email("petr@mail.com").age(25).build();


    @Test
    void shouldReturnUser() throws Exception {
        when(userService.get(1L)).thenReturn(mockUser);
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Petr"))
                .andExpect(jsonPath("$.email").value("petr@mail.com"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.create(any())).thenReturn(mockUser);
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("petr@mail.com"));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        when(userService.getAll()).thenReturn(List.of(mockUser));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Petr"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(userService.update(eq(1L), any())).thenReturn(mockUser);
        mockMvc.perform(put("/api/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).delete(1L);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}