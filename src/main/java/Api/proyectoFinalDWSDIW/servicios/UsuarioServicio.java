package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.RegistroTemporalDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.repositorios.RegistroTemporalRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

/**
 * Servicio que gestiona la autenticación y registro de usuarios, incluyendo
 * validación de credenciales, registro temporal y confirmación de cuentas.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenRepositorio tokenRepositorio;
    @Autowired
    private RegistroTemporalRepositorio registroTemporalRepositorio;
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);

    /**
     * Valida las credenciales de un usuario y verifica si su cuenta está confirmada.
     *
     * @param emailUsuario Correo electrónico del usuario.
     * @param passwordUsuario Contraseña del usuario.
     * @return ResponseEntity con el estado de autenticación o el rol del usuario si es exitoso.
     */
    public ResponseEntity<String> validarCredenciales(String emailUsuario, String passwordUsuario) {
        logger.info("Validando credenciales para el usuario: {}", emailUsuario);

        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);

        if (usuario == null || !passwordEncoder.matches(passwordUsuario, usuario.getPasswordUsuario())) {
            logger.warn("Intento de inicio de sesión fallido. Credenciales incorrectas para el usuario: {}", emailUsuario);
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos.");
        }

        if (!usuario.isConfirmado()) {
            logger.warn("Intento de inicio de sesión fallido. Usuario no confirmado: {}", emailUsuario);
            return ResponseEntity.status(403).body("Debe confirmar su cuenta antes de iniciar sesión.");
        }

        logger.info("Inicio de sesión exitoso para el usuario: {}", emailUsuario);
        return ResponseEntity.ok(usuario.getRolUsuario());
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la base de datos.
     *
     * @param emailUsuario Correo electrónico a verificar.
     * @return true si el correo ya está registrado, false en caso contrario.
     */
    public boolean emailExistsUsuario(String emailUsuario) {
        return usuarioRepositorio.existsByEmailUsuario(emailUsuario);
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuarioDto Datos del usuario a registrar.
     */
    public void registroUsuario(RegistroDto usuarioDto) {
        if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }

        UsuarioDao usuario = new UsuarioDao();
        usuario.setNombreCompletoUsuario(usuarioDto.getNombreCompletoUsuario());
        usuario.setDniUsuario(usuarioDto.getDniUsuario());
        usuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDto.getPasswordUsuario()));
        usuario.setRolUsuario("usuario");
        usuario.setFotoDniFrontalUsuario(usuarioDto.getFotoDniFrontalUsuario());
        usuario.setFotoDniTraseroUsuario(usuarioDto.getFotoDniTraseroUsuario());
        usuario.setFotoUsuario(usuarioDto.getFotoUsuario());

        usuarioRepositorio.save(usuario);
        logger.info("Usuario registrado con éxito: {}", usuario.getEmailUsuario());
    }

    /**
     * Actualiza la contraseña de un usuario utilizando un token de restablecimiento válido.
     *
     * @param token Token de restablecimiento.
     * @param nuevaPassword Nueva contraseña del usuario.
     * @return true si la contraseña se actualizó correctamente, false en caso contrario.
     */
    @Transactional
    public boolean actualizarPassword(String token, String nuevaPassword) {
        logger.info("Intentando actualizar la contraseña con token: {}", token);

        UsuarioDao usuario = usuarioRepositorio.findByToken(token);

        if (usuario == null) {
            logger.error("ERROR: Token inválido o usuario no encontrado.");
            return false;
        }

        usuario.setPasswordUsuario(passwordEncoder.encode(nuevaPassword));
        usuarioRepositorio.save(usuario);
        tokenRepositorio.deleteByToken(token);

        logger.info("Contraseña actualizada con éxito para el usuario: {}", usuario.getEmailUsuario());
        return true;
    }

    /**
     * Guarda un registro temporal de usuario con un token de confirmación.
     *
     * @param usuarioDto Datos del usuario a registrar temporalmente.
     * @param token Token de confirmación asociado.
     * @param fechaExpiracion Fecha de expiración del token.
     */
    public void guardarRegistroTemporal(RegistroDto usuarioDto, String token, LocalDateTime fechaExpiracion) {
        logger.info("Recibiendo datos del usuario para registro temporal...");

        UsuarioDao usuario = new UsuarioDao();
        usuario.setNombreCompletoUsuario(usuarioDto.getNombreCompletoUsuario());
        usuario.setDniUsuario(usuarioDto.getDniUsuario());
        usuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDto.getPasswordUsuario()));
        usuario.setRolUsuario("usuario");
        usuario.setConfirmado(false);
        usuario.setFotoDniFrontalUsuario(usuarioDto.getFotoDniFrontalUsuario());
        usuario.setFotoDniTraseroUsuario(usuarioDto.getFotoDniTraseroUsuario());
        usuario.setFotoUsuario(usuarioDto.getFotoUsuario());

        usuarioRepositorio.save(usuario);
        logger.info("Usuario guardado con éxito: {}", usuario.getEmailUsuario());

        RegistroTemporalDao registroTemporal = new RegistroTemporalDao();
        registroTemporal.setUsuario(usuario);
        registroTemporal.setToken(token);
        registroTemporal.setFechaExpiracion(fechaExpiracion);

        registroTemporalRepositorio.save(registroTemporal);
        logger.info("Registro temporal guardado con token: {}", token);
    }
}