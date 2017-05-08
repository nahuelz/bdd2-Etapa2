package bd2.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import bd2.Muber.model.*;

import com.google.gson.Gson;

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
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapPasajeros = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Muber muber = (Muber) session.createQuery("from Muber").uniqueResult();
		Set<Pasajero> pasajeros;
		pasajeros = new HashSet<Pasajero>();
		pasajeros = muber.obtenerPasajeros();
		tx.rollback();
		for (Pasajero p : pasajeros) {
			mapAtributos.put("nombre", p.getNombre());
			mapAtributos.put("password", p.getPassword());
			mapAtributos.put("creditos", p.getCreditos());
			mapAtributos.put("fechaIngreso", p.getFechaIngreso());
			mapPasajeros.put(p.getIdUsuario(), new LinkedHashMap<String, Object>(mapAtributos));
		}
		mapAll.put("pasajeros", mapPasajeros);
		return new Gson().toJson(mapAll);
		
	}
	
	@RequestMapping(value = "/conductores", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String conductores() {
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapConductores = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Muber muber = (Muber) session.createQuery("from Muber").uniqueResult();
		Set<Conductor> conductores;
		conductores = new HashSet<Conductor>();
		conductores = muber.obtenerConductores();
		tx.rollback();
		for (Conductor c : conductores) {
			mapAtributos.put("nombre", c.getNombre());
			mapAtributos.put("password", c.getPassword());
			mapAtributos.put("fechaVencimientoLic", c.getFechaVencimientoLic());
			mapAtributos.put("fechaIngreso", c.getFechaIngreso());
			mapConductores.put(c.getIdUsuario(), new LinkedHashMap<String, Object>(mapAtributos));
		}
		mapAll.put("conductores", mapConductores);
		return new Gson().toJson(mapAll);
		
	}
	
	@RequestMapping(value = "/abiertos", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public String abiertos() {
		Map<String, Object> mapAll = new LinkedHashMap<String, Object>();
		Map<Integer, Object> mapViajes = new LinkedHashMap<Integer, Object>();
		Map<String, Object> mapAtributos = new LinkedHashMap<String, Object>();
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Muber muber = (Muber) session.createQuery("from Muber").uniqueResult();
		Set<Viaje> viajes;
		viajes = new HashSet<Viaje>();
		viajes = muber.obtenerViajesAbiertos();
		tx.rollback();
		for (Viaje v : viajes) {
			mapAtributos.put("origen", v.getOrigen());
			mapAtributos.put("destino", v.getDestino());
			mapAtributos.put("costoTotal", v.getCostoTotal());
			mapAtributos.put("fecha", v.getFecha());
			mapAtributos.put("cantidadMaximaPasajeros", v.getCantidadMaximaPasajeros());
			mapAtributos.put("idConductor", v.getConductor().getIdConductor());
			mapAtributos.put("nombreConductor", v.getConductor().getNombre());
			mapViajes.put(v.getIdViaje(), new LinkedHashMap<String, Object>(mapAtributos));
		}
		mapAll.put("viajesAbiertos", mapViajes);
		return new Gson().toJson(mapAll);
		
	}
	 
}
