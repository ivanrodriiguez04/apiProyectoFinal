package Api.proyectoFinalDWSDIW.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

import java.util.Optional;

/**
 * Servicio que maneja la validación de tokens, activación de cuentas 
 * y restablecimiento de contraseñas.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class TokenServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TokenRepositorio tokenRepositorio;
    private static final Logger logger = LoggerFactory.getLogger(TokenServicio.class);

    /**
     * Verifica si un token es válido y no ha expirado.
     *
     * @param token el token a validar.
     * @return true si es válido, false si no lo es.
     */
    public boolean validarToken(String token) {
        Optional<TokenDao> tokenDaoOpt = tokenRepositorio.findByToken(token);
        if (tokenDaoOpt.isEmpty()) {
            return false; // Token no encontrado
        }
        TokenDao tokenDao = tokenDaoOpt.get();
        return !tokenDao.estaExpirado(); // Verifica que no esté expirado
    }

    /**
     * Activa la cuenta de un usuario utilizando el token de confirmación.
     *
     * @param token el token de confirmación.
     */
    public void activarCuenta(String token) {
        TokenDao tokenDao = tokenRepositorio.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (tokenDao.estaExpirado()) {
            tokenRepositorio.delete(tokenDao); // Se elimina el token expirado
            throw new RuntimeException("El token ha expirado");
        }

        UsuarioDao usuario = tokenDao.getUsuario();
        usuario.setConfirmado(true);
        usuarioRepositorio.save(usuario);

        tokenRepositorio.delete(tokenDao);
    }

    /**
     * Permite restablecer la contraseña de un usuario usando un token válido.
     *
     * @param token el token de restablecimiento de clave.
     * @param nuevaPassword la nueva contraseña del usuario.
     */
    public void restablecerClave(String token, String nuevaPassword) {
        TokenDao tokenDao = tokenRepositorio.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (tokenDao.estaExpirado()) {
            throw new RuntimeException("El token ha expirado");
        }

        UsuarioDao usuario = tokenDao.getUsuario();
        usuario.setPasswordUsuario(new BCryptPasswordEncoder().encode(nuevaPassword));
        usuarioRepositorio.save(usuario);

        tokenRepositorio.delete(tokenDao);
    }
}
