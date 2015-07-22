package hu.neuron.java.web.controllers;

import hu.neuron.java.service.facade.UserFacade;
import hu.neuron.java.service.vo.UserVO;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Controller for the login site.
 */
@Component
@ViewScoped
@ManagedBean(name = "registrationController")
public class RegistrationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName = "";

	private String password = "";

	private String password2 = "";

	@Autowired
	UserFacade userFacade;
	
	public String addUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if (!password.equals(getPassword2())) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error!",
						"Password not match"));
				return null;
			} else if (userFacade.findUserAndRolesByName(userName) != null) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error!",
						"Sorry we already have a user with this name"));
				return null;
			}

			UserVO userVO = new UserVO();

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encPassword = bCryptPasswordEncoder.encode(password);

			userVO.setPassword(encPassword);
			userVO.setUserName(userName);

			userFacade.registrationUser(userVO);
		} catch (Exception e) {
		e.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Info", "Registration sucessful you can log in now"));
		return "/pages/unsecure/login.xhtml?faces-redirect=true";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
}
