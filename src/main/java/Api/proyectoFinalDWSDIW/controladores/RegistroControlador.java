package Api.proyectoFinalDWSDIW.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.servicios.RegistroServicio;
import Api.proyectoFinalDWSDIW.servicios.TokenServicio;

/**
 * Controlador para la gestión del registro de usuarios.
 * Proporciona endpoints para registrar y confirmar cuentas.
 * 
 * @author irodhan - 06/03/2025
 */
@RestController
@RequestMapping("/api/registro")
public class RegistroControlador {
    @Autowired
    private TokenServicio tokenServicio;
    @Autowired
    private RegistroServicio registroServicio;
    private static final Logger logger = LoggerFactory.getLogger(RegistroControlador.class);

    /**
     * Registra un nuevo usuario.
     * 
     * @param usuarioDto Datos del usuario a registrar
     * @return ResponseEntity con el resultado del registro
     */
    @PostMapping("/usuario")
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroDto usuarioDto) {
        try {
            logger.info("Intentando registrar un nuevo usuario con email: {}", usuarioDto.getEmailUsuario());
            String token = registroServicio.registrarUsuario(usuarioDto);
            logger.info("Registro exitoso para el usuario con email: {}", usuarioDto.getEmailUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body("Registro exitoso. Token generado: " + token);
        } catch (IllegalArgumentException e) {
            logger.warn("Error en el registro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            logger.warn("Error de conflicto en el registro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error interno en el registro: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
    
    /**
     * Confirma la cuenta de un usuario mediante un token.
     * 
     * @param token Token de confirmación
     * @return ResponseEntity con el resultado de la confirmación
     */
    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarCuenta(@RequestParam("token") String token) {
        try {
            logger.info("Intentando confirmar cuenta con token: {}", token);
            boolean esValido = tokenServicio.validarToken(token);
            
            if (!esValido) {
                logger.warn("Token inválido o expirado: {}", token);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido o expirado");
            }
            
            tokenServicio.activarCuenta(token);
            logger.info("Cuenta confirmada con éxito para el token: {}", token);
            return ResponseEntity.ok("Cuenta confirmada con éxito");
        } catch (Exception e) {
            logger.error("Error interno en la confirmación de cuenta: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }
}
