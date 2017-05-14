package bd2.Muber.model;

import java.util.HashSet;
import java.util.Set;

import bd2.Muber.model.Usuario;
import bd2.Muber.model.Viaje;

public class Muber {

	private int idMuber;
	private Set<Usuario> usuarios;
	private Set<Viaje> viajes;

	public Muber(){
		usuarios = new HashSet<Usuario>();
		viajes = new HashSet<Viaje>();
	}
	
	public int getIdMuber() {
		return idMuber;
	}

	public void setIdMuber(int idMuber) {
		this.idMuber = idMuber;
	}
	
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void addUser(Usuario usuario){
		this.usuarios.add(usuario);
	}
	
	public Set<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(Set<Viaje> viajes) {
		this.viajes = viajes;
	}
	
	public void addViaje(Viaje viaje){
		this.viajes.add(viaje);
	}
	
	public void listarPasajeros() {
		Set<Usuario> usuarios = this.getUsuarios();
		if (!usuarios.isEmpty()) {
			System.out.format("%-5s%-14s%-8s%n", "ID", "Nombre", "Creditos");
			for( Usuario user : usuarios ) {
				if (user.isPasajero()) {
					Pasajero pasajero = (Pasajero) user;
					System.out.format("%-5s%-14s%-8s%n", pasajero.getIdUsuario(), pasajero.getNombre(), pasajero.getCreditos());
				}
			}
		}
		else System.out.println("No hay pasajeros registrados");
	}
	
	public Set<Pasajero> obtenerPasajeros() {
		Set<Usuario> usuarios = this.getUsuarios();
		Set<Pasajero> pasajeros = new HashSet<Pasajero>();
		if (!usuarios.isEmpty()) {
			for( Usuario user : usuarios ) {
				if (user.isPasajero()) {
					Pasajero p = (Pasajero) user;
					pasajeros.add(p);
				}
			}
		}
		return pasajeros;
	}
	
	public void listarConductores() {
		Set<Usuario> usuarios = this.getUsuarios();
		if (!usuarios.isEmpty()) {
			System.out.format("%-5s%-14s%-10s%n", "ID", "Nombre", "Venc. licencia");
			for( Usuario user : usuarios ) {
				if (user.isConductor()) {
					Conductor conductor = (Conductor) user;
					System.out.format("%-5d%-14s%-10tD%n", conductor.getIdUsuario(), conductor.getNombre(), conductor.getFechaVencimientoLic());
				}
			}
		}
		else System.out.println("No hay conductores registrados");
	}
	
	public Set<Conductor> obtenerConductores() {
		Set<Usuario> usuarios = this.getUsuarios();
		Set<Conductor> conductores = new HashSet<Conductor>();
		if (!usuarios.isEmpty()) {
			for( Usuario user : usuarios ) {
				if (user.isConductor()) {
					Conductor c = (Conductor) user;
					conductores.add(c);
				}
			}
		}
		return conductores;
	}
	
	
	
	public void listarViajesAbiertos() {
		Set<Viaje> viajes = this.getViajes();
		int abiertos = 0;
		System.out.format("%-5s%-15s%-15s%-10s%n", "ID", "Origen", "Destino", "Fecha");
		for( Viaje viaje : viajes ) {
			if (viaje.isAbierto()) {
				abiertos++;
				System.out.format("%-5s%-15s%-15s%-10tc%n", viaje.getIdViaje(), viaje.getOrigen(), viaje.getDestino(), viaje.getFecha());
			}
		}
		if (abiertos == 0) System.out.println("No hay viajes abiertos");
	}
	
	public Set<Viaje> obtenerViajesAbiertos() {
		Set<Viaje> viajes = new HashSet<Viaje>();
		for( Viaje viaje : this.getViajes() ) {
			if (viaje.isAbierto()) {
				viajes.add(viaje);
			}
		}
		return(viajes);
	}
	
	private void printViajes(Conductor conductor) {
		Set<Viaje> viajes = conductor.getViajes();
		if (!viajes.isEmpty()) {
			System.out.println("-----VIAJES-----");
			System.out.format("%-5s%-15s%-15s%-10s%n", "ID", "Origen", "Destino", "Fecha");
			for( Viaje viaje : viajes ) {
				System.out.format("%-5s%-15s%-15s%-10tc%n", viaje.getIdViaje(), viaje.getOrigen(), viaje.getDestino(), viaje.getFecha());
			}
		}
		else System.out.println("El conductor no posee viajes registrados");
	}
	
	public void getInfoConductor(int idConductor) {
		boolean ok = false;
		for( Usuario usuario : this.getUsuarios() ) {
			if ((usuario.getIdUsuario() == idConductor) && (usuario.isConductor())) {
				ok = true;
				Conductor conductor = (Conductor) usuario;
				System.out.format("%-14s%-13s%-10s%n", "Nombre", "Punt. prom.", "Venc. Licencia");
				System.out.format("%-14s%-13s%-10s%n", conductor.getNombre(), conductor.puntajePromedio(), conductor.getFechaVencimientoLic());
				this.printViajes(conductor);
				break;
			}
		}
		if (!ok) System.out.println("El conductor no existe");
	}
	
	public Conductor obtenerInfoConductor(int idConductor) {
		Conductor conductor = null;
		for( Usuario usuario : this.getUsuarios() ) {
			if ((usuario.getIdUsuario() == idConductor) && (usuario.isConductor())) {
				conductor = (Conductor) usuario;
				for (Viaje v : conductor.getViajes());
				break;
			}
		}
		return conductor;
	}
	
}
