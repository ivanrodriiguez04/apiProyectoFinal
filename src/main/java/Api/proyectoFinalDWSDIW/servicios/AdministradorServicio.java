package Api.proyectoFinalDWSDIW.servicios;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.UsuarioDto;
import Api.proyectoFinalDWSDIW.repositorios.AdministradorRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de administradores y usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class AdministradorServicio {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(AdministradorServicio.class);

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return Lista de todos los usuarios convertidos a DTO.
     */
    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        logger.info("Obteniendo todos los usuarios de la base de datos");
        List<UsuarioDto> usuariosDto = administradorRepositorio.findAll()
            .stream()
            .map(usuario -> convertirADto(usuario))
            .collect(Collectors.toList());

        logger.info("Usuarios obtenidos: {}", usuariosDto.size());
        return usuariosDto;
    }

    /**
     * Convierte un UsuarioDao a un UsuarioDto.
     * 
     * @param usuario UsuarioDao a convertir.
     * @return UsuarioDto con los datos necesarios.
     */
    private UsuarioDto convertirADto(UsuarioDao usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreCompletoUsuario(usuario.getNombreCompletoUsuario());
        dto.setTelefonoUsuario(usuario.getTelefonoUsuario());
        dto.setEmailUsuario(usuario.getEmailUsuario());
        dto.setRolUsuario(usuario.getRolUsuario()); // Agregado para incluir el rol
        return dto;
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    public Optional<UsuarioDto> obtenerUsuarioPorEmail(String email) {
        logger.info("Buscando usuario con email: {}", email);
        Optional<UsuarioDao> usuario = administradorRepositorio.findByEmailUsuario(email);
        if (usuario.isPresent()) {
            logger.info("Usuario encontrado con email: {}", email);
            return Optional.of(convertirADto(usuario.get()));
        } else {
            logger.warn("No se encontró usuario con email: {}", email);
            return Optional.empty();
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id Identificador del usuario a eliminar.
     * @return true si el usuario fue eliminado, false si no se encontró.
     */
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
