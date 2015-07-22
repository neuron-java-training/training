package hu.neuron.java.service;

import java.util.List;

import hu.neuron.java.service.vo.RoleVO;
import hu.neuron.java.service.vo.UserVO;

public interface UserService {

	public UserVO findUserByName(String name) throws Exception;

	public void registrationUser(UserVO userVO) throws Exception;

	public UserVO setUpRoles(UserVO vo) throws Exception;

	public List<UserVO> getUserList(int i, int pageSize, String sortField,
			int dir, String filter, String filterColumnName);

	public int getRowNumber();

	public List<RoleVO> getRoles();

	public RoleVO getRoleByName(String role);

	public void saveUser(UserVO selectedUser);
}
