package integrative.logic;

import java.util.List;

import integrative.UsersRelatedAPI.UserBoundary;

public interface InstancesService {

	public UserBoundary createUser(UserBoundary user);
	
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update);

	public UserBoundary login(String userSpace, String userEmail);

	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail);
	
	public void deleteAllUsers(String adminSpace, String adminEmail);
}
