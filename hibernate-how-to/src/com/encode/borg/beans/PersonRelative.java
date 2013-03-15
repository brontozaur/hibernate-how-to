package com.encode.borg.beans;

import javax.persistence.*;

import com.encode.borg.BeanSQLMappings;

@Cacheable
@Entity
@Table(name = BeanSQLMappings.PERSON_RELATIVE_TABLE_NAME)
public class PersonRelative {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = BeanSQLMappings.PERSON_RELATIVE_COLUMN_ID, unique = true)
	private long id;

	@Column(name = "numeGradRudenie", nullable = false)
	private String numeGradRudenie;

	@ManyToOne
	@JoinColumn(name = BeanSQLMappings.PERSON_RELATIVE_COLUMN_PERSON_ID, nullable = false)
	private Person person;

	public String getNumeGradRudenie() {
		return numeGradRudenie;
	}

	public void setNumeGradRudenie(String numeGradRudenie) {
		this.numeGradRudenie = numeGradRudenie;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public long getId() {
		return id;
	}
}
