package com.encode.borg.beans;

import javax.persistence.*;

import com.encode.borg.BeanSQLMappings;

@Entity
@Table(name = BeanSQLMappings.PERSON_JOB_TABLE_NAME)
public class PersonJob {

	@Transient
	public final static String DEFAULT_CURRENCY = "EUR";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = BeanSQLMappings.PERSON_JOB_COLUMN_ID, unique = true)
	private long idPersonJob;

	@Column(name = "numeJob", nullable = false)
	private String numeJob;

	@Column(name = "salary", precision = 2)
	private int salary;

	@Column(name = "currency", precision = 2)
	private String currency;

	// extract the whole Person object, based on the mappings bellow.
	// Person column (personId) is related 1-n to PersonJob column (idPerson).
	@ManyToOne
	@JoinColumn(name = BeanSQLMappings.PERSON_JOB_COLUMN_PERSON_ID, nullable = false)
	private Person personx;

	public String getNumeJob() {
		return numeJob;
	}

	public void setNumeJob(String numeJob) {
		this.numeJob = numeJob;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Person getPerson() {
		return personx;
	}

	public long getIdPersonJob() {
		return idPersonJob;
	}

	public void setPerson(Person person) {
		this.personx = person;
	}
}
