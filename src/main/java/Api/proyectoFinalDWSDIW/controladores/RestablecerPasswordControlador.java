package Api.proyectoFinalDWSDIW.controladores;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Api.proyectoFinalDWSDIW.servicios.RestablecerPasswordServicio;

@RestController
@RequestMapping("/api/usuarios")
public class RestablecerPasswordControlador {

    @Autowired
    private RestablecerPasswordServicio restablecerPasswordServicio;

    // ✅ Endpoint para guardar el token en la base de datos
    @PostMapping("/guardarToken")
    public ResponseEntity<?> guardarToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        LocalDateTime fechaExpiracion = LocalDateTime.parse(request.get("fechaExpiracion"));

        boolean guardado = restablecerPasswordServicio.guardarToken(email, token, fechaExpiracion);

        if (guardado) {
            return ResponseEntity.ok("Token guardado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("Error al guardar el token.");
        }
    }

    // ✅ Endpoint para actualizar la contraseña
    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        System.out.println("🔹 Datos recibidos en la API:");
        System.out.println("Email: " + request.get("email"));
        System.out.println("Token: " + request.get("token"));
        System.out.println("Nueva Contraseña: " + request.get("nuevaContrasena"));

        String email = request.get("email");
        String token = request.get("token");
        String nuevaContrasena = request.get("nuevaContrasena");

        boolean actualizado = restablecerPasswordServicio.actualizarContrasena(token, nuevaContrasena);

        if (actualizado) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            return ResponseEntity.badRequest().body("Error al actualizar la contraseña.");
        }
    }

}
