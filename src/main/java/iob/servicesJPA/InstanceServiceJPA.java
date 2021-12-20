package iob.servicesJPA;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.Dao.InstanceDao;
import iob.Dao.UserDao;
import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;
import iob.UsersRelatedAPI.UserId;
import iob.converters.InstanceConverter;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.EnhancedInstancesService;
import iob.logic.EnhancedInstancesServiceWithPagging;

@Service
public class InstanceServiceJPA implements EnhancedInstancesServiceWithPagging {

	private String appName;
	private UserDao userDao;
	private InstanceDao instanceDao;
	private InstanceConverter instanceConverter;

	@Autowired
	public InstanceServiceJPA(UserDao userDao, InstanceDao instanceDao) {
		super();
		this.userDao = userDao;
		this.instanceDao = instanceDao;
	}

	@Autowired
	public void setConverter(InstanceConverter converter) {
		this.instanceConverter = converter;
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Override
	@Transactional
	public InstanceBoundary createInstance(String userDomain, String userEmail, InstanceBoundary instance) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(userDomain, userEmail));
		if (optionalUser.isPresent()) {

			InstanceEntity entity = this.instanceConverter.convertToEntity(instance);

			if (userDomain == null || userDomain == "") {
				throw new RuntimeException("Could not create an instance withot user's domail");
			}
			if (userEmail == null || userEmail == "") {
				throw new RuntimeException("Could not create an instance without user's email");
			}
			if (instance == null)
				throw new RuntimeException("Could not create an instance without instance boundary");

			// User
			if (instance.getCreatedBy().getUserId().getDomain() == null
					|| instance.getCreatedBy().getUserId().getDomain().equals("")) {
				entity.setCreatedByUserDomain(appName);
			}
			if (instance.getCreatedBy().getUserId().getEmail() == null
					|| instance.getCreatedBy().getUserId().getEmail().equals("")) {
				entity.setCreatedByUserEmail(userEmail);
			}
			// Instance
			entity.getInsanceId().setDomain(appName);
			entity.getInsanceId().setId(UUID.randomUUID().toString());
			// Date
			entity.setCreatedTimestamp(new Date());
			entity = this.instanceDao.save(entity);
			return this.instanceConverter.convertToBoundary(entity);
		}
		throw new RuntimeException("No user found");

	}

	@Override
	@Transactional
	public InstanceBoundary updateInstance(String userDomain, String userEmail, String instanceDomain,
			String InstanceId, InstanceBoundary update) {
		UserEntity user;
		// Find user
		Optional<UserEntity> optinalUser = this.userDao.findById(new UserId(userDomain, userEmail));
		if (optinalUser.isPresent()) {
			user = optinalUser.get();
		} else {
			throw new RuntimeException("No user found");
		}
		if (user.getRole() != UserRole.MANAGER) {
			throw new RuntimeException("Only MANAGER can update Instances");
		}
		Optional<InstanceEntity> optionalEntity = this.instanceDao.findById(new InstanceId(instanceDomain, InstanceId));
		if (optionalEntity.isPresent()) {
			InstanceEntity entity = optionalEntity.get();
			if (userDomain != null && !userDomain.equals("")) {
				entity.setCreatedByUserDomain(userDomain);
			}
			if (userEmail != null && !userDomain.equals("")) {
				entity.setCreatedByUserEmail(userEmail);
			}
			if (update.getType() != null) {
				entity.setType(update.getType());
			}
			if (update.getName() != null) {
				entity.setName(update.getName());
			}
			if (update.getActive() != null) {
				entity.setActive(update.getActive());
			}
			// CreatedTimeStamp don't need to be changed
			if (update.getLocation() != null) {
				if (update.getLocation().getLat() != null) {
					entity.setLat(update.getLocation().getLat());
				}
				if (update.getLocation().getLng() != null) {
					entity.setLng(update.getLocation().getLng());
				}
			}
			if (update.getInstanceAttributes() != null)
				entity.setInstanceAttributes(update.getInstanceAttributes());

			entity = this.instanceDao.save(entity);
			return this.instanceConverter.convertToBoundary(entity);

		} else {
			// No update for InstanceId because not found
			throw new RuntimeException("Could not find instance");

		}
	}

	@Override
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail) {
		throw new RuntimeException("Uninmplemented deprecated operation");
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail, int page, int size) {
		Direction direction = Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, "createdTimestamp", "instanceId");
		Page<InstanceEntity> resultPage = this.instanceDao.findAll(pageable);

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(userDomain, userEmail));
		UserEntity user;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			if (user.getRole().equals(UserRole.ADMIN))
				throw new RuntimeException("Admin does not have permission to get instances");
		} else
			throw new RuntimeException("Cannot find the user");

		Iterable<InstanceEntity> allEntities = resultPage;

		return ((Streamable<InstanceEntity>) allEntities).stream().map(this.instanceConverter::convertToBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String InstanceDomain,
			String instanceId) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(userDomain, userEmail));
		UserEntity user;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			if (user.getRole().equals(UserRole.ADMIN))
				throw new RuntimeException("Admin does not have permission to get instances");
		} else
			throw new RuntimeException("Cannot find the user");

		Optional<InstanceEntity> optionalEntity = this.instanceDao.findById(new InstanceId(InstanceDomain, instanceId));
		if (optionalEntity.isPresent()) {
			InstanceEntity entity = optionalEntity.get();
			if (user.getRole() == UserRole.PLAYER && entity.getActive() == false) {
				throw new RuntimeException("Instance not found " + instanceId);
			}
			return this.instanceConverter.convertToBoundary(entity);
		} else {
			throw new RuntimeException("Could not find instance " + instanceId);// NullPointerException
		}

	}

	@Override
	@Transactional
	public void deleteAllInstances(String adminDomain, String adminEmail) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(adminDomain, adminEmail));
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN))
				this.instanceDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all items");
		} else
			throw new RuntimeException("Can't find user with domian : " + adminDomain + " and id : " + adminEmail);

	}

	@Override
	@Transactional
	public void bindExistingInstanceToExistingChildInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId) {
		InstanceEntity child = instanceConverter.convertToEntity(
				getSpecificInstance(user_domain, email, instanceIdBoundary.getDomain(), instanceIdBoundary.getId()));
		InstanceEntity origion = instanceConverter
				.convertToEntity(getSpecificInstance(user_domain, email, instance_domain, instanceId));
		origion.addChildren(child);
		child.addOrigin(origion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getAllChildrensOfExistingInstance(String user_domain, String email,
			String instance_domain, String instanceId, int page, int size) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(user_domain, email));
		UserEntity user = null;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			if (user.getRole().equals(UserRole.ADMIN))
				throw new RuntimeException("Admin does not have permission to get items");
		} else
			throw new RuntimeException("Can't find user with domian : " + user_domain + " and id : " + email);

		InstanceEntity origion = instanceConverter
				.convertToEntity(getSpecificInstance(user_domain, email, instance_domain, instanceId));

		return origion.getChildrens().stream().map(this.instanceConverter::convertToBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getInstanceParents(String user_domain, String email, String instance_domain,
			String instanceId, int page, int size) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(user_domain, email));
		UserEntity user = null;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			if (user.getRole().equals(UserRole.ADMIN))
				throw new RuntimeException("Admin does not have permission to get items");
		} else
			throw new RuntimeException("Can't find user with domian : " + user_domain + " and id : " + email);

		InstanceEntity child = instanceConverter
				.convertToEntity(getSpecificInstance(user_domain, email, instance_domain, instanceId));

		return child.getOrigins().stream().map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());
	}

	@Override
	public List<InstanceBoundary> searchByName(String name, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InstanceBoundary> searchByType(String type, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InstanceBoundary> searchByLocation(double lat, double lng, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InstanceBoundary> searchByCreate(Date createdTimestamp, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

}
