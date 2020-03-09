package me.coutelo.desafio.controller;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.annotation.*;
import javax.enterprise.context.*;
import javax.enterprise.inject.*;
import javax.faces.application.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.inject.*;

import me.coutelo.desafio.exception.*;
import me.coutelo.desafio.model.*;
import me.coutelo.desafio.model.Phone.*;
import me.coutelo.desafio.service.*;

@Named
@SessionScoped
public class UpdateUserBean implements Serializable {

	private static final long serialVersionUID = -5880279898605545582L;

	private int ddd[] = { 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22, 24, 27, 28, 31, 32, 33, 34, 35, 37,
			38,
			41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 53, 54, 55, 61, 62, 63, 64, 65, 66, 67, 68, 69, 71, 73, 74, 75, 77,
			79, 81, 82, 83, 84, 85, 86, 87, 88, 89, 91, 92, 93, 94, 95, 96, 97, 98, 99 };

	@Inject
	private FacesContext facesContext;

	@Inject
	private UserRegistration userUpdate;

	@Inject
	private Logger log;

	@Produces
	private User user;

	private Long id;

	private String currentPassword;

	private String newPassword;

	private UIInput uiCurrentPassword;

	private UIInput uiNewPassword;

	private int phoneDdd;

	private String phoneNumber;

	private String typeOfPhone;

	private List<Phone> phonesList;

	public List<Phone> getPhonesList() {
		return phonesList;
	}

	public void setPhonesList(final List<Phone> phonesList) {
		this.phonesList = phonesList;
	}

	public Phone getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(final Phone phoneList) {
		this.phoneList = phoneList;
	}

	private Phone phoneList;

	@PostConstruct
	public void init() {

		user = (User) facesContext.getExternalContext().getSessionMap().get("user");
		phonesList = user.getPhones();
		currentPassword = "";
		newPassword = "";

	}

	public void update() throws Exception {

		try {
			userUpdate.update(user);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Seu dados foram atualizados com sucesso",
					"");
			facesContext.addMessage(null, m);

		} catch (final UserNotFoundException une) {
			final String errorMessage = getRootErrorMessage(une);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usuario não encontrado",
					errorMessage);
			facesContext.addMessage(null, m);

		} catch (final UserExistentException ue) {

			final String errorMessage = getRootErrorMessage(ue);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Já existe um usuário cadastrado com e-mail",
					errorMessage);
			facesContext.addMessage(null, m);

		} catch (final Exception e) {

			final String errorMessage = getRootErrorMessage(e);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um erro, atualização não foi possivel",
					errorMessage);
			facesContext.addMessage(null, m);
		}
	}

	public void updatePassword() throws Exception {

		try {

			log.info("Tentando atualizar a senha. Senha atual digitada: " + currentPassword +
					" Senha gravada no banco: " + user.getPassword() +
					"Senha nova: " + newPassword);

			if (currentPassword.equals(user.getPassword())) {

				user.setPassword(newPassword);
				userUpdate.update(user);

				final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Seu dados foram atualizados com sucesso",
						"");
				facesContext.addMessage(null, m);

			} else {

				log.info("Não são iguais atual : " + currentPassword +
						" Senha gravada no banco: " + user.getPassword() +
						"Senha nova: " + newPassword);
				final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Senha digitada não confere com sua senha atual",
						"");
				facesContext.addMessage(null, m);

			}

		} catch (final UserNotFoundException une) {
			final String errorMessage = getRootErrorMessage(une);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usuario não encontrado",
					errorMessage);
			facesContext.addMessage(null, m);

		} catch (final Exception e) {

			final String errorMessage = getRootErrorMessage(e);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um erro, atualização não foi possivel",
					errorMessage);
			facesContext.addMessage(null, m);
		}
	}

	public void addPhone() throws Exception {

		try {

			final Phone phone = new Phone();
			phone.setDdd(phoneDdd);
			phone.setNumber(phoneNumber);
			phone.setPhonetype(PhoneType.valueOf(typeOfPhone.toUpperCase()));
			log.info("Tentando adcionar o telefone" + user.getPhones().size());
			user.addPhone(phone);
			userUpdate.update(user);

			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Telefone cadastrado com sucesso",
					"Telefone cadastrado com sucesso");
			facesContext.addMessage(null, m);

		} catch (final Exception e) {

			final String errorMessage = getRootErrorMessage(e);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não foi possivel incluir o telefone",
					errorMessage);
			facesContext.addMessage(null, m);
		}

	}

	public void removePhone(final Phone phoneList) throws Exception {

		try {

			user.removePhone(phoneList);
			userUpdate.update(user);

			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Telefone excluido",
					"Telefone excluido");
			facesContext.addMessage(null, m);

		} catch (final Exception e) {

			final String errorMessage = getRootErrorMessage(e);
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não foi possivel incluir o telefone",
					"Não foi possivel incluir o telefone");
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

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(final FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(final Logger log) {
		this.log = log;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;

	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public UserRegistration getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(final UserRegistration userUpdate) {
		this.userUpdate = userUpdate;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(final String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public UIInput getUiCurrentPassword() {
		return uiCurrentPassword;
	}

	public void setUiCurrentPassword(final UIInput uiCurrentPassword) {
		this.uiCurrentPassword = uiCurrentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public UIInput getUiNewPassword() {
		return uiNewPassword;
	}

	public void setUiNewPassword(final UIInput uiNewPassword) {
		this.uiNewPassword = uiNewPassword;
	}

	public int[] getDdd() {
		return ddd;
	}

	public void setDdd(final int ddd[]) {
		this.ddd = ddd;
	}

	public int getPhoneDdd() {
		return phoneDdd;
	}

	public void setPhoneDdd(final int phoneDdd) {
		this.phoneDdd = phoneDdd;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTypeOfPhone() {
		return typeOfPhone;
	}

	public void setTypeOfPhone(final String typeOfPhone) {
		this.typeOfPhone = typeOfPhone;
	}
}
