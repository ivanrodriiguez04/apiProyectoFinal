package Api.proyectoFinalDWSDIW.repositorios;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdministradorRepositorio extends JpaRepository<UsuarioDao, Long> {
    Optional<UsuarioDao> findByEmailUsuario(String email);  // Buscar por email
}
