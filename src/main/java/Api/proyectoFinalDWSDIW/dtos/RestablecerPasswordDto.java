package Api.proyectoFinalDWSDIW.dtos;

/**
 * DTO para el proceso de restablecimiento de contraseña.
 * Contiene la información necesaria para establecer una nueva contraseña.
 * 
 * @author irodhan - 06/03/2025
 */
public class RestablecerPasswordDto {
    private String token;
    private String nuevaPassword;

    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getNuevaPassword() { return nuevaPassword; }
    public void setNuevaPassword(String nuevaPassword) { this.nuevaPassword = nuevaPassword; }
}
