package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.servicios.CuentaServicio;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    @Autowired
    private CuentaServicio cuentaServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    /**
     * 🔹 Obtiene todas las cuentas de un usuario usando su ID.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerCuentasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        List<CuentaDao> cuentas = cuentaServicio.obtenerCuentasPorUsuario(idUsuario);

        if (cuentas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No hay cuentas para este usuario"));
        }

        return ResponseEntity.ok(cuentas);
    }

    /**
     * 🔹 Obtiene todas las cuentas de un usuario usando su EMAIL.
     */
    @GetMapping("/usuario/email/{email}")
    public ResponseEntity<?> obtenerCuentasPorEmail(@PathVariable("email") String emailUsuario) {
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }

        List<CuentaDao> cuentas = cuentaServicio.obtenerCuentasPorUsuario(usuario.getIdUsuario());

        if (cuentas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No hay cuentas para este usuario"));
        }

        return ResponseEntity.ok(cuentas);
    }

    /**
     * 🔹 Crea una nueva cuenta y asigna el ID del usuario encontrado por su email.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody Map<String, String> requestBody) {
        String emailUsuario = requestBody.get("emailUsuario");
        String nombreCuenta = requestBody.get("nombreCuenta");
        String tipoCuenta = requestBody.get("tipoCuenta");
        String ibanCuenta = requestBody.get("ibanCuenta");
        Double dineroCuenta = Double.parseDouble(requestBody.get("dineroCuenta"));

        if (emailUsuario == null || nombreCuenta == null || tipoCuenta == null || ibanCuenta == null || dineroCuenta == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Todos los campos son obligatorios"));
        }

        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }

        boolean creada = cuentaServicio.crearCuenta(usuario.getIdUsuario(), nombreCuenta, tipoCuenta, ibanCuenta, dineroCuenta);

        if (creada) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Cuenta creada con éxito"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El IBAN ya está en uso"));
        }
    }

    /**
     * 🔹 Elimina una cuenta por su ID.
     */
    @DeleteMapping("/eliminar/{idCuenta}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable("idCuenta") Long idCuenta) {
        boolean eliminado = cuentaServicio.eliminarCuenta(idCuenta);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Cuenta eliminada con éxito"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cuenta no encontrada"));
        }
    }
}
