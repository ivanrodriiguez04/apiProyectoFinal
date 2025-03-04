package Api.proyectoFinalDWSDIW.dtos;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;

public class CuentaDto {
	private Long idCuenta;
    private String nombreCuenta;
    private String tipoCuenta;
    private String ibanCuenta;
    private Double dineroCuenta;
    private Long idUsuario; // Solo el ID del usuario
    
    public CuentaDto(CuentaDao cuenta) {
        this.idCuenta = cuenta.getIdCuenta();
        this.nombreCuenta = cuenta.getNombreCuenta();
        this.tipoCuenta = cuenta.getTipoCuenta();
        this.ibanCuenta = cuenta.getIbanCuenta();
        this.dineroCuenta = cuenta.getDineroCuenta();
        this.idUsuario = cuenta.getUsuario().getIdUsuario(); // Extrae solo el ID
    }
}
