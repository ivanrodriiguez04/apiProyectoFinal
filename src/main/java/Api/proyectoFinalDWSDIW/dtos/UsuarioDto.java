package Api.proyectoFinalDWSDIW.dtos;

/**
 * DTO que representa la información básica de un usuario.
 * 
 * @author irodhan - 06/03/2025
 */
public class UsuarioDto {
    private Long idUsuario;
    private String nombreCompletoUsuario;
    private String telefonoUsuario;
    private String emailUsuario;

    // Getters & Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreCompletoUsuario() { return nombreCompletoUsuario; }
    public void setNombreCompletoUsuario(String nombreCompletoUsuario) { this.nombreCompletoUsuario = nombreCompletoUsuario; }

    public String getTelefonoUsuario() { return telefonoUsuario; }
    public void setTelefonoUsuario(String telefonoUsuario) { this.telefonoUsuario = telefonoUsuario; }

    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
}
