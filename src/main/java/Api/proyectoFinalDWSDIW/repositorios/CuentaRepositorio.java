package Api.proyectoFinalDWSDIW.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Api.proyectoFinalDWSDIW.daos.CuentaDao;

import java.util.Optional;

/**
 * Repositorio para la entidad Cuenta.
 * Permite acceder a las cuentas almacenadas en la base de datos.
 * 
 * @author irodhan - 06/03/2025
 */
public interface CuentaRepositorio extends JpaRepository<CuentaDao, Long> {
    /**
     * Encuentra todas las cuentas asociadas a un usuario específico.
     * 
     * @param idUsuario ID del usuario.
     * @return Lista de cuentas del usuario.
     */
    List<CuentaDao> findByUsuarioIdUsuario(Long idUsuario);

    /**
     * Busca una cuenta por su IBAN.
     * 
     * @param ibanCuenta IBAN de la cuenta.
     * @return Un Optional que contiene la cuenta si se encuentra.
     */
    Optional<CuentaDao> findByIbanCuenta(String ibanCuenta);
}

