package bd2.Muber.DTO;

import java.util.Date;

public class PasajeroDTO extends UsuarioDTO {
	private float creditos;
	private int idPasajero;

	public PasajeroDTO(){
		super();
	}
	
	public PasajeroDTO(String nombre, String password, Date fecha, int creditos){
		super(nombre, password, fecha);
		this.setCreditos(creditos);
	}
	
	public float getCreditos() {
		return creditos;
	}

	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}
	
	public int getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(int idPasajero) {
		this.idPasajero = idPasajero;
	}
	
	public void descontarCredito(float cantidad){
		this.creditos = this.creditos - cantidad;
	}
	
	public boolean isPasajero() {
		return true;
	}

	public boolean isConductor() {
		return false;
	}

}
