package bd2.Muber.DTO;
import java.util.Date;

import bd2.Muber.model.Conductor;

public class ConductorDTO extends UsuarioDTO  {
	
	private int idConductor;
	private Date fechaVencimientoLic;
	private float puntajePromedio;

	public ConductorDTO(){
		super();
	}
	
	public ConductorDTO(String nombre, String password, Date fechaIngreso, Date fechaVencimiento){
		super(nombre, password, fechaIngreso);
		this.setFechaVencimientoLic(fechaVencimiento);
	}

	public ConductorDTO(Conductor c) {
		this.setFechaIngreso(c.getFechaIngreso());
		this.setFechaVencimientoLic(c.getFechaVencimientoLic());
		this.setIdUsuario(c.getIdUsuario());
		this.setNombre(c.getNombre());
		this.setPassword(c.getPassword());
	}

	public Date getFechaVencimientoLic() {
		return fechaVencimientoLic;
	}

	public void setFechaVencimientoLic(Date fechaVencimientoLic) {
		this.fechaVencimientoLic = fechaVencimientoLic;
	}
	
	public int getIdConductor() {
		return idConductor;
	}

	public void setIdConductor(int idConductor) {
		this.idConductor = idConductor;
	}
	
	public boolean isPasajero() {
		return false;
	}

	public boolean isConductor() {
		return true;
	}
	
	public float getPuntajePromedio() {
		return puntajePromedio;
	}

	public void setPuntajePromedio(float puntajePromedio) {
		this.puntajePromedio = puntajePromedio;
	}

}

