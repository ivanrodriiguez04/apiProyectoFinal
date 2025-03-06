package Api.proyectoFinalDWSDIW.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.CuentaRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de cuentas de usuario.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class CuentaServicio {

    private final CuentaRepositorio cuentaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private static final Logger logger = LoggerFactory.getLogger(CuentaServicio.class);

    /**
     * Constructor del servicio de cuenta.
     * @param cuentaRepositorio Repositorio de cuentas.
     * @param usuarioRepositorio Repositorio de usuarios.
     */
    public CuentaServicio(CuentaRepositorio cuentaRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * Obtiene una lista de cuentas asociadas a un usuario.
     * @param idUsuario ID del usuario.
     * @return Lista de cuentas del usuario.
     */
    public List<CuentaDao> obtenerCuentasPorUsuario(Long idUsuario) {
        logger.info("Obteniendo cuentas para el usuario con ID: {}", idUsuario);
        return cuentaRepositorio.findByUsuarioIdUsuario(idUsuario);
    }

    /**
     * Crea una nueva cuenta para un usuario dado.
     * @param idUsuario ID del usuario al que se asociará la cuenta.
     * @param nombreCuenta Nombre de la cuenta.
     * @param tipoCuenta Tipo de cuenta.
     * @param ibanCuenta IBAN de la cuenta.
     * @param dineroCuenta Saldo inicial de la cuenta.
     * @return true si la cuenta se creó con éxito, false en caso contrario.
     */
    public boolean crearCuenta(Long idUsuario, String nombreCuenta, String tipoCuenta, String ibanCuenta, Double dineroCuenta) {
        logger.info("Intentando crear una cuenta para el usuario con ID: {}", idUsuario);
        Optional<UsuarioDao> usuarioOpt = usuarioRepositorio.findById(idUsuario);
        
        if (usuarioOpt.isPresent()) {
            if (cuentaRepositorio.findByIbanCuenta(ibanCuenta).isPresent()) {
                logger.warn("No se pudo crear la cuenta. IBAN {} ya está en uso", ibanCuenta);
                return false;
            }

            CuentaDao nuevaCuenta = new CuentaDao();
            nuevaCuenta.setUsuario(usuarioOpt.get());
            nuevaCuenta.setNombreCuenta(nombreCuenta);
            nuevaCuenta.setTipoCuenta(tipoCuenta);
            nuevaCuenta.setIbanCuenta(ibanCuenta);
            nuevaCuenta.setDineroCuenta(dineroCuenta);
            cuentaRepositorio.save(nuevaCuenta);
            
            logger.info("Cuenta creada con éxito para el usuario con ID: {}", idUsuario);
            return true;
        }
        
        logger.warn("No se pudo crear la cuenta. Usuario con ID {} no encontrado", idUsuario);
        return false;
    }

    /**
     * Elimina una cuenta por su ID.
     * @param idCuenta ID de la cuenta a eliminar.
     * @return true si la cuenta se eliminó con éxito, false en caso contrario.
     */
    public boolean eliminarCuenta(Long idCuenta) {
        logger.info("Intentando eliminar la cuenta con ID: {}", idCuenta);
        if (cuentaRepositorio.existsById(idCuenta)) {
            cuentaRepositorio.deleteById(idCuenta);
            logger.info("Cuenta con ID {} eliminada con éxito", idCuenta);
            return true;
        }
        
        logger.warn("No se pudo eliminar la cuenta. Cuenta con ID {} no encontrada", idCuenta);
        return false;
    }
}