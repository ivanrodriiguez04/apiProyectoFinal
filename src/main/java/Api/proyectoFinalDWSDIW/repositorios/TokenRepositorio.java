package Api.proyectoFinalDWSDIW.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
/**
 * Repositorio para la entidad Token.
 * Maneja la persistencia de los tokens de autenticación.
 * 
 * @author irodhan - 06/03/2025
 */
@Repository
public interface TokenRepositorio extends JpaRepository<TokenDao, Long> {
    /**
     * Busca un token por su valor.
     * 
     * @param token Token de autenticación.
     * @return Un Optional que contiene el token si se encuentra.
     */
    Optional<TokenDao> findByToken(String token);

    /**
     * Busca un token asociado a un usuario.
     * 
     * @param usuario Usuario asociado al token.
     * @return Un Optional con el token encontrado.
     */
    Optional<TokenDao> findByUsuario(UsuarioDao usuario);

    /**
     * Elimina un token específico por su valor.
     * 
     * @param token Token a eliminar.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM TokenDao t WHERE t.token = :token")
    void deleteByToken(@Param("token") String token);

    /**
     * Elimina todos los tokens de un usuario antes de generar uno nuevo.
     * 
     * @param usuario Usuario cuyos tokens serán eliminados.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM TokenDao t WHERE t.usuario = :usuario")
    void deleteByUsuario(@Param("usuario") UsuarioDao usuario);
}
