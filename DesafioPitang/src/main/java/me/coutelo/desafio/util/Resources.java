package me.coutelo.desafio.util;

import java.util.logging.*;

import javax.enterprise.context.*;
import javax.enterprise.inject.*;
import javax.enterprise.inject.spi.*;
import javax.faces.context.*;
import javax.persistence.*;

public class Resources {

	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLog(final InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
