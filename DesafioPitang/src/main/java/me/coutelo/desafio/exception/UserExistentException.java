package me.coutelo.desafio.exception;

import javax.ejb.*;

@ApplicationException
public class UserExistentException extends RuntimeException {

	private static final long serialVersionUID = -6194627081595907898L;

	public UserExistentException(final String message) {

		super(message);
	}

}