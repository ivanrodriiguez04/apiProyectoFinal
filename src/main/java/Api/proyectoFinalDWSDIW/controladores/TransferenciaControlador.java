package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Api.proyectoFinalDWSDIW.dtos.TransferenciaDto;
import Api.proyectoFinalDWSDIW.servicios.TransferenciaServicio;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaControlador {

    @Autowired
    private TransferenciaServicio transferenciaServicio;

    /**
     * Endpoint para realizar una transferencia si el usuario es propietario de la cuenta de origen.
     * 
     * @param dto Datos de la transferencia
     * @return Resultado de la operación
     */
    @PostMapping("/enviarDinero")
    public ResponseEntity<String> enviarTransferencia(@RequestBody TransferenciaDto dto) {
        try {
            transferenciaServicio.realizarTransferencia(dto);
            return ResponseEntity.ok("✅ Transferencia realizada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error inesperado: " + e.getMessage());
        }
    }
}
