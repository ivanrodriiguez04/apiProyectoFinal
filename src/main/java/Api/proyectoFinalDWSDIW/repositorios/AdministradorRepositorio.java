package Api.proyectoFinalDWSDIW.repositorios;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad Administrador.
 * Proporciona métodos de acceso a la base de datos para los administradores.
 * 
 * @author irodhan - 06/03/2025
 */
@Repository
public interface AdministradorRepositorio extends JpaRepository<UsuarioDao, Long> {
    /**
     * Busca un usuario por su dirección de correo electrónico.
     * 
     * @param email El email del usuario.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    Optional<UsuarioDao> findByEmailUsuario(String email);
} 