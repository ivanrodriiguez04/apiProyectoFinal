package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.daos.TransferenciaDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.TransferenciaDto;
import Api.proyectoFinalDWSDIW.repositorios.CuentaRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.TransferenciaRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class TransferenciaServicio {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;

    /**
     * Ejecuta una transferencia bancaria entre dos cuentas si el usuario es válido y dueño de la cuenta origen.
     * 
     * @param dto Datos de la transferencia
     * @throws IllegalArgumentException Si hay un error lógico en la operación
     */
    @Transactional
    public void realizarTransferencia(TransferenciaDto dto) {
        // Buscar el usuario que realiza la transferencia
        UsuarioDao usuario = usuarioRepositorio.findById(dto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        // Buscar la cuenta de origen y validar propiedad
        CuentaDao cuentaOrigen = cuentaRepositorio.findByIbanCuenta(dto.getIbanOrigen())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de origen no encontrada."));

        Long usuarioCuentaId = cuentaOrigen.getIdUsuario(); // método seguro

        if (usuarioCuentaId == null) {
            throw new IllegalArgumentException("La cuenta de origen no está asignada a ningún usuario.");
        }

        if (!usuarioCuentaId.equals(dto.getIdUsuario())) {
            throw new IllegalArgumentException("No puedes transferir desde una cuenta que no te pertenece.");
        }


        // Buscar la cuenta de destino
        CuentaDao cuentaDestino = cuentaRepositorio.findByIbanCuenta(dto.getIbanDestino())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de destino no encontrada."));

        // Validar fondos suficientes
        if (cuentaOrigen.getDineroCuenta() < dto.getCantidadTransferencia()) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta de origen.");
        }

        // Actualizar saldos
        cuentaOrigen.setDineroCuenta(cuentaOrigen.getDineroCuenta() - dto.getCantidadTransferencia());
        cuentaDestino.setDineroCuenta(cuentaDestino.getDineroCuenta() + dto.getCantidadTransferencia());

        cuentaRepositorio.save(cuentaOrigen);
        cuentaRepositorio.save(cuentaDestino);

        // Registrar transferencia
        TransferenciaDao transferencia = new TransferenciaDao();
        transferencia.setIbanOrigen(dto.getIbanOrigen());
        transferencia.setIbanDestino(dto.getIbanDestino());
        transferencia.setCantidadTransferencia(dto.getCantidadTransferencia());
        transferencia.setFechaTransferencia(LocalDateTime.now());
        transferencia.setUsuario(usuario);

        transferenciaRepositorio.save(transferencia);
    }
}
