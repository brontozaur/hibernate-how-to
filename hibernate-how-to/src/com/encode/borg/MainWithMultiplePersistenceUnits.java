package com.encode.borg;

import java.io.*;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.encode.borg.beans.*;
import com.encode.borg.util.PersistenceUtil;

public class MainWithMultiplePersistenceUnits {

	public static void main(String[] args) {
		System.err.println("---------------operations using " + PersistenceUtil.DEFAULT_PERSISTENCE_UNIT + " using borg_hibernate db persistence unit START------------------------------------\n");
		processPU(PersistenceUtil.DEFAULT_PERSISTENCE_UNIT);
		System.err.println("---------------operations using " + PersistenceUtil.DEFAULT_PERSISTENCE_UNIT + " using borg_hibernate db persistence unit END------------------------------------\n");
		System.err.println("---------------operations using " + PersistenceUtil.ENCODE_PERSISTENCE_UNIT + " using encode_hibernate persistence unit START------------------------------------\n");
		processPU(PersistenceUtil.ENCODE_PERSISTENCE_UNIT);
		System.err.println("---------------operations using " + PersistenceUtil.ENCODE_PERSISTENCE_UNIT + " using encode_hibernate persistence unit END------------------------------------\n");

		PersistenceUtil.closeAllFactories();
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
			manager = PersistenceUtil.getEntityManager(puName);
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
			manager = PersistenceUtil.getEntityManager(puName);
			manager.getTransaction().begin();
			Person p = manager.find(Person.class, id);
			manager.getTransaction();
			manager.remove(p);
			manager.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			manager.getTransaction().rollback();
		}
		finally {
			manager.close();
		}
	}

	private static void generateUserData(String puName) {
		EntityManager manager = null;
		try {
			manager = PersistenceUtil.getEntityManager(puName);
			manager.getTransaction().begin();

			if (PersistenceUtil.DEFAULT_PERSISTENCE_UNIT.equals(puName)) {
			createPerson("Popa", "Mihaela", manager);
			createPerson("Popa", "Octavian", manager);

			Person p = manager.find(Person.class, 1L);
			createPersonJob("casnica", 100, p, manager);
			createPersonJob("sotie", 200, p, manager);
			createPersonJob("mamica", 150, p, manager);

			createPersonRelative("varu Nelutu", p, manager);
			createPersonRelative("mama Maria", p, manager);
			createPersonRelative("verisoara Raluca", p, manager);

			p = manager.find(Person.class, 2L);
			createPersonJob("programator", 1500, p, manager);
			createPersonJob("cititor", 10, p, manager);

			createPersonRelative("nepotul Mihai", p, manager);
			createPersonRelative("varul Marius", p, manager);
			} else {
				createPerson("Robert", "de Niro", manager);
				createPerson("Tom", "Schneider", manager);

				Person p = manager.find(Person.class, 1L);
				createPersonJob("actor", 100, p, manager);
				createPersonJob("regizor", 200, p, manager);
				createPersonJob("scenarist", 150, p, manager);

				createPersonRelative("cousing Willy", p, manager);
				createPersonRelative("uncle John", p, manager);
				createPersonRelative("cousin Maria", p, manager);

				p = manager.find(Person.class, 2L);
				createPersonJob("sql patcher", 1500, p, manager);
				createPersonJob("sluga lui Dietmar", 10, p, manager);

				createPersonRelative("no relatives", p, manager);
			}
			manager.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			manager.getTransaction().rollback();
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

	private static void createPerson(String nume, String prenume, EntityManager manager) {
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
	}

	private static List<Person> listPersons(EntityManager manager) {
		return manager.createNamedQuery(QueryMappings.GET_ALL_PERSONS).getResultList();
	}

}
