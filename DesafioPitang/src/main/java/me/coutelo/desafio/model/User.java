package me.coutelo.desafio.model;

import java.io.*;
import java.util.*;

import javax.enterprise.context.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -2195815172557504093L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column
	@NotNull(message = "Name field should not be null")
	@Size(min = 5, message = "Name deve ter de  5 a 200 caracteres")
	private String name;

	@Column(unique = true)
	@NotNull(message = "E-mail field should not be null")
	@Email(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "E-mail inválido")
	private String email;

	@Column
	@NotNull(message = "Password field should not be null")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Phone> phones = new ArrayList<>();

	@Column(name = "created_at", updatable = false)
	private Date createdat;

	@Column(name = "update_at")
	private Date updateat;

	@ApplicationScoped
	public enum Role {
		MASTER("MASTER"), ADMINISTRATOR("ADMINISTRATOR"), USER("USER");

		private String label;

		private Role(final String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	public Role role;

	public User() {
	}

	@PrePersist
	protected void onCreate() {
		this.createdat = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateat = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void addPhone(final Phone phone) {
		this.phones.add(phone);
		phone.setUser(this);

	}

	public void removeAllPhones() {
		this.phones.clear();
	}

	public void removePhone(final Phone phone) {
		phone.setUser(null);
		this.phones.remove(phone);
	}

	public Date getUpdateat() {
		return updateat;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setPhones(final List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phones="
				+ phones + ", createdat=" + createdat + ", updateat=" + updateat + ", role=" + role.getLabel() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdat, email, id, name, password, phones, role, updateat);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		return Objects.equals(createdat, other.createdat) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(phones, other.phones)
				&& role == other.role && Objects.equals(updateat, other.updateat);
	}

}
