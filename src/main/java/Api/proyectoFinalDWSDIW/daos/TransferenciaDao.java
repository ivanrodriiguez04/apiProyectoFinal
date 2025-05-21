package Api.proyectoFinalDWSDIW.daos;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * Entidad JPA que representa la tabla 'transferencia' en el esquema 'logica_proyecto_final'.
 */
@Entity
@Table(name = "transferencia", schema = "logica_proyecto_final")
public class TransferenciaDao {

    /**
     * Identificador único de la transferencia.
     * Se genera automáticamente y corresponde a la clave primaria de la tabla.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia")
    private Long idTransferencia;

    /**
     * IBAN de origen de la transferencia.
     */
    @Column(name = "iban_origen", nullable = false, length = 34)
    private String ibanOrigen;

    /**
     * IBAN de destino de la transferencia.
     */
    @Column(name = "iban_destino", nullable = false, length = 34)
    private String ibanDestino;

    /**
     * Cantidad de dinero transferida.
     */
    @Column(name = "cantidad_transferencia", nullable = false)
    private double cantidadTransferencia;

    /**
     * Fecha y hora en que se realizó la transferencia.
     */
    @Column(name = "fecha_transferencia", nullable = false)
    private LocalDateTime fechaTransferencia;

    // Getters y Setters

    public Long getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Long idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public String getIbanOrigen() {
        return ibanOrigen;
    }

    public void setIbanOrigen(String ibanOrigen) {
        this.ibanOrigen = ibanOrigen;
    }

    public String getIbanDestino() {
        return ibanDestino;
    }

    public void setIbanDestino(String ibanDestino) {
        this.ibanDestino = ibanDestino;
    }

    public double getCantidadTransferencia() {
        return cantidadTransferencia;
    }

    public void setCantidadTransferencia(double cantidadTransferencia) {
        this.cantidadTransferencia = cantidadTransferencia;
    }

    public LocalDateTime getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(LocalDateTime fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }
}
