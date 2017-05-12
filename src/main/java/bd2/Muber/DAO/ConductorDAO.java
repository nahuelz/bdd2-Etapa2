package bd2.Muber.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bd2.Muber.DTO.ConductorDTO;
import bd2.Muber.model.Conductor;

public class ConductorDAO {
	
	protected Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}
	
	private Conductor obtenerConductor (Integer conductorId){
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		
		String hql = "FROM Conductor C WHERE C.idUsuario = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, conductorId);

		Conductor conductor = null;
		if (query.uniqueResult() != null){
			conductor = (Conductor) query.uniqueResult();
		}
		
		tx.rollback();
		session.close();
		
	return conductor;
}
	
	
	

}
