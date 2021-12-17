package iob.logic;

import java.util.List;

import iob.UsersRelatedAPI.UserBoundary;

public interface UsersService {

	public UserBoundary createUser(UserBoundary user);

	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update);

	public UserBoundary login(String userDomain, String userEmail);

	@Deprecated
	public List<UserBoundary> getAllUsers(String adminDomain, String adminEmail);

	public void deleteAllUsers(String adminDomain, String adminEmail);

}
