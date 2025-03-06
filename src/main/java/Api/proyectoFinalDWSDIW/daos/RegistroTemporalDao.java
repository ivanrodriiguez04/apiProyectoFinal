package Api.proyectoFinalDWSDIW.daos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un registro temporal en la base de datos.
 * Se usa para almacenar tokens de verificación de cuenta.
 * 
 * @author irodhan - 06/03/2025
 */
@Entity
@Table(name = "registro_temporal", schema = "logica_proyecto_final")
public class RegistroTemporalDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER) // 🚀 IMPORTANTE: Asegura que siempre cargue el usuario
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private UsuarioDao usuario;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    /**
     * Verifica si el token ha expirado.
     * 
     * @return true si ha expirado, false en caso contrario
     */
    public boolean estaExpirado() {
        return fechaExpiracion.isBefore(LocalDateTime.now());
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsuarioDao getUsuario() { return usuario; }
    public void setUsuario(UsuarioDao usuario) { this.usuario = usuario; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}
