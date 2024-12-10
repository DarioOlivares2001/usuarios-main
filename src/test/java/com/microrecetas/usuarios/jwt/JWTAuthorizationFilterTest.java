package com.microrecetas.usuarios.jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import jakarta.servlet.FilterChain;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JWTAuthorizationFilterTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @BeforeEach
    public void setUp() {
        jwtAuthorizationFilter = new JWTAuthorizationFilter();
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(jwtAuthorizationFilter)
                .build();
    }

    @Test
    void testDoFilterInternal_withValidJWT() throws Exception {
        // Simulamos una solicitud con un encabezado Authorization válido
        String token = "Bearer valid_jwt_token";  // Aquí deberías usar un JWT válido en tu entorno de pruebas
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        // Act
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        mockMvc.perform(get("/some-url").header("Authorization", token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testDoFilterInternal_withInvalidJWT() throws Exception {
        // Simulamos una solicitud con un encabezado Authorization inválido
        String token = "Bearer invalid_jwt_token";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        // Act
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        mockMvc.perform(get("/some-url").header("Authorization", token))
                .andExpect(status().is4xxClientError());
    }
}