package hu.neuron.java.web.servlet;

import hu.neuron.java.common.service.UserService;
import hu.neuron.java.common.vo.UserVO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

/**
 * This servlet handles the message box.
 */
@Component("/RegistrationServlet")
@WebServlet(name = "/RegistrationServlet", urlPatterns = "/RegistrationServlet")
public class RegistrationServlet extends HttpRequestHandlerServlet implements
		HttpRequestHandler {

	@Autowired
	UserService service;

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RegistrationServlet.class);

//	@PreAuthorize("hasRole('USER')")
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");

			if (!password.equals(password2)) {
				request.setAttribute("error", "Password not match");
			} else if (service.findUserByName(username) != null) {
				request.setAttribute("error", "Username exits");
			} else {

				UserVO userVO = new UserVO();

				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				String encPassword = bCryptPasswordEncoder.encode(password);

				userVO.setPassword(encPassword);
				userVO.setUserName(username);

				service.registrationUser(userVO);

				request.setAttribute("msg", "Success");

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.getRequestDispatcher("/public/registration.jsp").forward(
				request, response);
	}

}
