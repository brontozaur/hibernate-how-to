package com.encode.borg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.*;

import com.encode.borg.beans.*;
import com.encode.borg.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		// preparing database
		// HibernateUtil.prepareDatabase();

		// generateUserData();

		List<Person> persons = listPersons();
		for (Person person : persons) {
			System.err.println(person.getPrenume() + ", " + person.getNume());
			for (PersonJob pj : person.getJobs()) {
				System.err.println(pj.getNumeJob() + ", salary: " + pj.getSalary() + " " + pj.getCurrency());
			}
		}

		// test();
	}

	private static void generateUserData() {
		createPerson("Popa", "Mihaela");
		createPerson("Popa", "Octavian");

		Query q = HibernateUtil.getQuery(QueryMappings.GET_PERSON_BY_ID);
		q.setParameter("x", 1);
		Person p = (Person) q.list().get(0);
		createPersonJob("casnica", 100, p);
		createPersonJob("sotie", 200, p);
		createPersonJob("mamica", 150, p);

		q.setParameter("x", 2);
		p = (Person) q.list().get(0);
		createPersonJob("programator", 1500, p);
		createPersonJob("cititor", 10, p);
	}

	private static void createPersonJob(String nume, int salary, Person p) {
		try {
			PersonJob pj = new PersonJob();
			pj.setCurrency(PersonJob.DEFAULT_CURRENCY);
			pj.setNumeJob(nume);
			pj.setSalary(salary);
			pj.setPerson(p);
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(pj);
			session.getTransaction().commit();
		}
		catch (HibernateException ex) {
			ex.printStackTrace();
		}
	}

	private static void createPerson(String nume, String prenume) {
		try {
			Person p = new Person();
			p.setActiv(true);
			p.setNume(nume);
			p.setPrenume(prenume);
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
		}
		catch (HibernateException ex) {
			ex.printStackTrace();
		}
	}

	private static void test() {

		// Read
		System.out.println("******* READ *******");
		List<AppUpdate> updates = list();
		System.out.println("Total updates: " + updates.size());

		// Write
		System.out.println("******* WRITE *******");
		AppUpdate up = new AppUpdate();
		up.setCreate(new Timestamp(System.currentTimeMillis()));
		up.setQuery("SELECT 1");
		up.setStatus(1);
		up.setUserFirstname("John");
		up.setUserLastname("Malkovich");
		up = save(up);
		up = read(up.getId());
		System.out.printf("%d %s %s \n", up.getId(), up.getUserFirstname(), up.getUserLastname());

		// Update
		System.out.println("******* UPDATE *******");
		AppUpdate up2 = read(1); // read employee with id 1
		System.out.println("Name Before Update:" + up2.getUserFirstname());
		up2.setUserFirstname("Octavian");
		update(up2); // save the updated employee details

		up2 = read(1); // read again employee with id 1
		System.out.println("Name Aftere Update:" + up2.getUserFirstname());

		// Delete
		System.out.println("******* DELETE *******");
		// delete(up);
		AppUpdate up3 = read(up.getId());
		System.out.println("Object:" + up3);
	}

	private static List<Person> listPersons() {
		try {
			Session session = HibernateUtil.getSession();

			List<Person> persons = HibernateUtil.getQuery(QueryMappings.GET_ALL_PERSONS).list();
			session.close();
			return persons;
		}
		catch (HibernateException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static List<AppUpdate> list() {
		try {
			Session session = HibernateUtil.getSession();

			List<AppUpdate> employees = session.createQuery("from AppUpdate").list();
			session.close();
			return employees;
		}
		catch (HibernateException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static AppUpdate read(long id) {
		Session session = HibernateUtil.getSession();

		AppUpdate appUpdate = (AppUpdate) session.get(AppUpdate.class, id);
		session.close();
		return appUpdate;
	}

	private static AppUpdate save(AppUpdate appUpdate) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();

		Long id = (Long) session.save(appUpdate);
		appUpdate.setId(id);

		session.getTransaction().commit();

		session.close();

		return appUpdate;
	}

	private static AppUpdate update(AppUpdate appUpdate) {
		Session session = HibernateUtil.getSession();

		session.beginTransaction();

		session.merge(appUpdate);

		session.getTransaction().commit();

		session.close();
		return appUpdate;

	}

	private static void delete(AppUpdate appUpdate) {
		Session session = HibernateUtil.getSession();

		session.beginTransaction();

		session.delete(appUpdate);

		session.getTransaction().commit();

		session.close();
	}

}
