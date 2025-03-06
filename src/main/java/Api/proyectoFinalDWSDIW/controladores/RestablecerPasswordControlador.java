package Api.proyectoFinalDWSDIW.controladores;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Api.proyectoFinalDWSDIW.servicios.RestablecerPasswordServicio;

@RestController
@RequestMapping("/api/usuarios")
public class RestablecerPasswordControlador {

    @Autowired
    private RestablecerPasswordServicio restablecerPasswordServicio;
    private static final Logger logger = LoggerFactory.getLogger(RestablecerPasswordControlador.class);

    // ✅ Endpoint para guardar el token en la base de datos
    @PostMapping("/guardarToken")
    public ResponseEntity<?> guardarToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        LocalDateTime fechaExpiracion = LocalDateTime.parse(request.get("fechaExpiracion"));

        logger.info("Intentando guardar token para el email: {}", email);
        boolean guardado = restablecerPasswordServicio.guardarToken(email, token, fechaExpiracion);

        if (guardado) {
            logger.info("Token guardado correctamente para el email: {}", email);
            return ResponseEntity.ok("Token guardado correctamente.");
        } else {
            logger.warn("Error al guardar el token para el email: {}", email);
            return ResponseEntity.badRequest().body("Error al guardar el token.");
        }
    }

    // ✅ Endpoint para actualizar la contraseña
    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        String nuevaContrasena = request.get("nuevaContrasena");

        logger.info("Intentando restablecer contraseña para el email: {}", email);
        boolean actualizado = restablecerPasswordServicio.actualizarContrasena(token, nuevaContrasena);

        if (actualizado) {
            logger.info("Contraseña actualizada correctamente para el email: {}", email);
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            logger.warn("Error al actualizar la contraseña para el email: {}", email);
            return ResponseEntity.badRequest().body("Error al actualizar la contraseña.");
        }
    }
}
