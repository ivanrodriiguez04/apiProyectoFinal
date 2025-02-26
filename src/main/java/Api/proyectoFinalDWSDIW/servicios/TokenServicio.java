package Api.proyectoFinalDWSDIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class TokenServicio {
	@Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TokenRepositorio tokenRepositorio;

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
