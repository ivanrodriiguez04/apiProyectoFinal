package Api.proyectoFinalDWSDIW.servicios;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.AdministradorRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorServicio {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(AdministradorServicio.class);

    public List<UsuarioDao> obtenerTodosLosUsuarios() {
        logger.info("Obteniendo todos los usuarios de la base de datos");
        List<UsuarioDao> usuarios = administradorRepositorio.findAll();
        logger.info("Usuarios obtenidos: {}", usuarios.size());
        return usuarios;
    }

    public Optional<UsuarioDao> obtenerUsuarioPorEmail(String email) {
        logger.info("Buscando usuario con email: {}", email);
        Optional<UsuarioDao> usuario = administradorRepositorio.findByEmailUsuario(email);
        if (usuario.isPresent()) {
            logger.info("Usuario encontrado con email: {}", email);
        } else {
            logger.warn("No se encontró usuario con email: {}", email);
        }
        return usuario;
    }

    public boolean eliminarUsuario(Long id) {
        logger.info("Solicitud para eliminar usuario con ID: {}", id);
        if (administradorRepositorio.existsById(id)) {
            administradorRepositorio.deleteById(id);
            logger.info("Usuario con ID {} eliminado correctamente", id);
            System.out.println("Usuario con ID " + id + " eliminado correctamente.");
            return true;
        } else {
            logger.warn("Usuario con ID {} no encontrado", id);
            System.err.println("Usuario con ID " + id + " no encontrado.");
            return false;
        }
    }
}
