package me.coutelo.desafio.model;

import java.io.*;
import java.util.*;

import javax.enterprise.inject.*;
import javax.inject.*;
import javax.persistence.*;

@Entity
@Table(name = "phone")

public class Phone implements Serializable {

	private static final long serialVersionUID = 500723978213005492L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)

	public User user;

	@Column(length = 2)
	private int ddd;

	@Column(length = 12)
	private String number;

	public enum PhoneType {
		CELULAR("Celular"), RESIDENCIAL("Residencial"), COMERCIAL("Comercial"), OUTRO("Outro");

		private String label;

		private PhoneType(final String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

	@Enumerated(EnumType.STRING)
	private PhoneType phonetype;

	public Phone() {

	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public int getDdd() {
		return ddd;
	}

	public void setDdd(final int ddd) {
		this.ddd = ddd;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String numero) {
		this.number = numero;
	}

	public PhoneType getPhonetype() {
		return phonetype;
	}

	public void setPhonetype(final PhoneType phonetype) {
		this.phonetype = phonetype;
	}

	@Named
	@Produces
	public PhoneType[] PhoneTypes() {

		return PhoneType.values();
	}

	@Override
	public String toString() {
		return "Phone [id=" + id + ", user=" + user.getId() + ", ddd=" + ddd + ", number=" + number + ", phonetype="
				+ phonetype.getLabel()
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ddd, id, number, phonetype, user);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Phone other = (Phone) obj;
		return ddd == other.ddd && Objects.equals(id, other.id) && Objects.equals(number, other.number)
				&& phonetype == other.phonetype && Objects.equals(user, other.user);
	}

}
