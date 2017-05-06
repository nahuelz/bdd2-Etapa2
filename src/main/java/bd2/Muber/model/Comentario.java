package bd2.Muber.model;

public class Comentario {
	
	private int idComentario;
	private String comentario;
	private int calificacion;
	private Pasajero pasajero;
	
	public Comentario (){
		
	}
	
	public Comentario(int calificacion, String comentario, Pasajero pasajero) {
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.pasajero = pasajero;
	}
	
	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

	/*public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}*/
	
}
