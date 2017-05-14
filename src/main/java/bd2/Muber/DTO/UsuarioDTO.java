package bd2.Muber.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import bd2.Muber.model.Viaje;

public abstract class UsuarioDTO {
	private int idUsuario;
	private String nombre;
	private String password;
	private Date fechaIngreso;
	private Set<Viaje> viajes;
	
	public UsuarioDTO (String nombre, String password, Date fecha){
		this();
		this.setNombre(nombre);
		this.setPassword(password);
		this.setFechaIngreso(fecha);
	}
	
	public UsuarioDTO() {
		this.setViajes(new HashSet<Viaje>());
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	public Set<Viaje> getViajes() {
		return this.viajes;
	}
	
	private void setViajes(Set<Viaje> viajes) {
		this.viajes = viajes;
	}
	
	public void addViaje(Viaje viaje) {
		this.viajes.add(viaje);
	}
	
	public abstract boolean isPasajero();

	public abstract boolean isConductor();

}
