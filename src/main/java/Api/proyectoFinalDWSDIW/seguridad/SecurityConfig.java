package Api.proyectoFinalDWSDIW.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de seguridad de la aplicación.
 * Define los beans de codificación de contraseñas.
 * 
 * @author irodhan - 06/03/2025
 */
@Configuration
public class SecurityConfig {
    /**
     * Define un bean para la codificación de contraseñas.
     * 
     * @return Un codificador BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
