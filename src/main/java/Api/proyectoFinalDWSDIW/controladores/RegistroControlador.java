package Api.proyectoFinalDWSDIW.controladores;

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

@RestController
@RequestMapping("/api/registro")
public class RegistroControlador {
	@Autowired
    private TokenServicio tokenServicio;
	@Autowired
    private RegistroServicio registroServicio;

    @PostMapping("/usuario")
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroDto usuarioDto) {
        try {
            String token = registroServicio.registrarUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registro exitoso. Token generado: " + token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
    
    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarCuenta(@RequestParam("token") String token) {
        try {
            // Verificar si el token existe y es válido
            boolean esValido = tokenServicio.validarToken(token);
            
            if (!esValido) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido o expirado");
            }
            
            // Si es válido, activar la cuenta o realizar la acción necesaria
            tokenServicio.activarCuenta(token);
            return ResponseEntity.ok("Cuenta confirmada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }
}
