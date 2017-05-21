package bd2.Muber.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bd2.Muber.DTO.ConductorDTO;
import bd2.Muber.model.Conductor;
import bd2.Muber.model.Viaje;

public class ConductorDAO {
	
	protected Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}
	
	public List<ConductorDTO> obtenerConductores(){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Conductor> conductores = session.createQuery("from Conductor").list();
		List<ConductorDTO> conductoresDTO = new ArrayList<ConductorDTO>();
		for (Conductor c : conductores) {
			ConductorDTO con = new ConductorDTO(c);
			conductoresDTO.add(con);
		}
		tx.rollback();
		session.disconnect();
		session.close();
		return conductoresDTO;
	}
	
	public ConductorDTO obtenerConductor(Integer conductorId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Conductor conductor = (Conductor) session.get(Conductor.class, conductorId);
		ConductorDTO conductorDTO = null;
		if (conductor != null){		
			conductorDTO = new ConductorDTO(conductor);
		}
		tx.rollback();
		session.disconnect();
		session.close();
		return conductorDTO;
	}
	
	public List<ConductorDTO> obtenerTop10(){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<Conductor> conductores = session.createQuery("from Conductor c where c not in (select v.Conductor from Viaje v where v.estado = 'A')").list();
		conductores.sort((c1, c2) -> c2.puntajePromedio().compareTo(c1.puntajePromedio()));
		conductores = conductores.subList(0, Integer.min(conductores.size(), 10));
		List<ConductorDTO> conductoresDTO = new ArrayList<ConductorDTO>();
		for (Conductor c : conductores) {
			ConductorDTO con = new ConductorDTO(c);
			con.setPuntajePromedio(c.puntajePromedio());
			conductoresDTO.add(con);
		}
		tx.rollback();
		session.disconnect();
		session.close();
		return conductoresDTO;
	}
	
}
