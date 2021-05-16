package com.thumann.server.domain.pupil;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.user.UserCredentials;

@Entity
@NamedQueries({ @NamedQuery(name = Pupil.GET_PUPIL_COUNT, query = "SELECT COUNT(domain.id) FROM Pupil domain") })
public class Pupil extends Domain implements Serializable {

	private static final long serialVersionUID = -4340150305661866300L;

	public static final String GET_PUPIL_COUNT = "GET_PUPIL_COUNT";

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	private UserCredentials credentials;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public UserCredentials getCredentials()
	{
		return credentials;
	}

	public void setCredentials(UserCredentials credentials)
	{
		this.credentials = credentials;
	}

}
