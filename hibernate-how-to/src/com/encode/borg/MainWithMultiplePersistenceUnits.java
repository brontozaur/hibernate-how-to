package com.encode.borg;

import java.io.*;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.encode.borg.beans.*;
import com.encode.borg.util.BorgPersistence;

public class MainWithMultiplePersistenceUnits {

	public static void main(String[] args) {
		System.err.println("---------------operations using " + BorgPersistence.DEFAULT_PERSISTENCE_UNIT + " using borg_hibernate db persistence unit START------------------------------------\n");
		processPU(BorgPersistence.DEFAULT_PERSISTENCE_UNIT);
		System.err.println("---------------operations using " + BorgPersistence.DEFAULT_PERSISTENCE_UNIT + " using borg_hibernate db persistence unit END------------------------------------\n");
		System.err.println("---------------operations using " + BorgPersistence.ENCODE_PERSISTENCE_UNIT + " using encode_hibernate persistence unit START------------------------------------\n");
		processPU(BorgPersistence.ENCODE_PERSISTENCE_UNIT);
		System.err.println("---------------operations using " + BorgPersistence.ENCODE_PERSISTENCE_UNIT + " using encode_hibernate persistence unit END------------------------------------\n");

		BorgPersistence.closeAllFactories();
	}

	private static void processPU(String persistenceUnitName) {
		generateUserData(persistenceUnitName);
		printUserData(persistenceUnitName);

		deleteUserWithId(1, persistenceUnitName);
		printUserData(persistenceUnitName);
	}

	private static void printUserData(String puName) {
		EntityManager manager = null;
		try {
			manager = BorgPersistence.getEntityManager(puName);
			List<Person> persons = listPersons(manager);
			for (Person person : persons) {
				System.err.println("----------------------------Jobs for user: " + person.getPrenume() + ", " + person.getNume() + "-----------------------------------");
				for (PersonJob pj : person.getJobs()) {
					System.err.println(pj.getNumeJob() + ", salary: " + pj.getSalary() + " " + pj.getCurrency());
				}
				System.err.println("----------------------------Relatives for user: " + person.getPrenume() + ", " + person.getNume() + "-----------------------------------");
				for (PersonRelative pr : person.getRelatives()) {
					System.err.println(pr.getNumeGradRudenie());
				}
			}
		}
		finally {
			manager.close();
		}

	}

	private static void deleteUserWithId(long id, String puName) {
		EntityManager manager = null;
		try {
			manager = BorgPersistence.getEntityManager(puName);
			manager.getTransaction().begin();
			Person p = manager.getReference(Person.class, id);
			System.err.println(p.getNume() + " has id: " + p.getIdUser());
			manager.remove(p);
			manager.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
		}
		finally {
			manager.close();
		}
	}

	private static void generateUserData(String puName) {
		EntityManager manager = null;
		try {
			manager = BorgPersistence.getEntityManager(puName);
			manager.getTransaction().begin();

			if (BorgPersistence.DEFAULT_PERSISTENCE_UNIT.equals(puName)) {
				createPerson("Popa", "Mihaela", puName);
				createPerson("Popa", "Octavian", puName);

				Person p = manager.getReference(Person.class, 1L);
			createPersonJob("casnica", 100, p, manager);
			createPersonJob("sotie", 200, p, manager);
			createPersonJob("mamica", 150, p, manager);

			createPersonRelative("varu Nelutu", p, manager);
			createPersonRelative("mama Maria", p, manager);
			createPersonRelative("verisoara Raluca", p, manager);

				p = manager.getReference(Person.class, 2L);
			createPersonJob("programator", 1500, p, manager);
			createPersonJob("cititor", 10, p, manager);

			createPersonRelative("nepotul Mihai", p, manager);
			createPersonRelative("varul Marius", p, manager);
			} else {
				createPerson("Robert", "de Niro", puName);
				createPerson("Tom", "Schneider", puName);

				Person p = manager.getReference(Person.class, 1L);
				createPersonJob("actor", 100, p, manager);
				createPersonJob("regizor", 200, p, manager);
				createPersonJob("scenarist", 150, p, manager);

				createPersonRelative("cousing Willy", p, manager);
				createPersonRelative("uncle John", p, manager);
				createPersonRelative("cousin Maria", p, manager);

				p = manager.getReference(Person.class, 2L);
				createPersonJob("sql patcher", 1500, p, manager);
				createPersonJob("sluga lui Dietmar", 10, p, manager);

				createPersonRelative("no relatives", p, manager);
			}
			manager.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
		}
		finally {
			manager.close();
		}
	}

	private static void createPersonJob(String nume, int salary, Person p, EntityManager manager) {
		PersonJob pj = new PersonJob();
		pj.setCurrency(PersonJob.DEFAULT_CURRENCY);
		pj.setNumeJob(nume);
		pj.setSalary(salary);
		pj.setPerson(p);
		manager.persist(pj);
	}

	private static void createPersonRelative(String nume, Person p, EntityManager manager) {
		PersonRelative pr = new PersonRelative();
		pr.setNumeGradRudenie(nume);
		pr.setPerson(p);
		manager.persist(pr);
	}

	private static void createPerson(String nume, String prenume, String puName) {
		EntityManager manager = null;
		try {
			manager = BorgPersistence.getEntityManager(puName);
			manager.getTransaction().begin();
		Person p = new Person();
		p.setActiv(true);
		p.setNume(nume);
		p.setPrenume(prenume);
		p.setAdresa("Sibiu");
		p.setCnp("11111");
		p.setData(new Date(System.currentTimeMillis()));
		p.setEmail("k@pax.ru");
		p.setFunctia("boss");
		p.setObservatii("");

		// loads an image into person
		File file = new File("d:\\borg.png");
		byte[] bFile = new byte[(int) file.length()];
		BufferedInputStream buff = null;
		try {
			buff = new BufferedInputStream(new FileInputStream(file));
			// convert file into array of bytes
			buff.read(bFile);
			buff.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (buff != null) {
				try {
					buff.close();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		p.setImage(bFile);

		manager.persist(p);
			manager.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
		}
		finally {
			manager.close();
		}
	}

	private static List<Person> listPersons(EntityManager manager) {
		return manager.createNamedQuery(QueryMappings.GET_ALL_PERSONS).getResultList();
	}

}
