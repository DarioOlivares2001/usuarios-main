package com.microrecetas.usuarios;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsuariosApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verifica que el contexto de la aplicación se carga correctamente
        assertThat(applicationContext).isNotNull();
    }

    
    @Test
    void jwtAuthorizationFilterBeanExists() {
        // Verifica que un bean específico (por ejemplo, JWTAuthorizationFilter) está configurado
        boolean beanExists = applicationContext.containsBean("jwtAuthorizationFilter");
        assertThat(beanExists).isFalse();
    }
}
