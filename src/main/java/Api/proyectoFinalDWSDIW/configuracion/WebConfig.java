package Api.proyectoFinalDWSDIW.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para la aplicación.
 * Permite el acceso a la API desde orígenes específicos y define métodos HTTP permitidos.
 * 
 * @author irodhan - 06/03/2025
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas de CORS para la aplicación.
     * 
     * @param registry el registro de configuración de CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica las reglas a todas las rutas bajo "/api/**"
                .allowedOrigins("http://16.170.127.156:8080") // Permite solicitudes desde estos orígenes
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los encabezados en la solicitud
                .allowCredentials(true); // Permite el uso de cookies o credenciales en las solicitudes
    }
}
