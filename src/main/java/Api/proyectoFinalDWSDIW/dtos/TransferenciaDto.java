package Api.proyectoFinalDWSDIW.dtos;

import java.time.LocalDateTime;

public class TransferenciaDto {
    private String ibanOrigen;
    private String ibanDestino;
    private double cantidadTransferencia;
    private Long idUsuario; // usuario que realiza la transferencia
    // Getters y Setters
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
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
    
}
