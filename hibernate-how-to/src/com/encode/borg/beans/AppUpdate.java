package com.encode.borg.beans;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "hibernate_app_update")
public class AppUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "query")
	private String query;

	@Column(name = "status")
	private int status;

	@Column(name = "created")
	private Timestamp create;

	@Column(name = "userFirstname")
	private String userFirstname;

	@Column(name = "userLastname")
	private String userLastname;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreate() {
		return create;
	}

	public void setCreate(Timestamp create) {
		this.create = create;
	}

	public String getUserFirstname() {
		return userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

}
