package Api.proyectoFinalDWSDIW.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
/**
 * Repositorio para la entidad Usuario.
 * Maneja la persistencia de los datos de los usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDao, Long> {
    /**
     * Busca un usuario por su email.
     * 
     * @param emailUsuario Email del usuario.
     * @return El usuario encontrado.
     */
    UsuarioDao findByEmailUsuario(String emailUsuario);

    /**
     * Verifica si existe un usuario con el email dado.
     * 
     * @param emailUsuario Email a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    boolean existsByEmailUsuario(String emailUsuario);

    /**
     * Encuentra un usuario basado en su token de autenticación.
     * 
     * @param token Token de autenticación.
     * @return Usuario asociado al token.
     */
    @Query("SELECT t.usuario FROM TokenDao t WHERE t.token = :token")
    UsuarioDao findByToken(@Param("token") String token);
}