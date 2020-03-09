
package me.coutelo.desafio.data;

import java.util.*;

import javax.annotation.*;
import javax.enterprise.context.*;
import javax.enterprise.event.*;
import javax.enterprise.inject.*;
import javax.inject.*;

import me.coutelo.desafio.model.*;

@RequestScoped
public class UserListProducer {

	@Inject
	private UserRepository userRepository;

	private List<User> users;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	@Produces
	@Named
	public List<User> getUsers() {
		return users;
	}

	public void onUserListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final User user) {
		retrieveAllUsersOrderedByName();

	}

	@PostConstruct
	public void retrieveAllUsersOrderedByName() {
		users = userRepository.findAllOrderedByName();
	}

}
