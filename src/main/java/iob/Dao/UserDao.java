package iob.Dao;

import org.springframework.data.repository.PagingAndSortingRepository;

//import org.springframework.data.repository.CrudRepository;

import iob.data.UserEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {

	// TODO
}
