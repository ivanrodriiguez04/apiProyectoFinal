package Api.proyectoFinalDWSDIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RestablecerPasswordServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TokenRepositorio tokenRepositorio;

    // ✅ Guardar el token en la base de datos
    public boolean guardarToken(String email, String token, LocalDateTime fechaExpiracion) {
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(email);
        
        if (usuario == null) {
            System.out.println("❌ No se encontró un usuario con el email: " + email);
            return false;
        }

        // ✅ Eliminar tokens antiguos antes de guardar el nuevo
        tokenRepositorio.deleteByUsuario(usuario);

        TokenDao nuevoToken = new TokenDao();
        nuevoToken.setToken(token);
        nuevoToken.setUsuario(usuario);
        nuevoToken.setFechaExpiracion(fechaExpiracion);

        tokenRepositorio.save(nuevoToken);

        System.out.println("✅ Token guardado correctamente para el usuario: " + email);
        return true;
    }

    // ✅ Verificar y actualizar la contraseña (sin doble encriptación)
    public boolean actualizarContrasena(String token, String contrasenaEncriptada) {
        Optional<TokenDao> tokenEntidadOpt = tokenRepositorio.findByToken(token);

        if (tokenEntidadOpt.isEmpty()) {
            System.out.println("❌ Token no encontrado en la base de datos.");
            return false;
        }

        TokenDao tokenEntidad = tokenEntidadOpt.get();

        // ✅ Verificar si el token ha expirado
        if (tokenEntidad.estaExpirado()) {
            System.out.println("❌ El token ha expirado.");
            return false;
        }

        // ✅ Obtener el usuario desde el token
        UsuarioDao usuario = tokenEntidad.getUsuario();

        // ✅ Guardar la contraseña sin volver a encriptarla
        usuario.setPasswordUsuario(contrasenaEncriptada);
        usuarioRepositorio.save(usuario);

        // ✅ Eliminar el token después de usarlo
        tokenRepositorio.deleteByToken(tokenEntidad.getToken());
        System.out.println("✅ Contraseña actualizada y token eliminado correctamente.");

        return true;
    }
}
