package iob.Dao;

import org.springframework.data.repository.CrudRepository;

import iob.data.UserEntity;

public interface UserDao extends CrudRepository<UserEntity, String> {
	// TODO
}
