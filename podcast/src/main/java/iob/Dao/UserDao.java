package iob.Dao;


import org.springframework.data.repository.PagingAndSortingRepository;

import iob.UsersRelatedAPI.UserId;

//import org.springframework.data.repository.CrudRepository;

import iob.data.UserEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, UserId> {

	
//	public List<UserEntity> findUserByUserDomainUserEmailId(
//			@Param("userIdDomain") String userDomain,
//			@Param("userIdEmail") String userEmail);
}
