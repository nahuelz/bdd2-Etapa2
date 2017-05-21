package bd2.Muber.DTO;

import java.util.Date;

import bd2.Muber.model.Pasajero;

public class PasajeroDTO extends UsuarioDTO {
	private double creditos;
	private int idPasajero;

	public PasajeroDTO(){
		super();
	}
	
	public PasajeroDTO(String nombre, String password, Date fecha, int creditos){
		super(nombre, password, fecha);
		this.setCreditos(creditos);
	}
	
	public PasajeroDTO(Pasajero p) {
		this.setCreditos(p.getCreditos());
		this.setFechaIngreso(p.getFechaIngreso());
		this.setIdUsuario(p.getIdUsuario());
		this.setNombre(p.getNombre());
		this.setPassword(p.getPassword());
	}

	public double getCreditos() {
		return creditos;
	}

	public void setCreditos(double creditos) {
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
