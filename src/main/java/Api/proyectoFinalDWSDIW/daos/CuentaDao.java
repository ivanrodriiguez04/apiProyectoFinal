package Api.proyectoFinalDWSDIW.daos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * Entidad que representa una cuenta en la base de datos.
 * Contiene información sobre la cuenta bancaria de un usuario.
 * 
 * @author irodhan - 06/03/2025
 */
@Entity
@Table(name = "cuenta", schema = "logica_proyecto_final")
public class CuentaDao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta", updatable = false)
    private long idCuenta;

    @Column(name = "nombre_cuenta", nullable = false)
    private String nombreCuenta;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "iban_cuenta", nullable = false, unique = true)
    private String ibanCuenta;

    @Column(name = "dinero_cuenta", nullable = false)
    private Double dineroCuenta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties("cuentas")
    private UsuarioDao usuario;

    @Transient
    @JsonProperty("idUsuario") // Asegura que se mapea desde JSON
    private Long idUsuario;

    /**
     * Obtiene el ID del usuario asociado a la cuenta.
     * 
     * @return ID del usuario
     */
    public Long getIdUsuario() {
        return (usuario != null) ? usuario.getIdUsuario() : idUsuario;
    }

    /**
     * Establece el ID del usuario y lo inicializa si es necesario.
     * 
     * @param idUsuario ID del usuario
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        if (this.usuario == null) {
            this.usuario = new UsuarioDao();
        }
        this.usuario.setIdUsuario(idUsuario);
    }

    // Getters & Setters
    public long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(long idCuenta) { this.idCuenta = idCuenta; }

    public String getNombreCuenta() { return nombreCuenta; }
    public void setNombreCuenta(String nombreCuenta) { this.nombreCuenta = nombreCuenta; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getIbanCuenta() { return ibanCuenta; }
    public void setIbanCuenta(String ibanCuenta) { this.ibanCuenta = ibanCuenta; }

    public Double getDineroCuenta() { return dineroCuenta; }
    public void setDineroCuenta(Double dineroCuenta) { this.dineroCuenta = dineroCuenta; }

    public UsuarioDao getUsuario() { return usuario; }
    public void setUsuario(UsuarioDao usuario) { this.usuario = usuario; }
}