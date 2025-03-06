package Api.proyectoFinalDWSDIW.dtos;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;

/**
 * DTO para representar una Cuenta.
 * Contiene información resumida de la cuenta bancaria.
 * 
 * @author irodhan - 06/03/2025
 */
public class CuentaDto {
    private Long idCuenta;
    private String nombreCuenta;
    private String tipoCuenta;
    private String ibanCuenta;
    private Double dineroCuenta;
    private Long idUsuario; // Solo el ID del usuario

    /**
     * Constructor basado en la entidad CuentaDao.
     * 
     * @param cuenta Entidad CuentaDao
     */
    public CuentaDto(CuentaDao cuenta) {
        this.idCuenta = cuenta.getIdCuenta();
        this.nombreCuenta = cuenta.getNombreCuenta();
        this.tipoCuenta = cuenta.getTipoCuenta();
        this.ibanCuenta = cuenta.getIbanCuenta();
        this.dineroCuenta = cuenta.getDineroCuenta();
        this.idUsuario = cuenta.getUsuario().getIdUsuario(); // Extrae solo el ID
    }
}