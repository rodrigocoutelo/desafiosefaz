package me.coutelo.desafio.data;

import java.util.*;

import javax.enterprise.context.*;
import javax.inject.*;
import javax.persistence.*;
import javax.persistence.criteria.*;

import me.coutelo.desafio.model.*;

@ApplicationScoped
public class UserRepository {

	@Inject
	public EntityManager em;

	public User findById(final Long id) {
		return em.find(User.class, id);
	}

	public User findByEmail(final String email) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<User> criteria = cb.createQuery(User.class);
		final Root<User> user = criteria.from(User.class);
		user.fetch("phones", JoinType.INNER);
		criteria.select(user).where(cb.equal(user.get("email"), email));
		criteria.select(user);

		User persitedUser = null;

		try {

			persitedUser = em.createQuery(criteria).getSingleResult();

		} catch (final NoResultException e) {

		}
		return persitedUser;
	}

	public List<User> findAllOrderedByName() {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<User> criteria = cb.createQuery(User.class);
		final Root<User> user = criteria.from(User.class);
		criteria.select(user).orderBy(cb.asc(user.get("name")));
		return em.createQuery(criteria).getResultList();
	}

	public boolean isValidUser(final String email, final String password) {
		User user = new User();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<User> criteria = cb.createQuery(User.class);
		final Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(cb.equal(userRoot.get("email"), email));
		user = em.createQuery(criteria).getSingleResult();
		return password == user.getPassword();

	}
}
