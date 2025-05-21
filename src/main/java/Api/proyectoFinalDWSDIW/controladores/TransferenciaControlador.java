package Api.proyectoFinalDWSDIW.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.TransferenciaDto;
import Api.proyectoFinalDWSDIW.servicios.TransferenciaServicio;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaControlador {
	@Autowired
    private TransferenciaServicio transferenciasServicio;
    
    private static final Logger logger = LoggerFactory.getLogger(TransferenciaControlador.class);

    @PostMapping("/enviarDinero")
    public ResponseEntity<String> enviarDinero(@RequestBody TransferenciaDto dto) {
        try {
        	transferenciasServicio.realizarTransferencia(dto);
            return ResponseEntity.ok("Transferencia realizada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
