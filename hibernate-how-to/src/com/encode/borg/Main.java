package com.encode.borg;

import java.io.*;
import java.sql.Date;
import java.util.List;

import org.hibernate.Session;

import com.encode.borg.beans.*;
import com.encode.borg.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		generateUserData();
		printUserData();

		deleteUserWithId(1);
		printUserData();
	}

	private static void printUserData() {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
		List<Person> persons = listPersons(session);
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
		}finally {
			session.close();
		}

	}

	private static void deleteUserWithId(long id) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Person p = (Person) session.get(Person.class, id);
			session.beginTransaction();
			session.delete(p);
			session.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}

	private static void generateUserData() {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			createPerson("Popa", "Mihaela", session);
			createPerson("Popa", "Octavian", session);

			Person p = (Person) session.get(Person.class, 1L);
			createPersonJob("casnica", 100, p, session);
			createPersonJob("sotie", 200, p, session);
			createPersonJob("mamica", 150, p, session);

			createPersonRelative("varu Nelutu", p, session);
			createPersonRelative("mama Maria", p, session);
			createPersonRelative("verisoara Raluca", p, session);

			p = (Person) session.get(Person.class, 2L);
			createPersonJob("programator", 1500, p, session);
			createPersonJob("cititor", 10, p, session);

			createPersonRelative("nepotul Mihai", p, session);
			createPersonRelative("varul Marius", p, session);
			session.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}

	private static void createPersonJob(String nume, int salary, Person p, Session session) {
			PersonJob pj = new PersonJob();
			pj.setCurrency(PersonJob.DEFAULT_CURRENCY);
			pj.setNumeJob(nume);
			pj.setSalary(salary);
			pj.setPerson(p);
		session.save(pj);
	}

	private static void createPersonRelative(String nume, Person p, Session session) {
			PersonRelative pr = new PersonRelative();
			pr.setNumeGradRudenie(nume);
			pr.setPerson(p);
		session.save(pr);
	}

	private static void createPerson(String nume, String prenume, Session session) {
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

		session.save(p);
	}

	private static List<Person> listPersons(Session session) {
		return HibernateUtil.getQuery(session, QueryMappings.GET_ALL_PERSONS).list();
	}

}
