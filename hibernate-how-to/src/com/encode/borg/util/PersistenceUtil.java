package com.encode.borg.util;

import java.util.*;

import javax.persistence.*;

import org.jboss.logging.Logger;

public class PersistenceUtil {

	private static Map<String, EntityManagerFactory> factories = new HashMap<String, EntityManagerFactory>();
	public static String DEFAULT_PERSISTENCE_UNIT = "BORG";
	public static String ENCODE_PERSISTENCE_UNIT = "ENCODE";
	private static Logger logger = Logger.getLogger(PersistenceUtil.class);

	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
		logger.info("requested EntityManagerFactory with persistence unit name: " + persistenceUnitName);
		EntityManagerFactory factory = factories.get(persistenceUnitName);
		if (factory != null) {
			if (!factory.isOpen()) {
				factory = Persistence.createEntityManagerFactory(persistenceUnitName);
				factories.put(persistenceUnitName, factory);
			}
		} else {
			factory = Persistence.createEntityManagerFactory(persistenceUnitName);
			factories.put(persistenceUnitName, factory);
		}
		return factory;
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return getEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT);
	}

	public static EntityManager getEntityManager(final String persistenceUnitName) {
		logger.info("requested getEntityManager with persistence unit name: " + persistenceUnitName);
		EntityManagerFactory factory = getEntityManagerFactory(persistenceUnitName);
		return factory.createEntityManager();
	}

	public static EntityManager getEntityManager() {
		return getEntityManager(DEFAULT_PERSISTENCE_UNIT);
	}

	public static void closeFactory(String persistenceUnitName) {
		logger.info("closing EntityManagerFactory: " + persistenceUnitName);
		EntityManagerFactory factory = getEntityManagerFactory(persistenceUnitName);
		if (factory.isOpen()) {
			factory.close();
		}
		factories.remove(persistenceUnitName);
		logger.info("EntityManagerFactory " + persistenceUnitName + " succesfully closed");
	}

	public static void closeAllFactories() {
		logger.info("closing all factories....factories size: " + factories.size());
		List<String> factoryNames = new ArrayList<>();
		factoryNames.addAll(factories.keySet());
		for (Iterator<String> it = factoryNames.iterator(); it.hasNext();) {
			closeFactory(it.next());
		 }
		logger.info("all factories were successfully closed.");
	}

}
