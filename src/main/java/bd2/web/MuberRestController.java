package bd2.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import bd2.Muber.model.*;

import com.google.gson.Gson;

@Controller
@ControllerAdvice
@RequestMapping("/services")
@ResponseBody
@EnableWebMvc
public class MuberRestController {

	protected Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}

	@RequestMapping(value = "/example", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String example() {
		Map<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("result", "OK");
		return new Gson().toJson(aMap);
	}
	
	@RequestMapping(value = "/pasajeros", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String pasajeros() {
		
		/*
		 * URL
		 * http://localhost:8080/MuberRESTful/rest/services/pasajeros
		 */
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapPasajeros = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Pasajero> pasajeros = session.createQuery("from Pasajero").list();
		tx.rollback();
		for (Pasajero p : pasajeros) {
			mapAtributos.put("nombre", p.getNombre());
			mapAtributos.put("password", p.getPassword());
			mapAtributos.put("creditos", p.getCreditos());
			mapAtributos.put("fechaIngreso", p.getFechaIngreso());
			mapPasajeros.put(p.getIdUsuario(), new LinkedHashMap<String, Object>(mapAtributos));
		}
		mapAll.put("result", "OK");
		mapAll.put("pasajeros", mapPasajeros);
		return new Gson().toJson(mapAll);
		
	}
	
	@RequestMapping(value = "/conductores", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String conductores() {
		
		/*
		 * URL
		 * http://localhost:8080/MuberRESTful/rest/services/conductores
		 */
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapConductores = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Conductor> conductores = session.createQuery("from Conductor").list();
		tx.rollback();
		for (Conductor c : conductores) {
			mapAtributos.put("nombre", c.getNombre());
			mapAtributos.put("password", c.getPassword());
			mapAtributos.put("fechaVencimientoLic", c.getFechaVencimientoLic());
			mapAtributos.put("fechaIngreso", c.getFechaIngreso());
			mapConductores.put(c.getIdUsuario(), new LinkedHashMap<String, Object>(mapAtributos));
		}
		mapAll.put("result", "OK");
		mapAll.put("conductores", mapConductores);
		return new Gson().toJson(mapAll);
		
	}
	
	@RequestMapping(value = "/abiertos", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String abiertos() {
		/*
		 * URL:
		 * http://localhost:8080/MuberRESTful/rest/services/abiertos
		 */
		
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapViajes = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Viaje> viajes = session.createQuery("from Viaje").list();
		tx.rollback();
		for (Viaje v : viajes) {
			if (v.isAbierto()){
				mapAtributos.put("origen", v.getOrigen());
				mapAtributos.put("destino", v.getDestino());
				mapAtributos.put("costoTotal", v.getCostoTotal());
				mapAtributos.put("fecha", v.getFecha());
				mapAtributos.put("cantidadMaximaPasajeros", v.getCantidadMaximaPasajeros());
				mapAtributos.put("idConductor", v.getConductor().getIdConductor());
				mapAtributos.put("nombreConductor", v.getConductor().getNombre());
				mapViajes.put(v.getIdViaje(), new LinkedHashMap<String, Object>(mapAtributos));
			}
		}
		mapAll.put("result", "OK");
		mapAll.put("viajesAbiertos", mapViajes);
		return new Gson().toJson(mapAll);
		
	}
	
	@RequestMapping(value = "/viajes/nuevo", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public String nuevo(String origen, String destino, Integer conductorId, Integer costoTotal, Integer cantidadPasajeros) {
		
		/*
		 * Comando curl:
		 * curl -X POST -d "origen=Mar del Plata&destino=Capital Federal&conductorId=2&costoTotal=1000&cantidadPasajeros=6" "http://localhost:8080/MuberRESTful/rest/services/viajes/nuevo"
		 */
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (origen != null) & (destino != null) & (conductorId != null) & (costoTotal != null) & (cantidadPasajeros != null) ){
			Session session = this.getSession();
			Transaction tx = session.beginTransaction();
			Muber muber = (Muber) session.createQuery("from Muber").uniqueResult();
			Conductor conductor = muber.obtenerInfoConductor(conductorId);
			if (conductor != null){
				if (conductor.getFechaVencimientoLic().after(new Date())) {
					Viaje viaje = new Viaje (origen, destino, costoTotal, cantidadPasajeros, new Date(), conductor);
					muber.addViaje(viaje);
					aMap.put("result", "OK");
				}
				else aMap.put("result", "Error, id conductor incorrecto");;
			}
			else aMap.put("result", "Error, el conductor tiene la licencia vencida");
			
			tx.commit();
		}
		else aMap.put("result", "Error, parametros incorrectos");
		return new Gson().toJson(aMap);
	}
	
	
	@RequestMapping(value = "/conductores/detalle", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String detalle(Integer conductorId) {
		
		/*
		 * URL
		 * http://localhost:8080/MuberRESTful/rest/services/conductores/detalle?conductorId=2
		 */
		
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapViajes = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapConductor = new LinkedHashMap<String, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		
		if (conductorId != null){
			Session session = this.getSession();
			Transaction tx = session.beginTransaction();
			Conductor conductor = obtenerConductor(conductorId, session);
			tx.rollback();
			if (conductor != null){
				mapConductor.put("idUsuario", conductor.getIdUsuario());
				mapConductor.put("nombre", conductor.getNombre());
				mapConductor.put("password", conductor.getPassword());
				mapConductor.put("fechaVencimientoLic", conductor.getFechaVencimientoLic());
				mapConductor.put("fechaIngreso", conductor.getFechaIngreso());
	
				for (Viaje v : conductor.getViajes()) {
					mapAtributos.put("origen", v.getOrigen());
					mapAtributos.put("destino", v.getDestino());
					mapAtributos.put("costoTotal", v.getCostoTotal());
					mapAtributos.put("fecha", v.getFecha());
					mapAtributos.put("cantidadMaximaPasajeros", v.getCantidadMaximaPasajeros());
					mapAtributos.put("estado", v.getEstado());
					mapViajes.put(v.getIdViaje(), new LinkedHashMap<String, Object>(mapAtributos));
				}
				mapConductor.put("viajes", mapViajes);
				mapAll.put("result", "OK");
				mapAll.put("conductor", mapConductor);
			}else{
				mapAll.put("result", "El ID no corresponde a un conductor");
			}
		}else{
			mapAll.put("result", "No se ingreso un parametro");
		}
		
		return new Gson().toJson(mapAll);
		
	}
	
	
	@RequestMapping(value = "/viajes/agregarPasajero", method = RequestMethod.PUT, produces = "application/json", headers = "Accept=application/json")
	public String agregarPasajero(Integer viajeId, Integer pasajeroId) {
		
		/*
		 * Comando curl:
		 * curl -X PUT "http://localhost:8080/MuberRESTful/rest/services/viajes/agregarPasajero?viajeId=1&pasajeroId=1"
		 */
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (viajeId != null) & (pasajeroId != null) ){
			Session session = this.getSession();
			Transaction tx = session.beginTransaction();
			Viaje viaje = obtenerViaje(viajeId, session);
			Pasajero pasajero = obtenerPasajero(pasajeroId, session);
			if (viaje != null){
				if (pasajero != null){
					if (viaje.isAbierto()) {
						if (viaje.getPasajeros().size() < viaje.getCantidadMaximaPasajeros()){
							viaje.addPasajero(pasajero);
							aMap.put("result", "OK");
						}
						else aMap.put("result", "Error, el viaje esta lleno");
					}
					else aMap.put("result", "Error, el viaje esta cerrado");
				}
				else aMap.put("result", "Error, el pasajero no existe ");
			}
			else aMap.put("result", "Error, el viaje no existe ");
			
			tx.commit();
		}
		else aMap.put("result", "Error, parametros incorrectos");
		
		return new Gson().toJson(aMap);
	}
	
	
	@RequestMapping(value = "/viajes/calificar", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public String calificar(Integer viajeId, Integer pasajeroId, Integer puntaje, String comentario) {
		
		/*
		 * Comando curl:
		 * curl -X POST -d "viajeId=1&pasajeroId=1&puntaje=4&comentario=Conductor agradable" "http://localhost:8080/MuberRESTful/rest/services/viajes/calificar"
		 */
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (viajeId != null) & (pasajeroId != null) & (puntaje != null) & (comentario != null)){
			Session session = this.getSession();
			Transaction tx = session.beginTransaction();
			Viaje viaje = obtenerViaje(viajeId, session);
			Pasajero pasajero = obtenerPasajero(pasajeroId, session);
			if (viaje != null){
				if (pasajero != null){
					Comentario c = new Comentario (puntaje, comentario, pasajero);
					viaje.addComentario(c);
					aMap.put("result", "OK");
				}
				else aMap.put("result", "Error, el pasajero no existe ");
			}
			else aMap.put("result", "Error, el viaje no existe ");
			
			tx.commit();
		}
		else aMap.put("result", "Error, parametros incorrectos");
		
		return new Gson().toJson(aMap);
	}
	
	private Conductor obtenerConductor (Integer conductorId, Session session){
			String hql = "FROM Conductor C WHERE C.idUsuario = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, conductorId);

			Conductor conductor = null;
			if (query.uniqueResult() != null){
				conductor = (Conductor) query.uniqueResult();
			}
		return conductor;
	}
	
	private Pasajero obtenerPasajero (Integer pasajeroId, Session session){
		String hql = "FROM Pasajero P WHERE P.idUsuario = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, pasajeroId);

		Pasajero pasajero = null;
		if (query.uniqueResult() != null){
			pasajero = (Pasajero) query.uniqueResult();
		}	
		return pasajero;
	}
	
	private Viaje obtenerViaje (Integer viajeId, Session session){
		String hql = "FROM Viaje V WHERE V.idViaje = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, viajeId);

		Viaje viaje = null;
		if (query.uniqueResult() != null){
			viaje = (Viaje) query.uniqueResult();
		}
		return viaje;
	}
}
