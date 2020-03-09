package me.coutelo.desafio.test.util;

import org.junit.*;

import me.coutelo.desafio.model.*;
import me.coutelo.desafio.model.Phone.*;
import me.coutelo.desafio.model.User.*;

@Ignore
public class UsersForTest {

	public static User Joao() {
		final User user = new User();
		user.setName("João da Silva");
		user.setEmail("joao@domain.com");
		user.setPassword("123456");
		user.setRole(Role.USER);

		return user;
	}

	public static User Jose() {
		final User user = new User();
		user.setName("Jose da Silva");
		user.setEmail("jose@domain.com");
		user.setPassword("1234567");
		user.setRole(Role.USER);

		return user;
	}

	public static User Admin() {
		final User user = new User();

		user.setName("admin");
		user.setEmail("admin@domain.com");
		user.setPassword("123456890");
		user.setRole(Role.ADMINISTRATOR);

		return user;
	}

	public static User Master() {
		final User user = new User();
		user.setName("master");
		user.setEmail("master@domain.com");
		user.setPassword("abc1234");
		user.setRole(Role.ADMINISTRATOR);

		return user;
	}

	public static User NullNameUser() {
		final User user = new User();
		user.setName(null);
		user.setEmail("admin@domain.com");
		user.setPassword("123456");
		user.setRole(Role.ADMINISTRATOR);

		return user;
	}

	public static User ShortName() {
		final User user = new User();
		user.setName("abc");
		user.setEmail("admin@domain.com");
		user.setPassword("123456");
		user.setRole(Role.ADMINISTRATOR);

		return user;
	}

	public static User ShortEmail() {
		final User user = new User();
		user.setName("jonhh doe");
		user.setEmail("a@b.c");
		user.setPassword("123456");
		user.setRole(Role.ADMINISTRATOR);

		return user;
	}

	public static User userWihtPhones() {
		final User user = new User();
		final Phone phone = new Phone();
		phone.setDdd(81);
		phone.setNumber("3221.3648");
		phone.setPhonetype(PhoneType.COMERCIAL);

		final Phone phone2 = new Phone();
		phone2.setDdd(82);
		phone2.setNumber("93221.3648");
		phone2.setPhonetype(PhoneType.CELULAR);

		user.setName("admin");
		user.setEmail("admin@domain.com");
		user.setPassword("123456");
		user.setRole(Role.ADMINISTRATOR);
		user.addPhone(phone);
		user.addPhone(phone2);
		return user;
	}

}
