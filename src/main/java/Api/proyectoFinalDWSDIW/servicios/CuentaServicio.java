package Api.proyectoFinalDWSDIW.servicios;

import org.springframework.stereotype.Service;
import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.CuentaRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServicio {

    private final CuentaRepositorio cuentaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public CuentaServicio(CuentaRepositorio cuentaRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * 🔹 Obtiene todas las cuentas de un usuario por su ID.
     */
    public List<CuentaDao> obtenerCuentasPorUsuario(Long idUsuario) {
        return cuentaRepositorio.findByUsuarioIdUsuario(idUsuario);
    }

    /**
     * 🔹 Crea una cuenta y asigna el ID del usuario en la relación.
     */
    public boolean crearCuenta(Long idUsuario, String nombreCuenta, String tipoCuenta, String ibanCuenta, Double dineroCuenta) {
        Optional<UsuarioDao> usuarioOpt = usuarioRepositorio.findById(idUsuario);
        
        if (usuarioOpt.isPresent()) {  
            // 🔹 Verifica si el IBAN ya existe antes de guardar la cuenta
            if (cuentaRepositorio.findByIbanCuenta(ibanCuenta).isPresent()) {
                return false; // 🔴 No se puede crear porque el IBAN ya está en uso
            }

            CuentaDao nuevaCuenta = new CuentaDao();
            nuevaCuenta.setUsuario(usuarioOpt.get());
            nuevaCuenta.setNombreCuenta(nombreCuenta);
            nuevaCuenta.setTipoCuenta(tipoCuenta);
            nuevaCuenta.setIbanCuenta(ibanCuenta);
            nuevaCuenta.setDineroCuenta(dineroCuenta);
            cuentaRepositorio.save(nuevaCuenta);
            return true;
        }
        return false; // 🔴 No se encontró el usuario
    }

    /**
     * 🔹 Elimina una cuenta si existe en la base de datos.
     */
    public boolean eliminarCuenta(Long idCuenta) {
        if (cuentaRepositorio.existsById(idCuenta)) {
            cuentaRepositorio.deleteById(idCuenta);
            return true;
        }
        return false;
    }
}
