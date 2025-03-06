package Api.proyectoFinalDWSDIW.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.servicios.AdministradorServicio;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorControlador {

    @Autowired
    private AdministradorServicio administradorServicio;

    private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);
    
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDao>> obtenerUsuarios() {
        logger.info("Solicitud recibida para obtener todos los usuarios");
        List<UsuarioDao> usuarios = administradorServicio.obtenerTodosLosUsuarios();
        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios en la base de datos");
            return ResponseEntity.noContent().build();
        }
        logger.info("Usuarios obtenidos: {}", usuarios.size());
        System.out.println("Usuarios obtenidos desde la API: " + usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        logger.info("Solicitud recibida para eliminar usuario con ID: {}", id);
        boolean eliminado = administradorServicio.eliminarUsuario(id);
        if (eliminado) {
            logger.info("Usuario con ID {} eliminado correctamente", id);
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            logger.warn("Usuario con ID {} no encontrado", id);
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }
}