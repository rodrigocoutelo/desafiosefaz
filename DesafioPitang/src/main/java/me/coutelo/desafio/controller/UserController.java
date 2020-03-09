package me.coutelo.desafio.controller;

import java.util.logging.*;

import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.faces.application.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.inject.*;

import me.coutelo.desafio.exception.*;
import me.coutelo.desafio.model.*;
import me.coutelo.desafio.model.User.*;
import me.coutelo.desafio.service.*;

@Model
public class UserController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private UserRegistration userInclude;

	@Inject
	private Logger log;

	@Produces
	@Named
	private User newUser;

	private String password1;

	UIInput uiEmail;

	public UIInput getUiEmail() {
		return uiEmail;
	}

	public void setUiEmail(final UIInput uiEmail) {
		this.uiEmail = uiEmail;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(final User newUser) {
		this.newUser = newUser;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(final String password1) {
		this.password1 = password1;
	}

	@PostConstruct
	public void initNewUser() {
		newUser = new User();

	}

	public void save() throws Exception {

		try {
			newUser.setRole(Role.USER);
			userInclude.save(newUser);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parabéns!",
					"Seu cadastro foi realizado com sucesso");
			facesContext.addMessage(null, m);
			initNewUser();

		} catch (final UserExistentException ue) {
			log.info("Erro de registo, e-mail já cadastrado: " + ue.getMessage());
			final String errorMessage = ue.getMessage();
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não foi possível realizar seu cadastro", errorMessage);

			facesContext.addMessage(uiEmail.getClientId(), m);
		}

		catch (final Exception e) {
			final String errorMessage = getRootErrorMessage(e);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não foi possível realizar seu cadastro",
					errorMessage);
			facesContext.addMessage(null, m);
		}
	}

	private String getRootErrorMessage(final Exception e) {

		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {

			return errorMessage; // Caso de algum erro na hora de lançar o erro, imporvável.
		}

		Throwable t = e;
		while (t != null) {

			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}

		return errorMessage;
	}

}
