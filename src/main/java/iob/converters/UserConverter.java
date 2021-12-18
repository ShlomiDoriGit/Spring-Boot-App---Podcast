package iob.converters;

import org.springframework.stereotype.Component;


import iob.UsersRelatedAPI.UserBoundary;
import iob.UsersRelatedAPI.UserId;
import iob.data.UserEntity;
import iob.data.UserRole;

@Component
public class UserConverter {

	public UserEntity convertToEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		// Default
//		entity.setUserIdDomain("2022a.demo");
//		entity.setUserIdEmail("user@demo.com");
		entity.getUserId().setDomain("2022a.demo");
		entity.getUserId().setEmail("user@demo.com");
		entity.setRole(UserRole.PLAYER);
		entity.setUserName("demo");
		entity.setAvatar("demo");

		if (boundary.getUserId() != null) {
			if (boundary.getUserId().getDomain() != null) {
				entity.getUserId().setDomain(boundary.getUserId().getDomain());
			}
			if (boundary.getUserId().getEmail() != null) {
				entity.getUserId().setEmail(boundary.getUserId().getEmail());
			}
		}
		
		if (boundary.getRole() != null) {
			entity.setRole(UserRole.valueOf(boundary.getRole()));
		}
		
		if (boundary.getUsername() != null) {
			entity.setUserName(boundary.getUsername());
		}
		
		if (boundary.getAvatar() != null) {
			entity.setAvatar(boundary.getAvatar());
		}

		return entity;
	}

	public UserBoundary convertToBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setUserId(new UserId(entity.getUserId().getDomain(), entity.getUserId().getEmail()));
		boundary.setRole(entity.getRole().name());
		boundary.setUsername(entity.getUserName());
		boundary.setAvatar(entity.getAvatar());
		return boundary;
	}


}
