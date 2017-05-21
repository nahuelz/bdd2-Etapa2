package bd2.Muber.model;

import java.util.Date;

public class Pasajero extends Usuario {

	private double creditos;
	private int idPasajero;

	public Pasajero(){
		super();
	}
	
	public Pasajero (String nombre, String password, Date fecha, int creditos){
		super(nombre, password, fecha);
		this.setCreditos(creditos);
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
	
	public void descontarCredito(Double cantidad){
		this.creditos = this.creditos - cantidad;
	}
	
	public void cargarCredito(Double cantidad) {
		this.creditos = this.creditos + cantidad;
		
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
