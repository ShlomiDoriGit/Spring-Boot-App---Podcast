package iob.servicesJPA;

import java.util.List;

import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.Dao.UserDao;
import iob.UsersRelatedAPI.UserBoundary;
import iob.converters.UserConverter;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.EnhancedUsersService;
import iob.logic.UsersService;

@Service
public class UserServiceJpa implements EnhancedUsersService {
	private UserDao userDao;
	private String appName;
	private UserConverter userConverter;

	@Autowired
	public UserServiceJpa(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Autowired
	public void setEntityConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Override
	@Transactional
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
		entity = this.userDao.save(entity);
		return this.userConverter.convertToBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminDomain, String adminEmail) {
		throw new RuntimeException("Uninmplemented deprecated operation");
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminDomain, String adminEmail, int page, int size) {
		Direction direction = Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, "userName", "userIdEmail");
		Page<UserEntity> resultPage = this.userDao.findAll(pageable);

		Optional<UserEntity> optionalUser = this.userDao.findById(adminDomain + "@@" + adminEmail);

		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN)) { // Permission check
//				Iterable<UserEntity>  allEntities = this.userDao
//						.findAll();

				return resultPage.stream().map(this.userConverter::convertToBoundary) // Stream<UserBoundary>
						.collect(Collectors.toList()); // List<UserBoundary>

			} else
				throw new RuntimeException("Only user with ADMIN role can get all users");
		} else {
			throw new RuntimeException("Can't find user with domain : " + adminDomain + " and id : " + adminEmail);
		}

	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		Optional<UserEntity> optionalUser = this.userDao.findById(userDomain + "@@" + userEmail);
		if (optionalUser.isPresent()) {

			UserEntity entity = optionalUser.get();

			if (update.getAvatar() != null)
				entity.setAvatar(update.getAvatar());
			try {
				// UserRole temp = UserRole.valueOf(entity.getRole().toString());
				if (update.getRole() != null)
					entity.setRole(UserRole.valueOf(update.getRole().toUpperCase()));

			} catch (IllegalArgumentException ex) {
			}
			if (update.getUsername() != null)
				entity.setUserName(update.getUsername());

			entity = this.userDao.save(entity);
			UserBoundary boundary = userConverter.convertToBoundary(entity);
			return boundary;

		}

		else {
			throw new RuntimeException("could not find user");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {

		Optional<UserEntity> optionalUser = this.userDao.findById(userDomain + "@@" + userEmail);

		if (optionalUser.isPresent()) {
			UserEntity entity = optionalUser.get();
			UserBoundary boundary = userConverter.convertToBoundary(entity);
			return boundary;
		} else {
			throw new RuntimeException("Could not find user by space and email: " + userDomain + " , " + userEmail);// NullPointerException
		}

	}

	@Override
	@Transactional
	public void deleteAllUsers(String adminDomain, String adminEmail) {

		Optional<UserEntity> optionalUser = this.userDao.findById(adminDomain + "@@" + adminEmail);
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN))
				this.userDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all users");
		} else
			throw new RuntimeException("Can't find user with space : " + adminDomain + " and id : " + adminEmail);
	}

}
