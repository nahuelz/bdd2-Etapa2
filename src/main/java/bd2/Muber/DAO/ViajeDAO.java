package bd2.Muber.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;

import bd2.Muber.DTO.ViajeDTO;

public class ViajeDAO {
	
	protected Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}
	
	private ViajeDTO obtenerViaje (Integer viajeId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "FROM Viaje V WHERE V.idViaje = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, viajeId);
		ViajeDTO viaje = null;
		if (query.uniqueResult() != null){
			viaje = (ViajeDTO) query.uniqueResult();
		}
		tx.rollback();
		session.close();
		return viaje;
	}
	
	
	
}
