package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public boolean emailExistsUsuario(String emailUsuario) {
        return usuarioRepositorio.existsByEmailUsuario(emailUsuario);
    }

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

    @Transactional
    public boolean confirmarRegistro(String token) {
        logger.info("Intentando confirmar registro con token: {}", token);

        Optional<RegistroTemporalDao> optionalRegistroTemporal = registroTemporalRepositorio.findByToken(token);

        if (optionalRegistroTemporal.isEmpty()) {
            logger.warn("Token no encontrado en la base de datos.");
            return false;
        }

        RegistroTemporalDao registroTemporal = optionalRegistroTemporal.get();

        if (registroTemporal.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            logger.warn("Token expirado.");
            return false;
        }

        UsuarioDao usuario = registroTemporal.getUsuario();

        if (usuario == null) {
            logger.warn("Usuario no encontrado en la base de datos.");
            return false;
        }

        usuario.setConfirmado(true);
        usuarioRepositorio.save(usuario);
        logger.info("Usuario confirmado exitosamente: {}", usuario.getEmailUsuario());

        registroTemporalRepositorio.delete(registroTemporal);
        logger.info("Registro temporal eliminado para el token: {}", token);

        return true;
    }
}
