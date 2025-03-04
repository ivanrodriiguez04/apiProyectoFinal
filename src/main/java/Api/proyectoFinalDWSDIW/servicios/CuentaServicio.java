package Api.proyectoFinalDWSDIW.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.CuentaRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class CuentaServicio {

    private final CuentaRepositorio cuentaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public CuentaServicio(CuentaRepositorio cuentaRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<CuentaDao> obtenerCuentasPorUsuario(Long idUsuario) {
        return cuentaRepositorio.findByUsuarioIdUsuario(idUsuario);
    }

    public boolean crearCuenta(CuentaDao cuenta) {
        try {
            Optional<UsuarioDao> usuarioOpt = usuarioRepositorio.findById(cuenta.getIdUsuario());
            if (usuarioOpt.isPresent()) {
                cuenta.setUsuario(usuarioOpt.get());  // 🔹 Asigna correctamente el usuario
                cuenta.setDineroCuenta(0.0);
                cuentaRepositorio.save(cuenta);
                return true;
            }
            return false; // Si no existe el usuario, no se crea la cuenta
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarCuenta(Long idCuenta) {
        Optional<CuentaDao> cuenta = cuentaRepositorio.findById(idCuenta);
        if (cuenta.isPresent()) {
            cuentaRepositorio.delete(cuenta.get());
            return true;
        }
        return false;
    }
}
