package me.coutelo.desafio.exception;

import javax.ejb.*;

@ApplicationException
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3377915123887120870L;

	public UserNotFoundException(final String message) {

		super(message);
	}
}
