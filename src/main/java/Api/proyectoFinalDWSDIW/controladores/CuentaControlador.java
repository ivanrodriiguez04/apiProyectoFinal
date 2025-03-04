package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.servicios.CuentaServicio;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    @Autowired
    private CuentaServicio cuentaServicio;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerCuentasPorUsuario(@PathVariable Long idUsuario) {
        List<CuentaDao> cuentas = cuentaServicio.obtenerCuentasPorUsuario(idUsuario);
        if (cuentas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No hay cuentas para este usuario"));
        }
        return ResponseEntity.ok(cuentas);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody CuentaDao cuentaDao) {
        System.out.println("Recibido: " + cuentaDao);

        if (cuentaDao.getIdUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID de usuario es obligatorio"));
        }
        if (cuentaDao.getIbanCuenta() == null || cuentaDao.getIbanCuenta().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El IBAN es obligatorio"));
        }

        boolean creada = cuentaServicio.crearCuenta(cuentaDao);
        if (creada) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Cuenta creada con éxito"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No se pudo crear la cuenta"));
        }
    }

    @DeleteMapping("/eliminar/{idCuenta}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Long idCuenta) {
        boolean eliminado = cuentaServicio.eliminarCuenta(idCuenta);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Cuenta eliminada con éxito"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cuenta no encontrada"));
        }
    }
}