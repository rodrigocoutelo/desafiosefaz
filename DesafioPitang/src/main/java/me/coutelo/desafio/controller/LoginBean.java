package me.coutelo.desafio.controller;

import java.io.*;
import java.util.logging.*;

import javax.enterprise.context.*;
import javax.faces.application.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.event.*;
import javax.inject.*;

import org.primefaces.*;

import me.coutelo.desafio.data.*;
import me.coutelo.desafio.model.*;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -4560058611907729487L;

	@Inject
	private UserRepository repository;

	@Inject
	private Logger log;

	@Inject
	private FacesContext fc;

	private UIInput uiPassword, uiEmail;

	public UIInput getUiEmail() {
		return uiEmail;
	}

	public void setUiEmail(final UIInput uiEmail) {
		this.uiEmail = uiEmail;
	}

	public UIInput getUiPassword() {
		return uiPassword;
	}

	public void setUiPassword(final UIInput uiPassword) {
		this.uiPassword = uiPassword;
	}

	private User user;

	private String email;

	private String password;

	private boolean logged;

//	@PostConstruct
//	public void initNewUser() {
//		email = "";
//		password = "";
//
//	}

	public String validLogin() {
		FacesMessage message = null;
		boolean loggedIn = false;
		final FacesContext context = FacesContext.getCurrentInstance();

		user = repository.findByEmail(email);

		if (user != null) {

			if (user.getPassword().equals(password)) {

				if (user.getRole().getLabel().equals("ADMNISTRATOR") || user.getRole().getLabel().equals("MASTER")) {
					loggedIn = true;
					log.info("Logou como: " + user.getRole().getLabel() + " Esperado: ADMIN ou MASTER ");
					context.getExternalContext().getSessionMap().put("user", user);
					context.getExternalContext().getSessionMap().put("loggedIn", loggedIn);
					return "adminpage?faces-redirect=true";

				} else {

					loggedIn = true;
					log.info("Logou como: " + user.getRole().getLabel() + " Esperado: USER");
					context.getExternalContext().getSessionMap().put("user", user);
					context.getExternalContext().getSessionMap().put("loggedIn", loggedIn);
					return "userpage?faces-redirect=true";

				}

			} else {
				log.info("Password Invalido para o email: " + email + " e password:" + password);
				loggedIn = false;
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loggin Error",
						"Senha inválida");
				FacesContext.getCurrentInstance().addMessage(uiPassword.getClientId(), message);
				user = null;
			}

		} else {
			log.info("Usuario não encontrado para o email: " + email + " e password:" + password);
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loggin Error",
					"Usuário não encontrado");
			FacesContext.getCurrentInstance().addMessage(uiEmail.getClientId(), message);

			user = null;
		}

		return null;

	}

	public String logout() {
		final FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
		context.getExternalContext().getSessionMap().put("user", null);
		context.getExternalContext().getSessionMap().put("loggedIn", null);
		PrimeFaces.current().ajax().addCallbackParam("loggedIn", false);
		return "index?faces-redirect=true";

	}

//	public void setIsLogged() {
//
//		final FacesContext context = FacesContext.getCurrentInstance();
//
//		if (context.getExternalContext().getSessionMap().get("loggedIn") != null
//				&& context.getExternalContext().getSessionMap().get("user") != null) {
//
//			setLogged(true);
//
//		} else {
//
//			setLogged(false);
//		}
//
//	}

	public String doLoggin(final ComponentSystemEvent event) {

		final FacesContext context = FacesContext.getCurrentInstance();

		if (context.getExternalContext().getSessionMap().get("loggedIn") != null
				&& context.getExternalContext().getSessionMap().get("user") != null) {

			return "login?faces-redirect=true";

		} else {

			User user = null;
			user = (User) context.getExternalContext().getSessionMap().get("user");
			if (user.getRole().getLabel().equals("Administrador") || user.getRole().getLabel().equals("Master")) {

				return "admin?faces-redirect=true";

			}

			return "index?faces-redirect=true";
		}

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

	public FacesContext getFc() {
		return fc;
	}

	public void setFc(final FacesContext fc) {
		this.fc = fc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(final boolean logged) {
		this.logged = logged;
	}

}
