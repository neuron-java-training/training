package hu.neuron.java.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class CustomLoginModule implements LoginModule {

	private Subject subject;
	private CallbackHandler callbackHandler;
	private boolean authenticated = false;
	private String userName;
	private Collection<RolePrincipal> roles;
	private UserPrincipal userPrincipal;

	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;

	}

	public boolean login() throws LoginException {
		if (callbackHandler == null) {
			throw new LoginException("No callback handler supplied.");
		}
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("Enter user name");
		callbacks[1] = new PasswordCallback("Enter password", true);

		try {
			callbackHandler.handle(callbacks);
			userName = ((NameCallback) callbacks[0]).getName();
			String password = String.valueOf(((PasswordCallback) callbacks[1])
					.getPassword());

			if ((userName == null) || (password == null)) {
				authenticated = false;
				throw new FailedLoginException();
			}

			if (userName.equals("admin") && password.equals("admin")) {
				authenticated = true;
				roles = new ArrayList<>();
				roles.add(new RolePrincipal("user"));
				roles.add(new RolePrincipal("admin"));
				roles.add(new RolePrincipal("manager"));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new LoginException("IOException occured: " + ioe.getMessage());
		} catch (UnsupportedCallbackException ucbe) {
			ucbe.printStackTrace();
			throw new LoginException(
					"UnsupportedCallbackException encountered: "
							+ ucbe.getMessage());
		}

		return authenticated;
	}

	public boolean commit() throws LoginException {
		if (authenticated) {
			userPrincipal = new UserPrincipal(userName);
			subject.getPrincipals().add(userPrincipal);
			subject.getPrincipals().addAll(roles);
			return true;

		} else {
			return false;
		}
	}

	public boolean abort() throws LoginException {
		return false;
	}

	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(userPrincipal);
		subject.getPrincipals().removeAll(roles);
		return true;
	}

}
