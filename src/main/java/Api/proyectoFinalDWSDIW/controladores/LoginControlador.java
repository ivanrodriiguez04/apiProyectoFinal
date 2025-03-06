package Api.proyectoFinalDWSDIW.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.LoginDto;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;

/**
 * Controlador para la autenticación de usuarios.
 * Proporciona un endpoint para validar credenciales.
 * 
 * @author irodhan - 06/03/2025
 */
@RestController
@RequestMapping("/api/login")
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    private static final Logger logger = LoggerFactory.getLogger(LoginControlador.class);

    /**
     * Valida las credenciales de un usuario.
     * 
     * @param usuario Objeto con email y contraseña del usuario
     * @return ResponseEntity con mensaje de autenticación
     */
    @PostMapping("/validarUsuario")
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginDto usuario) {
        logger.info("Intento de inicio de sesión para el usuario: {}", usuario.getEmailUsuario());

        ResponseEntity<String> response = usuarioServicio.validarCredenciales(usuario.getEmailUsuario(), usuario.getPasswordUsuario());

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Inicio de sesión exitoso para el usuario: {}", usuario.getEmailUsuario());
        } else {
            logger.warn("Intento de inicio de sesión fallido. Credenciales incorrectas o usuario no confirmado: {}", usuario.getEmailUsuario());
        }

        return response;
    }
}
