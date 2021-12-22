package iob.logic;

import java.util.List;

import iob.UsersRelatedAPI.UserBoundary;

public interface EnhancedUsersService extends UsersService {

	public List<UserBoundary> getAllUsers(String adminDomain, String adminEmail, int page, int size);

}
