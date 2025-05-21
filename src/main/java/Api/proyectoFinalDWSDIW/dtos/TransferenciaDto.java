package Api.proyectoFinalDWSDIW.dtos;

import java.time.LocalDateTime;

public class TransferenciaDto {
    private Long idTransferencia;
    private String ibanOrigen;
    private String ibanDestino;
    private double cantidadTransferencia;
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
