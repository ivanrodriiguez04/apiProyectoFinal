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
        if (idUsuario == null || idUsuario <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID de usuario es inválido"));
        }
        List<CuentaDao> cuentas = cuentaServicio.obtenerCuentasPorUsuario(idUsuario);
        if (cuentas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No hay cuentas para este usuario"));
        }
        return ResponseEntity.ok(cuentas);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody CuentaDao cuentaDao) {
        System.out.println("Recibido: " + cuentaDao);

        if (cuentaDao.getIdUsuario() == null || cuentaDao.getIdUsuario() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID de usuario es obligatorio y debe ser válido"));
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
        if (idCuenta == null || idCuenta <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID de cuenta es inválido"));
        }
        boolean eliminado = cuentaServicio.eliminarCuenta(idCuenta);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Cuenta eliminada con éxito"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cuenta no encontrada"));
        }
    }
}
