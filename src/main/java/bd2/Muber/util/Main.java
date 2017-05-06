package bd2.Muber.util;

import java.util.Date;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import bd2.Muber.model.Comentario;
import bd2.Muber.model.Conductor;
import bd2.Muber.model.Muber;
import bd2.Muber.model.Pasajero;
import bd2.Muber.model.Viaje;

public class Main {

	public static void main(String[] args) {
		System.out.println("----------------------- Setting up Hibernate -----------------------");
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		System.out.println("Droping schema.........");
		new SchemaExport(cfg).drop(true, true);
		System.out.println("DONE.");
		
		System.out.println("Generating schema.........");
		new SchemaExport(cfg).create(true, true);
		System.out.println("DONE.");

		System.out.println("Building sessions.........");
		
		SessionFactory sf = cfg.buildSessionFactory();
		
		Conductor conductor = new Conductor("Roberto", "123456", new Date(), new Date());
		
		Viaje viaje = new Viaje("La Plata", "Tres Arroyos", 900, 10, new Date(), conductor);

		Pasajero pasajero1 = new Pasajero("German", "123456", new Date(), 1500);
		
		Pasajero pasajero2 = new Pasajero("Alicia", "123456", new Date(), 1500);
		
		Pasajero pasajero3 = new Pasajero("Margarita", "123456", new Date(), 1500);
		
		viaje.addPasajero(pasajero1);
		viaje.addPasajero(pasajero2);
		viaje.addPasajero(pasajero3);
		
		
		Comentario comentario1 = new Comentario(5, "Muy buen conductor", pasajero1);
		viaje.addComentario(comentario1);
		
		Comentario comentario2 = new Comentario(4, "Buen conductor", pasajero2);
		viaje.addComentario(comentario2);
		
		Comentario comentario3 = new Comentario(4, "Buen conductor", pasajero3);
		viaje.addComentario(comentario3);
		
		float costo = viaje.costoPorPasajero();
		pasajero1.descontarCredito(costo);
		pasajero2.descontarCredito(costo);
		pasajero3.descontarCredito(costo);
		
		Muber muber = new Muber();
		muber.addViaje(viaje);
		muber.addUser(conductor);
		muber.addUser(pasajero1);
		muber.addUser(pasajero2);
		muber.addUser(pasajero3);
		
		Session session = sf.openSession();

		Transaction tx = session.beginTransaction();
		session.persist(muber);
		tx.commit();
		
		 	
	}

}
