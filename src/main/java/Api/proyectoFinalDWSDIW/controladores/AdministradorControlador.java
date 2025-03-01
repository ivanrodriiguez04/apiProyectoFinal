package Api.proyectoFinalDWSDIW.controladores;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.servicios.AdministradorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorControlador {

    @Autowired
    private AdministradorServicio administradorServicio;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDao>> obtenerUsuarios() {
        List<UsuarioDao> usuarios = administradorServicio.obtenerTodosLosUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("Usuarios obtenidos desde la API: " + usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = administradorServicio.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }
}
