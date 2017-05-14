package bd2.web;


import java.util.Date;
import java.util.HashMap;
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
import org.springframework.util.comparator.ComparableComparator;
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
	
	@RequestMapping(value = "/pasajeros", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String pasajeros() {
		/* 
		 * curl http://localhost:8080/MuberRESTful/rest/services/pasajeros
		 */

		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapPasajeros = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		List <Pasajero> pasajeros = obtenerPasajeros();
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
		 * curl http://localhost:8080/MuberRESTful/rest/services/conductores
		 */
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapConductores = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		List <Conductor> conductores = obtenerConductores();
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
		 * curl http://localhost:8080/MuberRESTful/rest/services/abiertos
		 */
		
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapViajes = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		List <Viaje> viajes = obtenerViajes();
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
		 * curl -X POST -d "origen=Mar del Plata&destino=Capital Federal&conductorId=2&costoTotal=1000&cantidadPasajeros=6" "http://localhost:8080/MuberRESTful/rest/services/viajes/nuevo"
		 */
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (origen != null) & (destino != null) & (conductorId != null) & (costoTotal != null) & (cantidadPasajeros != null) ){
			String resultado = this.crearViaje(origen, destino, conductorId, costoTotal, cantidadPasajeros);
			aMap.put("result", resultado);
		}else{ 
			aMap.put("result", "Error, parametros incorrectos");
		}
		return new Gson().toJson(aMap);
	}
	
	
	@RequestMapping(value = "/conductores/detalle", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String detalle(Integer conductorId) {
		/*
		 * curl http://localhost:8080/MuberRESTful/rest/services/conductores/detalle?conductorId=2
		 */
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapViajes = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapConductor = new LinkedHashMap<String, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		if (conductorId != null){
			Conductor conductor = obtenerConductor(conductorId);
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
		 * curl -X PUT "http://localhost:8080/MuberRESTful/rest/services/viajes/agregarPasajero?viajeId=1&pasajeroId=1"
		 */
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (viajeId != null) & (pasajeroId != null) ){
			String resultado = addPasajero(viajeId, pasajeroId);
			aMap.put("Result", resultado);
		}else{
			aMap.put("result", "Error, parametros incorrectos");
		}
		return new Gson().toJson(aMap);
	}
	
	
	@RequestMapping(value = "/viajes/calificar", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public String calificar(Integer viajeId, Integer pasajeroId, Integer puntaje, String comentario) {	
		/*
		 * curl -X POST -d "viajeId=1&pasajeroId=1&puntaje=4&comentario=Conductor agradable" "http://localhost:8080/MuberRESTful/rest/services/viajes/calificar"
		 */
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (viajeId != null) & (pasajeroId != null) & (puntaje != null) & (comentario != null)){
			String resultado = this.calificarViaje(viajeId, pasajeroId, puntaje, comentario);
			aMap.put("result", resultado);
		}else 
			aMap.put("result", "Error, parametros incorrectos");
		return new Gson().toJson(aMap);
	}
	
	@RequestMapping(value = "/conductores/top10", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String top10() {
		
		/*
		 * curl http://localhost:8080/MuberRESTful/rest/services/conductores/top10
		 */
		
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapConductores = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Conductor> conductores = session.createQuery("from Conductor c where c not in (select v.Conductor from Viaje v where v.estado = 'A')").list();
		tx.rollback();
		conductores.sort((c1, c2) -> c2.puntajePromedio().compareTo(c1.puntajePromedio()));
		conductores = conductores.subList(0, Integer.min(conductores.size(), 10));
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
		
	@RequestMapping(value = "/pasajeros/cargarCredito", method = RequestMethod.PUT, produces = "application/json", headers = "Accept=application/json")
	public String cargarCredito(Integer pasajeroId, Integer monto) {
		/*
		 * curl -X PUT "http://localhost:8080/MuberRESTful/rest/services/pasajeros/cargarCredito?pasajeroId=4&monto=100"
		 */
		Map<String, Object> aMap = new HashMap<String, Object>();
		if  ((pasajeroId != null) && (monto != null)) {
			String resultado = this.addCredito(pasajeroId, monto);
			aMap.put("result", resultado);
		}else{
			aMap.put("result", "Error parametros incorrectos");
		}
		return new Gson().toJson(aMap);
	}
	
	@RequestMapping(value = "/viajes/finalizar", method = RequestMethod.PUT, produces = "application/json", headers = "Accept=application/json")
	public String finalizar(Integer viajeId) {	
		/*
		 * curl -X PUT http://localhost:8080/MuberRESTful/rest/services/viajes/finalizar?viajeId=4
		 */
		Map<String, Object> aMap = new HashMap<String, Object>();
		if  (viajeId != null) {
			if (this.finalizarViaje(viajeId)){
				aMap.put("Result", "Viaje Fianlizado");
			}else{
				aMap.put("Result", "Error, no existe viaje abierto con el id ingresado");
			}
		}else{
			aMap.put("Result", "Error parametro incorrecto");
		}
		return new Gson().toJson(aMap);
	}

	private String addPasajero(Integer viajeId, Integer pasajeroId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Viaje viaje = (Viaje) session.get(Viaje.class, viajeId);
		Pasajero pasajero = (Pasajero) session.get(Pasajero.class, pasajeroId);
		String resultado;
		if (viaje != null){
			if (pasajero != null){
				if (viaje.addPasajero(pasajero)){
					tx.commit();
					resultado = "pasajero agregado";
				}else{
					resultado = "No se pudo agregar el pasajero";
				}
			}else{
				resultado = "No se encontro pasajero con el id ingresado";
			}
		}else{
			resultado = "No se encontro viaje con el id ingresado";			
		}
		session.disconnect();
		session.close();
		return resultado;
	}
	private String addCredito(Integer pasajeroId, Integer monto){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Pasajero pasajero = (Pasajero) session.get(Pasajero.class, pasajeroId);
		String resultado;
		if (pasajero != null){
			resultado = "Credito agregado";
			pasajero.cargarCredito(monto);
			tx.commit();
		}else{
			resultado = "El ID ingresado no corresponde a un pasajero";
		}
		session.disconnect();
		session.close();
		return resultado;
	}
	
	private boolean finalizarViaje(Integer viajeId){
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
	
	private String calificarViaje(Integer viajeId, Integer pasajeroId, Integer puntaje, String comentario){
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
	
	private Pasajero obtenerPasajero(Integer pasajeroId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Pasajero pasajero = (Pasajero) session.get(Pasajero.class, pasajeroId);
		tx.rollback();
		session.disconnect();
		session.close();
		return pasajero;
	}
	
	private Conductor obtenerConductor(Integer conductorId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Conductor conductor = (Conductor) session.get(Conductor.class, conductorId);
		for (Viaje v : conductor.getViajes()){}
		tx.rollback();
		session.disconnect();
		session.close();
		return conductor;
	}
	
	private Viaje obtenerViaje(Integer viajeId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Viaje viaje = (Viaje) session.get(Viaje.class, viajeId);
		tx.rollback();
		session.disconnect();
		session.close();
		return viaje;
	}
	
	private List<Pasajero> obtenerPasajeros(){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Pasajero> pasajeros = session.createQuery("from Pasajero").list();
		tx.rollback();
		session.disconnect();
		session.close();
		return pasajeros;
	}
	
	private List<Conductor> obtenerConductores(){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Conductor> conductores = session.createQuery("from Conductor").list();
		tx.rollback();
		session.disconnect();
		session.close();
		return conductores;
	}
	
	private List<Viaje> obtenerViajes(){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Viaje> viaje = session.createQuery("from Viaje").list();
		tx.rollback();
		session.disconnect();
		session.close();
		return viaje;
	}
	
	private String crearViaje (String origen, String destino, Integer costoTotal, Integer cantidadPasajeros, Integer conductorId){
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
			resultado = "No se encontro conductor con el id ingresado";
		}
		
		tx.commit();
		session.disconnect();
		session.close();
		return resultado;
		
	}
	
	
	
}
