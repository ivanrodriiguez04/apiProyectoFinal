package Api.proyectoFinalDWSDIW.dtos;

/**
 * DTO para el proceso de recuperación de contraseña.
 * Contiene el correo del usuario para solicitar un restablecimiento de contraseña.
 * 
 * @author irodhan - 06/03/2025
 */
public class RecuperarPasswordDto {
    private String emailUsuario;

    // Getters & Setters
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
}
