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

@RestController
@RequestMapping("/api/registro")
public class RegistroControlador {
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
    public ResponseEntity<Object> confirmarCuenta(@RequestParam("token") String token) {
        boolean confirmado = registroServicio.confirmarCuenta(token);

        if (confirmado) {
            System.out.println("✅ Usuario confirmado correctamente. Redirigiendo...");

            // 🔹 Redirigir a la página de inicio de sesión
            return ResponseEntity.status(302)
                    .header("Location", "http://localhost:8080/inicioSesion.jsp")
                    .build();
        } else {
            System.out.println("⚠️ Token inválido o expirado. No se puede confirmar el usuario.");

            // 🔹 Redirigir a una página de error
            return ResponseEntity.status(302)
                    .header("Location", "http://localhost:8080/registro.jsp?error=tokenInvalido")
                    .build();
        }
    }
}
