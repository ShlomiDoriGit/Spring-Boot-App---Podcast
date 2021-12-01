package iob.servicesMockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import iob.UsersRelatedAPI.UserBoundary;
import iob.converters.UserConverter;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.UsersService;

@Service
public class UsersServiceMockup implements UsersService {

	private String appName;
	// KeyFormat:
	// <UserIdDomain>"@@"<UserIdEmail>
	private Map<String, UserEntity> users;
	private UserConverter userConverter;

	@Autowired
	public void setEntityConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	@PostConstruct
	public void init() {
		this.users = Collections.synchronizedMap(new HashMap<>());
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Override
	public UserBoundary createUser(UserBoundary user) {
		try {
			@SuppressWarnings("unused")
			UserRole temp = UserRole.valueOf(user.getRole());
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException("could not create user by role: " + user.getRole());
		}
		if (user.getUserId().getEmail() == null || user.getUserId().getEmail() == "")
			throw new RuntimeException("could not create user with no email");

		user.getUserId().setDomain(appName);
		UserEntity entity = this.userConverter.convertToEntity(user);
		this.users.put(entity.getUserIdDomain() + "@@" + entity.getUserIdEmail(), entity);
		// TODO check this line ^
		return this.userConverter.convertToBoundary(entity);
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		UserEntity entity = this.users.get(userDomain + "@@" + userEmail);
		if (entity == null) {
			// have server return status 404 here
			throw new RuntimeException("could not find user by email: " + userEmail);// NullPointerException
		}

		UserBoundary boundary = userConverter.convertToBoundary(entity);
		return boundary;
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserEntity entity = this.users.get(userDomain + "@@" + userEmail);
		if (entity == null) {
			// TODO have server return status 404 here
			throw new RuntimeException("Could not find user");// NullPointerException
		}

		if (update.getAvatar() != null)
			entity.setAvatar(update.getAvatar());
		try {
			UserRole temp = UserRole.valueOf(entity.getRole().toString());
			if (update.getRole() != null)
				entity.setRole(temp);
		} catch (IllegalArgumentException ex) { // Not supposed to happen
			throw new RuntimeException("Cannot convert to userRole");
		}
		if (update.getUsername() != null)
			entity.setUserName(update.getUsername());
		return this.userConverter.convertToBoundary(entity); // previously returned update

	}

	@Override
	public List<UserBoundary> getAllUsers(String adminDomain, String adminEmail) {
		return this.users.values().stream().map(this.userConverter::convertToBoundary).collect(Collectors.toList());
	}

	@Override
	public void deleteAllUsers(String adminDomain, String adminEmail) {
		this.users.clear();
	}

}