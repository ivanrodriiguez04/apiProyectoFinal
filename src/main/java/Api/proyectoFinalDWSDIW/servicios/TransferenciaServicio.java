package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.daos.TransferenciaDao;
import Api.proyectoFinalDWSDIW.dtos.TransferenciaDto;
import Api.proyectoFinalDWSDIW.repositorios.CuentaRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.TransferenciaRepositorio;

/**
 * Servicio para la gestión de transferencias en la aplicacion.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class TransferenciaServicio {

	@Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;

    /**
     * Busca una cuenta por su IBAN.
     */
    public CuentaDao buscarCuentaPorIban(String iban) {
        return cuentaRepositorio.findByIbanCuenta(iban)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con IBAN: " + iban));
    }

    /**
     * Realiza una transferencia entre dos cuentas.
     */
    @Transactional
    public void realizarTransferencia(TransferenciaDto dto) {
        CuentaDao cuentaOrigen = buscarCuentaPorIban(dto.getIbanOrigen());
        CuentaDao cuentaDestino = buscarCuentaPorIban(dto.getIbanDestino());

        if (cuentaOrigen.getDineroCuenta() < dto.getCantidadTransferencia()) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta de origen");
        }

        // Descontar del origen
        cuentaOrigen.setDineroCuenta(cuentaOrigen.getDineroCuenta() - dto.getCantidadTransferencia());

        // Sumar al destino
        cuentaDestino.setDineroCuenta(cuentaDestino.getDineroCuenta() + dto.getCantidadTransferencia());

        // Guardar actualizaciones
        cuentaRepositorio.save(cuentaOrigen);
        cuentaRepositorio.save(cuentaDestino);

        // Registrar transferencia
        TransferenciaDao transferencia = new TransferenciaDao();
        transferencia.setIbanOrigen(dto.getIbanOrigen());
        transferencia.setIbanDestino(dto.getIbanDestino());
        transferencia.setCantidadTransferencia(dto.getCantidadTransferencia());
        transferencia.setFechaTransferencia(LocalDateTime.now());

        transferenciaRepositorio.save(transferencia);
    }
}
