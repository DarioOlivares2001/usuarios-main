package com.microrecetas.usuarios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.microrecetas.usuarios.jwt.JWTAuthtenticationConfig;
import com.microrecetas.usuarios.model.User;
import com.microrecetas.usuarios.service.CustomUserDetailsService;
import com.microrecetas.usuarios.service.RecetaService;
import com.microrecetas.usuarios.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RecetaService recetaService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        doNothing().when(userService).registerUser(any(User.class));

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\", \"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testLoginUserSuccess() throws Exception {
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn(encodedPassword);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtAuthtenticationConfig.getJWTToken(username)).thenReturn("mockToken");

        mockMvc.perform(post("/api/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("mockToken"));

        verify(userDetailsService, times(1)).loadUserByUsername(username);
    }

    @Test
    void testLoginUserInvalidPassword() throws Exception {
        String username = "testuser";
        String password = "wrongpassword";
        String encodedPassword = "encodedPassword";

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn(encodedPassword);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        mockMvc.perform(post("/api/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));

        verify(userDetailsService, times(1)).loadUserByUsername(username);
    }

    @Test
    void testGetNombresRecetas() throws Exception {
        List<String> nombresRecetas = List.of("Receta1", "Receta2");

        when(recetaService.obtenerNombresDeRecetas()).thenReturn(nombresRecetas);

        mockMvc.perform(get("/api/recetas/nombres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("Receta1"))
                .andExpect(jsonPath("$[1]").value("Receta2"));

        verify(recetaService, times(1)).obtenerNombresDeRecetas();
    }

    @Test
    void testInsertarMultiplesRecetas() throws Exception {
        doNothing().when(recetaService).guardarRecetas(anyList());

        String recetasJson = "[{\"nombre\":\"Receta1\"}, {\"nombre\":\"Receta2\"}]";

        mockMvc.perform(post("/api/recetas/multiples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recetasJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Recetas insertadas exitosamente"));

        verify(recetaService, times(1)).guardarRecetas(anyList());
    }
}
