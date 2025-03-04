package Api.proyectoFinalDWSDIW.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Api.proyectoFinalDWSDIW.daos.CuentaDao;

public interface CuentaRepositorio extends JpaRepository<CuentaDao, Long> {
    List<CuentaDao> findByUsuarioIdUsuario(Long idUsuario);
}
