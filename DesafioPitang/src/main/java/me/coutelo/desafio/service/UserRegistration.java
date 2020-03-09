
package me.coutelo.desafio.service;

import java.util.logging.*;

import javax.ejb.*;
import javax.enterprise.event.*;
import javax.inject.*;
import javax.persistence.*;

import me.coutelo.desafio.data.*;
import me.coutelo.desafio.exception.*;
import me.coutelo.desafio.model.*;

@Stateless
public class UserRegistration {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private UserRepository repository;

	@Inject
	private Event<User> userEventSrc;

	public void save(final User user) throws Exception {
		log.info("Registering " + user.getName());

		final User userPersisted = repository.findByEmail(user.getEmail());

		if (userPersisted != null) {

			throw new UserExistentException("Já existe um usuário cadastrado com esse e-mail!");

		} else {

			em.merge(user);
			userEventSrc.fire(user);

		}

	}

	public void update(final User user) throws Exception {
		log.info("Updating " + user.getName() + "Qtd phones: " + user.getPhones().size());

		if (user != null && user.getId() != null) {

			final User userPersisted = repository.findById(user.getId());
			userPersisted.setName(user.getName());
			userPersisted.setEmail(user.getEmail());
			userPersisted.setPassword(user.getPassword());
			userPersisted.removeAllPhones();

			for (final Phone phone : user.getPhones()) {
				userPersisted.addPhone(phone);
			}

			em.merge(userPersisted);

		} else {
			log.info("ID" + user.getId() + " não localizado ao fazer Updating " + user.getName() + "Password: "
					+ user.getPassword());
			throw new UserNotFoundException("Usuário não cadastrado, impossível fazer atualização");

		}

	}

}
