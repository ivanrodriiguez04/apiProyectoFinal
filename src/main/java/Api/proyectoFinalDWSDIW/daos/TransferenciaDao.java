package Api.proyectoFinalDWSDIW.daos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla 'transferencia' en el esquema 'logica_proyecto_final'.
 */
@Entity
@Table(name = "transferencia", schema = "logica_proyecto_final")
public class TransferenciaDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia")
    private Long idTransferencia;

    @Column(name = "iban_origen", nullable = false)
    private String ibanOrigen;

    @Column(name = "iban_destino", nullable = false)
    private String ibanDestino;

    @Column(name = "cantidad_transferencia", nullable = false)
    private double cantidadTransferencia;

    @Column(name = "fecha_transferencia", nullable = false)
    private LocalDateTime fechaTransferencia;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true) // Paso 1: lo dejas nullable=true temporalmente
    @JsonIgnoreProperties("transferencias")
    private UsuarioDao usuario;

    @Transient
    @JsonProperty("idUsuario")
    private Long idUsuario;

    // ===== Getters y Setters =====

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

    public UsuarioDao getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDao usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            this.idUsuario = usuario.getIdUsuario();
        }
    }

    public Long getIdUsuario() {
        return (usuario != null) ? usuario.getIdUsuario() : idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        if (this.usuario == null) {
            this.usuario = new UsuarioDao();
        }
        this.usuario.setIdUsuario(idUsuario);
    }
}
