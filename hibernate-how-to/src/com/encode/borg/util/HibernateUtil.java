package com.encode.borg.util;

import org.hibernate.*;
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

	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() {
		return getSessionFactory().openSession();
	}

	public static Query getQuery(Session session, String queryName) {
		return session.getNamedQuery(queryName);
	}

	public static void shutdown() {
		getSessionFactory().close();
	}

	public static void prepareDatabase() {
		getSessionFactory();
	}

}
