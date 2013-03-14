package com.encode.borg.util;

import java.util.*;

import javax.persistence.*;

public class PersistenceUtil {

	private static Map<String, EntityManagerFactory> factories = new HashMap<String, EntityManagerFactory>();
	private static String DEFAULT_PERSISTENCE_UNIT = "BORG";

	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
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
		EntityManagerFactory factory = getEntityManagerFactory(persistenceUnitName);
		return factory.createEntityManager();
	}

	public static EntityManager getEntityManager() {
		return getEntityManager(DEFAULT_PERSISTENCE_UNIT);
	}

	public static void closeFactory(String persistenceUnitName) {
		EntityManagerFactory factory = getEntityManagerFactory(persistenceUnitName);
		if (factory.isOpen()) {
			factory.close();
		}
		factories.remove(persistenceUnitName);
	}

	public static void closeAllFactories() {
		for (Iterator<String> it = factories.keySet().iterator(); it.hasNext();) {
			closeFactory(it.next());
		}
	}

}
