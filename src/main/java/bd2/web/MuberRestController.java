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

import bd2.Muber.DAO.ConductorDAO;
import bd2.Muber.DAO.PasajeroDAO;
import bd2.Muber.DAO.ViajeDAO;
import bd2.Muber.DTO.ConductorDTO;
import bd2.Muber.DTO.PasajeroDTO;
import bd2.Muber.DTO.ViajeDTO;
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
		PasajeroDAO dao = new PasajeroDAO();
		List <PasajeroDTO> pasajeros =  dao.obtenerPasajeros();
		for (PasajeroDTO p : pasajeros) {
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
		ConductorDAO dao = new ConductorDAO();
		List <ConductorDTO> conductores =  dao.obtenerConductores();
		for (ConductorDTO c : conductores) {
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
		ViajeDAO dao = new ViajeDAO();
		List <ViajeDTO> viajes =  dao.obtenerViajes();
		for (ViajeDTO v : viajes) {
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
	public String nuevo(@RequestParam("origen") String origen, @RequestParam("destino") String destino, @RequestParam("conductorId") Integer conductorId, @RequestParam("costoTotal") Integer costoTotal, @RequestParam("cantidadPasajeros") Integer cantidadPasajeros) {
		/*
		 * curl -X POST -d "origen=Mar del Plata&destino=Capital Federal&conductorId=2&costoTotal=1000&cantidadPasajeros=6" "http://localhost:8080/MuberRESTful/rest/services/viajes/nuevo"
		 */
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (origen != null) & (destino != null) & (conductorId != null) & (costoTotal != null) & (cantidadPasajeros != null) ){
			ViajeDAO dao = new ViajeDAO();
			String resultado = dao.crearViaje(origen, destino, conductorId, costoTotal, cantidadPasajeros);
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
			ConductorDAO dao = new ConductorDAO();
			ConductorDTO conductor = dao.obtenerConductor(conductorId);
			if (conductor != null){
				mapConductor.put("idUsuario", conductor.getIdUsuario());
				mapConductor.put("nombre", conductor.getNombre());
				mapConductor.put("password", conductor.getPassword());
				mapConductor.put("fechaVencimientoLic", conductor.getFechaVencimientoLic());
				mapConductor.put("fechaIngreso", conductor.getFechaIngreso());
	
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
	public String agregarPasajero(@RequestBody Map<String, Integer> params) {
		
		/*
		 * Probado con Postman {"viajeId":1,"pasajeroId":3}
		 */
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (params.get("viajeId") != null) & (params.get("pasajeroId") != null) ){
			PasajeroDAO dao = new PasajeroDAO();
			String resultado = dao.addPasajero(params.get("viajeId"), params.get("pasajeroId"));
			aMap.put("Result", resultado);
		}else{
			aMap.put("result", "Error, parametros incorrectos");
		}
		return new Gson().toJson(aMap);
	}
	
	
	@RequestMapping(value = "/viajes/calificar", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public String calificar(@RequestParam("viajeId") Integer viajeId, @RequestParam("pasajeroId") Integer pasajeroId, @RequestParam("puntaje") Integer puntaje, @RequestParam("comentario") String comentario) {	
		/*
		 * curl -X POST -d "viajeId=1&pasajeroId=1&puntaje=4&comentario=Conductor agradable" "http://localhost:8080/MuberRESTful/rest/services/viajes/calificar"
		 */
		Map<String, Object> aMap = new HashMap<String, Object>();
		if ( (viajeId != null) & (pasajeroId != null) & (puntaje != null) & (comentario != null)){
			ViajeDAO dao = new ViajeDAO();
			String resultado = dao.calificarViaje(viajeId, pasajeroId, puntaje, comentario);
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
		ConductorDAO dao = new ConductorDAO();
		List <ConductorDTO> conductores =  dao.obtenerTop10();
		for (ConductorDTO c : conductores) {
			mapAtributos.put("nombre", c.getNombre());
			mapAtributos.put("password", c.getPassword());
			mapAtributos.put("fechaVencimientoLic", c.getFechaVencimientoLic());
			mapAtributos.put("fechaIngreso", c.getFechaIngreso());
			mapAtributos.put("puntajePromedio", c.getPuntajePromedio());
			mapConductores.put(c.getIdUsuario(), new LinkedHashMap<String, Object>(mapAtributos));
		}
		mapAll.put("result", "OK");
		mapAll.put("conductores", mapConductores);
		return new Gson().toJson(mapAll);
	}
		
	@RequestMapping(value = "/pasajeros/cargarCredito", method = RequestMethod.PUT, produces = "application/json", headers = "Accept=application/json")
	public String cargarCredito(@RequestBody Map<String, Object> params) {
		/*
		 * Probado con Postman {"pasajeroId":1,"monto":200}
		 */
		Double aux = (Double) params.get("pasajeroId");
		Integer pasajeroId =  aux.intValue();
		Double monto = (Double) params.get("monto");
		Map<String, Object> aMap = new HashMap<String, Object>();
		if  ((pasajeroId != null) && (monto != null)) {
			PasajeroDAO dao = new PasajeroDAO();
			String resultado = dao.addCredito(pasajeroId, monto);
			aMap.put("result", resultado);
		}else{
			aMap.put("result", "Error parametros incorrectos");
		}
		return new Gson().toJson(aMap);
	}
	
	@RequestMapping(value = "/viajes/finalizar", method = RequestMethod.PUT, produces = "application/json", headers = "Accept=application/json")
	public String finalizar(@RequestBody Map<String, Integer> params) {	
		/*
		 * Probado con Postman {"viajeId":1}
		 */
		Map<String, Object> aMap = new HashMap<String, Object>();
		if  (params.get("viajeId") != null) {
			ViajeDAO dao = new ViajeDAO();
			if (dao.finalizarViaje(params.get("viajeId"))){
				aMap.put("Result", "Viaje Fianlizado");
			}else{
				aMap.put("Result", "Error, no existe viaje abierto con el id ingresado");
			}
		}else{
			aMap.put("Result", "Error parametro incorrecto");
		}
		return new Gson().toJson(aMap);
	}
}
