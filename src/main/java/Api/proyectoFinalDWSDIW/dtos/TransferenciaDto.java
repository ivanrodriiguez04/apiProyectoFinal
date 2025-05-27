package Api.proyectoFinalDWSDIW.dtos;

public class TransferenciaDto {
    private String ibanOrigen;
    private String ibanDestino;
    private double cantidadTransferencia;
    private String emailUsuario; // ✅ reemplaza idUsuario

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

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
