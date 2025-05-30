package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

/**
 * Servicio para manejar el registro y la confirmación de cuentas de usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class RegistroServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private TokenRepositorio tokenRepositorio;
    
    private static final Logger logger = LoggerFactory.getLogger(RegistroServicio.class);

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuarioDto Datos del usuario a registrar.
     * @return Token de confirmación asociado al usuario registrado.
     * @throws IllegalArgumentException Si el email o el token son nulos o vacíos.
     * @throws IllegalStateException Si el email ya está registrado en el sistema.
     */
    public String registrarUsuario(RegistroDto usuarioDto) {
        logger.info("Intentando registrar usuario con email: {}", usuarioDto.getEmailUsuario());
        
        if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
            logger.warn("Error en el registro: El email es obligatorio.");
            throw new IllegalArgumentException("El email es obligatorio.");
        }

        if (usuarioRepositorio.existsByEmailUsuario(usuarioDto.getEmailUsuario())) {
            logger.warn("Error en el registro: El email {} ya está registrado.", usuarioDto.getEmailUsuario());
            throw new IllegalStateException("El email ya está registrado.");
        }

        UsuarioDao usuario = usuarioDto.toEntity();
        usuario.setConfirmado(false);
        usuario.setRolUsuario("usuario");
        usuarioRepositorio.save(usuario);

        if (usuarioDto.getToken() == null || usuarioDto.getToken().isEmpty()) {
            logger.warn("Error en el registro: No se recibió un token válido.");
            throw new IllegalArgumentException("No se recibió un token válido.");
        }

        TokenDao tokenDao = new TokenDao();
        tokenDao.setToken(usuarioDto.getToken());
        tokenDao.setUsuario(usuario);
        tokenDao.setFechaExpiracion(LocalDateTime.now().plusDays(7));
        tokenRepositorio.save(tokenDao);

        logger.info("Registro exitoso para el usuario con email: {}", usuarioDto.getEmailUsuario());
        return usuarioDto.getToken();
    }
    
    /**
     * Confirma la cuenta de un usuario utilizando un token de validación.
     * 
     * @param token Token de confirmación asociado a la cuenta del usuario.
     * @return true si la cuenta fue confirmada correctamente, false si el token es inválido o expiró.
     */
    public boolean confirmarCuenta(String token) {
        logger.info("Intentando confirmar cuenta con token: {}", token);
        
        Optional<TokenDao> tokenOpt = tokenRepositorio.findByToken(token);
        if (tokenOpt.isEmpty()) {
            logger.warn("Token no encontrado o inválido: {}", token);
            return false;
        }

        TokenDao tokenDao = tokenOpt.get();
        if (tokenDao.estaExpirado()) {
            logger.warn("Token expirado: {}", token);
            tokenRepositorio.delete(tokenDao);
            return false;
        }

        UsuarioDao usuario = tokenDao.getUsuario();
        usuario.setConfirmado(true);
        usuarioRepositorio.save(usuario);

        tokenRepositorio.delete(tokenDao);
        logger.info("Cuenta confirmada con éxito para el usuario con email: {}", usuario.getEmailUsuario());
        return true;
    }
}
