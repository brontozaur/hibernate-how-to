package com.encode.borg.util;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.jboss.logging.Logger;

import com.encode.borg.beans.*;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Logger logger = Logger.getLogger(HibernateUtil.class);

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration config = new Configuration();
			config.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			return config.buildSessionFactory(serviceRegistry);
		}
		catch (Throwable ex) {
			logger.error("buildSessionFactory() error" + ex);
			throw new ExceptionInInitializerError(ex);
		}
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

	public static void emptyDatabase() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.getTransaction().begin();
			Query query = null;
			query = session.createQuery("DELETE FROM " + PersonJob.class.getName());
			query.executeUpdate();
			query = session.createQuery("DELETE FROM " + PersonRelative.class.getName());
			query.executeUpdate();
			query = session.createQuery("DELETE FROM " + Person.class.getName());
			query.executeUpdate();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
	}

}
