package Api.proyectoFinalDWSDIW.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Api.proyectoFinalDWSDIW.daos.CuentaDao;

import java.util.Optional;

public interface CuentaRepositorio extends JpaRepository<CuentaDao, Long> {
    
    List<CuentaDao> findByUsuarioIdUsuario(Long idUsuario);

    // 🔹 Nuevo método para buscar una cuenta por IBAN
    Optional<CuentaDao> findByIbanCuenta(String ibanCuenta);
}

