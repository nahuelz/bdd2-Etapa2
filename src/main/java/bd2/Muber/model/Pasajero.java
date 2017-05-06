package bd2.Muber.model;

import java.util.Date;

public class Pasajero extends Usuario {

	private float creditos;
	private int idPasajero;

	public Pasajero(){
		super();
	}
	
	public Pasajero (String nombre, String password, Date fecha, int creditos){
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
	
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + super.getIdUsuario() + ", nombre=" + super.getNombre() + ", password=" + super.getPassword() + ", fechaIngreso="
				+ super.getFechaIngreso() + ", viajes=" + super.getViajes() + "]";
	}
	
}
