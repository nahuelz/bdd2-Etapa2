package db2.Muber.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import db2.Muber.DTO.ConductorDTO;


public class ViajeDTO {
	
	private int idViaje;
	private String origen;
	private String destino;
	private int costoTotal;
	private Date fecha;
	private int cantidadMaximaPasajeros;
	private ConductorDTO conductor;
	private Set<PasajeroDTO> pasajeros;
	private Set<ComentarioDTO> comentarios;
	private char estado;

	public ViajeDTO() {
		this.pasajeros = new HashSet<PasajeroDTO>();
		this.comentarios = new HashSet<ComentarioDTO>();
		this.estado = 'A';
	}
	
	public ViajeDTO(String origen, String destino, int costoTotal, int pasajeros, Date fecha, ConductorDTO conductor){
		this();
		if (conductor.getFechaVencimientoLic().after(fecha)) {
			this.setOrigen(origen);
			this.setDestino(destino);
			this.setCantidadMaximaPasajeros(pasajeros);
			this.setFecha(fecha);
			this.setCostoTotal(costoTotal);
			this.setConductor(conductor);
		}
	}
	
	public int getIdViaje() {
		return idViaje;
	}
	public void setIdViaje(int idViaje) {
		this.idViaje = idViaje;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public int getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(int costoTotal) {
		this.costoTotal = costoTotal;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getCantidadMaximaPasajeros() {
		return cantidadMaximaPasajeros;
	}
	public void setCantidadMaximaPasajeros(int cantidadMaximaPasajeros) {
		this.cantidadMaximaPasajeros = cantidadMaximaPasajeros;
	}
	
	public ConductorDTO getConductor() {
		return conductor;
	}

	public void setConductor(ConductorDTO conductor) {
		this.conductor = conductor;
	}
	
	public Set<PasajeroDTO> getPasajeros() {
		return pasajeros;
	}
 
	public void setPasajeros(Set<PasajeroDTO> pasajeros) {
		this.pasajeros = pasajeros;
	}
	
	public Set<ComentarioDTO> getComentarios() {
		return comentarios;
	}
 
	public void setComentarios(Set<ComentarioDTO> comentarios) {
		this.comentarios = comentarios;
	}

	
	public char getEstado() {
		return estado;
	}
	
	private void setEstado(char estado) {
		this.estado = estado;
	}
	
	public float costoPorPasajero() {
		return (this.costoTotal / this.pasajeros.size());
	}
	
	public void cerrar() {
		this.setEstado('C');
	}
	
	public boolean isAbierto() {
		return estado == 'A';
	}
	
	public boolean isCerrado() {
		return estado == 'C';
	}
	
	public boolean isFinalizado() {
		return estado == 'F';
	}
	
	public void addComentario(ComentarioDTO comentario) {
		this.comentarios.add(comentario);
	}

}
