package integrative.converters;

import org.springframework.stereotype.Component;

import integrative.UsersRelatedAPI.UserBoundary;
import integrative.UsersRelatedAPI.UserId;
import integrative.data.UserEntity;
import integrative.data.UserRole;

@Component
public class UserConverter {

	public UserEntity convertToEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		// Default
		entity.setUserId_Domain("2022a.demo");
		entity.setUserId_email("user@demo.com");
		entity.setRole(UserRole.PLAYER);
		entity.setUsername("demo");
		entity.setAvatar("demo");

		if (boundary.getUserId() != null) {
			if (boundary.getUserId().getDomain() != null) {
				entity.setUserId_Domain(boundary.getUserId().getDomain());
			}
			if (boundary.getUserId().getEmail() != null) {
				entity.setUserId_email(boundary.getUserId().getEmail());
			}
		}
		
		if (boundary.getRole() != null) {
			entity.setRole(UserRole.valueOf(boundary.getRole()));
		}
		
		if (boundary.getUsername() != null) {
			entity.setUsername(boundary.getUsername());
		}
		
		if (boundary.getAvatar() != null) {
			entity.setAvatar(boundary.getAvatar());
		}

		return entity;
	}

	public UserBoundary convertToBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setUserId(new UserId(entity.getUserId_Domain(), entity.getUserId_email()));
		boundary.setRole(entity.getRole().name());
		boundary.setUsername(entity.getUsername());
		boundary.setAvatar(entity.getAvatar());
		return boundary;
	}

}
