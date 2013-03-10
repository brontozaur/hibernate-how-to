package com.encode.borg.beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.encode.borg.BeanSQLMappings;
import com.encode.borg.QueryMappings;

@Entity
@Table(name = BeanSQLMappings.PERSON_TABLE_NAME)
@NamedQueries({
		@NamedQuery(name = QueryMappings.GET_ALL_PERSONS, query = "from Person"),
		@NamedQuery(name = QueryMappings.GET_PERSON_BY_ID, query = "from Person WHERE " + BeanSQLMappings.PERSON_COLUMN_ID + " = :x") })
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = BeanSQLMappings.PERSON_COLUMN_ID, nullable = false, unique = true)
	private long idUser = 0;

	@Column(name = "nume", nullable = false)
	private String nume;

	@Column(name = "prenume")
	private String prenume;

	@Column(name = "adresa")
	private String adresa;

	@Column(name = "telefon")
	private String telefon;

	@Column(name = "serieCI")
	private String serieCI;

	@Column(name = "nrCI")
	private String nrCI;

	@Column(name = "cnp")
	private String cnp;

	@Column(name = "parola")
	private String parola;

	@Column(name = "functia")
	private String functia;

	@Column(name = "studii")
	private String studii;

	@Column(name = "email")
	private String email;

	@Column(name = "activ")
	private boolean activ;

	@Column(name = "observatii")
	private String observatii;

	@Column(name = "data")
	private Date data;

	@Column(name = "serverTimestamp")
	private Timestamp serverTimestamp;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "image", length = 10_000_000)
	private byte[] image;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = PersonJob.class, orphanRemoval = true)
	@JoinColumn(name = BeanSQLMappings.PERSON_JOB_COLUMN_PERSON_ID)
	List<PersonJob> jobs;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = PersonRelative.class, orphanRemoval = true)
	@JoinColumn(name = BeanSQLMappings.PERSON_RELATIVE_COLUMN_PERSON_ID)
	List<PersonRelative> relatives;

	@Transient
	private String strNumeUser;

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(final String adresa) {
		this.adresa = adresa;
	}

	public String getCnp() {
		return this.cnp;
	}

	public void setCnp(final String cnp) {
		this.cnp = cnp;
	}

	public String getFunctia() {
		return this.functia;
	}

	public void setFunctia(final String functia) {
		this.functia = functia;
	}

	public String getStudii() {
		return this.studii;
	}

	public void setStudii(final String studii) {
		this.studii = studii;
	}

	public String getNrCI() {
		return this.nrCI;
	}

	public void setNrCI(final String nrCI) {
		this.nrCI = nrCI;
	}

	public final String getNume() {
		return this.nume;
	}

	public final void setNume(final String nume) {
		this.nume = nume;
	}

	public final String getPrenume() {
		return this.prenume;
	}

	public final void setPrenume(final String prenume) {
		this.prenume = prenume;
	}

	public String getParola() {
		return this.parola;
	}

	public void setParola(final String parola) {
		this.parola = parola;
	}

	public String getSerieCI() {
		return this.serieCI;
	}

	public void setSerieCI(final String serieCI) {
		this.serieCI = serieCI;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(final String telefon) {
		this.telefon = telefon;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setData(final Date data) {
		this.data = data;
	}

	public Date getData() {
		return this.data;
	}

	public final Timestamp getServerTimestamp() {
		return this.serverTimestamp;
	}

	public final void setServerTimestamp(final Timestamp serverTimestamp) {
		this.serverTimestamp = serverTimestamp;
	}

	public void setActiv(final boolean activ) {
		this.activ = activ;
	}

	public boolean isActiv() {
		return this.activ;
	}

	public final long getIdUser() {
		return this.idUser;
	}

	public final void setIdUser(final long idUser) {
		this.idUser = idUser;
	}

	public final String getObservatii() {
		return this.observatii;
	}

	public final void setObservatii(final String observatii) {
		this.observatii = observatii;
	}

	public String getStrNumeUser() {
		return this.strNumeUser;
	}

	public void setStrNumeUser(final String strNumeUser) {
		this.strNumeUser = strNumeUser;
	}

	public List<PersonJob> getJobs() {
		return jobs;
	}

	public List<PersonRelative> getRelatives() {
		return relatives;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
