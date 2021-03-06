package hu.neuron.java.common.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = -1265516570893529965L;

	private Long id;
	private String userName;

	private String password;

	private List<RoleDTO> roles;

	public UserDTO() {
	}

	public UserDTO(Long id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + ", password="
				+ password + "]";
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

}
