package bd2.Muber.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bd2.Muber.DTO.ConductorDTO;
import bd2.Muber.DTO.ViajeDTO;
import bd2.Muber.model.Comentario;
import bd2.Muber.model.Conductor;
import bd2.Muber.model.Muber;
import bd2.Muber.model.Pasajero;
import bd2.Muber.model.Viaje;

public class ViajeDAO {
	
	protected Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}
	
	@SuppressWarnings("null")
	public List<ViajeDTO> obtenerViajes(){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Viaje> viajes = session.createQuery("from Viaje").list();
		List<ViajeDTO> viajesDTO = new ArrayList<ViajeDTO>();
		for (Viaje v : viajes) {
			ViajeDTO via = new ViajeDTO(v);
			viajesDTO.add(via);
		}
		tx.rollback();
		session.disconnect();
		session.close();
		return viajesDTO;
	}
	
	public String crearViaje (String origen, String destino, Integer conductorId, Integer costoTotal, Integer cantidadPasajeros){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Muber muber = (Muber) session.createQuery("from Muber").uniqueResult();
		Conductor conductor = (Conductor) session.get(Conductor.class, conductorId);
		String resultado;
		if (conductor != null){
			if (conductor.getFechaVencimientoLic().after(new Date())) {
				Viaje viaje = new Viaje (origen, destino, costoTotal, cantidadPasajeros, new Date(), conductor);
				muber.addViaje(viaje);
				resultado = "Viaje creado";
			}else{
				resultado = "Error, el conductor posee la licencia vencida";
			}
		}else{
			resultado = "No se encontro conductor con el id ingresado" + conductorId;
		}
		
		tx.commit();
		session.disconnect();
		session.close();
		return resultado;
		
	}
	
	public String calificarViaje(Integer viajeId, Integer pasajeroId, Integer puntaje, String comentario){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Viaje viaje = (Viaje) session.get(Viaje.class, viajeId);
		Pasajero pasajero = (Pasajero) session.get(Pasajero.class, pasajeroId);
		String resultado;
		if ((viaje != null) && (!viaje.isAbierto()) ) {
			if (pasajero != null){
				if (this.fuePasajero(pasajero, viaje)){
					if (!this.calificoViaje(pasajero, viaje)){
						Comentario c = new Comentario (puntaje, comentario, pasajero);
						viaje.addComentario(c);
						tx.commit();
						resultado = "Viaje calificado.";
					}else{
						resultado = "El pasajero ya ah calificado este viaje";
					}
				}else{
					resultado = "El pasajero no fue pasajero (valga la redundancia) del viaje ingresado";
				}
			}else{
				resultado = "El pasajero ingresado no existe";
			}
		}else{
			resultado = "No se encontro viaje finalizado con el id ingresado";
		}
		session.disconnect();
		session.close();
		return resultado;
	}
	
	private boolean calificoViaje(Pasajero pasajero, Viaje viaje){
		Set<Comentario> comentarios = viaje.getComentarios();
		for (Comentario c : comentarios) {
			if (c.getPasajero().equals(pasajero)){
				return true;
			}
		}
		return false;
	}
	
	private boolean fuePasajero(Pasajero pasajero, Viaje viaje){
		 Set<Pasajero> pasajeros = viaje.getPasajeros();
		 for (Pasajero p : pasajeros) {
				if ( p.equals(pasajero)){
					return true;
				}
		 }
		 return false;
	}
	
	public boolean finalizarViaje(Integer viajeId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Viaje viaje = (Viaje) session.get(Viaje.class, viajeId);
		boolean resultado = false;
		if ( (viaje != null) && (!viaje.isFinalizado())){
			viaje.finalizar();
			tx.commit();
			resultado = true;
		}
		session.disconnect();
		session.close();
		return resultado;
	}	

}
