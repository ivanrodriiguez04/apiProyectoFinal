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

        // 🔹 Convertir DTO a entidad
        UsuarioDao usuario = usuarioDto.toEntity();
        usuario.setConfirmado(false);
        usuario.setRolUsuario("usuario");

        usuarioRepositorio.save(usuario);

        // 🔹 Verificar que el token se haya enviado desde la web
        if (usuarioDto.getToken() == null || usuarioDto.getToken().isEmpty()) {
            throw new IllegalArgumentException("No se recibió un token válido.");
        }

        // 🔹 Guardar el token en la base de datos
        TokenDao tokenDao = new TokenDao();
        tokenDao.setToken(usuarioDto.getToken()); // Se usa el token enviado desde la web
        tokenDao.setUsuario(usuario);
        tokenDao.setFechaExpiracion(LocalDateTime.now().plusDays(7));

        tokenRepositorio.save(tokenDao);

        return usuarioDto.getToken(); // Se devuelve el mismo token
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

        // 🔹 Confirmar la cuenta del usuario
        UsuarioDao usuario = tokenDao.getUsuario();
        usuario.setConfirmado(true);
        usuarioRepositorio.save(usuario);

        // 🔹 Eliminar el token después de usarlo
        tokenRepositorio.delete(tokenDao);
        return true;
    }

}

