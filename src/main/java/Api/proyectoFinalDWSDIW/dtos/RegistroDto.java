package Api.proyectoFinalDWSDIW.dtos;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;

/**
 * DTO para el proceso de registro de un usuario.
 * Contiene los datos necesarios para crear un nuevo usuario.
 * 
 * @author irodhan - 06/03/2025
 */
public class RegistroDto {

    private long idUsuario;
    private String nombreCompletoUsuario;
    private String telefonoUsuario;
    private String rolUsuario;
    private String emailUsuario;
    private String passwordUsuario;
    private String dniUsuario;
    private byte[] fotoDniFrontalUsuario;
    private byte[] fotoDniTraseroUsuario;
    private byte[] fotoUsuario;
    private boolean confirmado;
    private String token;

    /**
     * Constructor con los atributos principales para el registro de usuario.
     */
    public RegistroDto(long idUsuario, String nombreCompletoUsuario, String telefonoUsuario, String emailUsuario,
                       String passwordUsuario, String dniUsuario, byte[] fotoDniFrontalUsuario, 
                       byte[] fotoDniTraseroUsuario, byte[] fotoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreCompletoUsuario = nombreCompletoUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.rolUsuario = "usuario"; // Valor por defecto
        this.emailUsuario = emailUsuario;
        this.passwordUsuario = passwordUsuario;
        this.dniUsuario = dniUsuario;
        this.fotoDniFrontalUsuario = fotoDniFrontalUsuario;
        this.fotoDniTraseroUsuario = fotoDniTraseroUsuario;
        this.fotoUsuario = fotoUsuario;
        this.confirmado = false; // Valor por defecto
    }

    /**
     * Convierte el DTO en la entidad UsuarioDao.
     * 
     * @return Instancia de UsuarioDao
     */
    public UsuarioDao toEntity() {
        UsuarioDao usuario = new UsuarioDao();
        usuario.setNombreCompletoUsuario(this.nombreCompletoUsuario);
        usuario.setTelefonoUsuario(this.telefonoUsuario);
        usuario.setEmailUsuario(this.emailUsuario);
        usuario.setPasswordUsuario(this.passwordUsuario);
        usuario.setDniUsuario(this.dniUsuario);
        usuario.setFotoDniFrontalUsuario(this.fotoDniFrontalUsuario);
        usuario.setFotoDniTraseroUsuario(this.fotoDniTraseroUsuario);
        usuario.setFotoUsuario(this.fotoUsuario);
        usuario.setConfirmado(false); // Inicialmente no confirmado
        return usuario;
    }
    //Getters & Setters

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}

	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}

	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getPasswordUsuario() {
		return passwordUsuario;
	}

	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}

	public String getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	public byte[] getFotoDniFrontalUsuario() {
		return fotoDniFrontalUsuario;
	}

	public void setFotoDniFrontalUsuario(byte[] fotoDniFrontalUsuario) {
		this.fotoDniFrontalUsuario = fotoDniFrontalUsuario;
	}

	public byte[] getFotoDniTraseroUsuario() {
		return fotoDniTraseroUsuario;
	}

	public void setFotoDniTraseroUsuario(byte[] fotoDniTraseroUsuario) {
		this.fotoDniTraseroUsuario = fotoDniTraseroUsuario;
	}

	public byte[] getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(byte[] fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}