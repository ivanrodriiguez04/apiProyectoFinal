package Api.proyectoFinalDWSDIW.dtos;

/**
 * DTO para el proceso de inicio de sesión.
 * Contiene la información del usuario para autenticarse.
 * 
 * @author irodhan - 06/03/2025
 */
public class LoginDto {

    private Long idUsuario;
    private String emailUsuario;
    private String passwordUsuario;
    private String rolUsuario;

    // Getters & Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
    
    public String getPasswordUsuario() { return passwordUsuario; }
    public void setPasswordUsuario(String passwordUsuario) { this.passwordUsuario = passwordUsuario; }
    
    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }
}