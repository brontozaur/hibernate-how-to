package com.encode.borg.util;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration config = new Configuration();
			config.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			return config.buildSessionFactory(serviceRegistry);
		}
		catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static EntityManager buildSessionFactoryUsingPersistence() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("BORG");
		EntityManager em = emf.createEntityManager();
		return em;
	}

	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session openSession() {
		return getSessionFactory().openSession();
	}

	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public static org.hibernate.Query getQuery(Session session, String queryName) {
		return session.getNamedQuery(queryName);
	}

	public static void shutdown() {
		getSessionFactory().close();
	}

	public static void prepareDatabase() {
		getSessionFactory();
	}

}
