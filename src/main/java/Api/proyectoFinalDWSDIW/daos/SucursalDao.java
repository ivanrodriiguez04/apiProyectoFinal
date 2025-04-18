package Api.proyectoFinalDWSDIW.daos;

import jakarta.persistence.*;

/**
 * Entidad que representa una sucursal bancaria en la base de datos.
 * 
 * Contiene el ID de la sucursal, el código del banco, el código de la sucursal,
 * la ciudad donde se encuentra y su dirección.
 * 
 * @author irodhan - 24/03/2025
 */
@Entity
@Table(name = "sucursal", schema = "logica_proyecto_final")
public class SucursalDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @Column(name = "codigo_banco", nullable = false)
    private String codigoBanco; // Código fijo del banco

    @Column(name = "codigo_sucursal", nullable = false, unique = true)
    private String codigoSucursal;

    @Column(name = "ciudad_sucursal", nullable = false)
    private String ciudadSucursal;

    @Column(name = "direccion_sucursal", nullable = false)
    private String direccionSucursal;

    // Getters & Setters
    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getCiudadSucursal() {
        return ciudadSucursal;
    }

    public void setCiudadSucursal(String ciudadSucursal) {
        this.ciudadSucursal = ciudadSucursal;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }
}
