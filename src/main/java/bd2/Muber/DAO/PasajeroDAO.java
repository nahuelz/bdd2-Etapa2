package bd2.Muber.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bd2.Muber.DTO.PasajeroDTO;

public class PasajeroDAO {
	
	public PasajeroDAO(){
		
	}
	
	protected Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}
	
	public List<PasajeroDTO> obtenerPasajeros (){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		List<PasajeroDTO> pasajeros = session.createQuery("from Pasajero").list();
		tx.rollback();
		session.close();
		return pasajeros;
	}
		
	
	private PasajeroDTO obtenerPasajero (Integer pasajeroId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "FROM Pasajero P WHERE P.idUsuario = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, pasajeroId);

		PasajeroDTO pasajero = null;
		if (query.uniqueResult() != null){
			pasajero = (PasajeroDTO) query.uniqueResult();
		}
		tx.rollback();
		session.close();
		
		return pasajero;
	}
}
