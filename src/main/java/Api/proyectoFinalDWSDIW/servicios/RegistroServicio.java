package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class RegistroServicio {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenRepositorio tokenRepositorio;

    public String registrarUsuario(RegistroDto usuarioDto) {
        if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }

        if (usuarioRepositorio.existsByEmailUsuario(usuarioDto.getEmailUsuario())) {
            throw new IllegalStateException("El email ya está registrado.");
        }

        // 🔹 Convertir el DTO en UsuarioDao
        UsuarioDao usuario = usuarioDto.toEntity();
        usuario.setConfirmado(false); // Usuario aún no confirmado
        usuario.setRolUsuario("usuario"); // 🔹 Establecer rol por defecto
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDto.getPasswordUsuario())); // 🔹 Encriptar contraseña

        usuarioRepositorio.save(usuario);

        // 🔹 Generar y guardar el token en su propia tabla
        String token = UUID.randomUUID().toString();
        TokenDao tokenDao = new TokenDao();
        tokenDao.setToken(token);
        tokenDao.setUsuario(usuario);
        tokenDao.setFechaExpiracion(LocalDateTime.now().plusDays(7)); // 🔹 Asegurar que expire en 7 días

        tokenRepositorio.save(tokenDao);

        return token;
    }

    
    public boolean confirmarCuenta(String token) {
        // 🔹 Buscar el token en la base de datos
        Optional<TokenDao> tokenOpt = tokenRepositorio.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return false; // Token no encontrado
        }

        TokenDao tokenDao = tokenOpt.get();

        // 🔹 Verificar si el token ha expirado
        if (tokenDao.estaExpirado()) {
            tokenRepositorio.delete(tokenDao); // Eliminar el token expirado
            return false;
        }

        // 🔹 Marcar al usuario como confirmado
        UsuarioDao usuario = tokenDao.getUsuario();
        usuario.setConfirmado(true);
        usuarioRepositorio.save(usuario);

        // 🔹 Eliminar el token una vez usado
        tokenRepositorio.delete(tokenDao);
        return true;
    }
}

