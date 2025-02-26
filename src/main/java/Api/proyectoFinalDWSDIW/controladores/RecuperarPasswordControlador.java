package Api.proyectoFinalDWSDIW.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.RecuperarPasswordDto;
import Api.proyectoFinalDWSDIW.servicios.TokenServicio;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;
/*
@RestController
@RequestMapping("/api/password")
public class RecuperarPasswordControlador {

	@Autowired
    private UsuarioServicio usuarioServicio;
	@Autowired
    private TokenServicio tokenServicio;

    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarClave(@RequestBody RecuperarPasswordDto solicitud) {
    	tokenServicio.enviarCorreoRecuperacion(solicitud.getEmailUsuario());
        return ResponseEntity.ok("Se ha enviado un correo con instrucciones para recuperar la clave.");
    }

    @PostMapping("/restablecer")
    public ResponseEntity<String> restablecerPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String nuevaPassword = requestBody.get("passwordUsuario");

        System.out.println("Token recibido en la API: " + token);
        System.out.println("Nueva contraseña en la API: " + nuevaPassword);

        if (nuevaPassword == null || nuevaPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: La contraseña no puede estar vacía.");
        }

        usuarioServicio.actualizarPassword(token, nuevaPassword);
        return ResponseEntity.ok("Contraseña restablecida con éxito.");
    }

}
*/