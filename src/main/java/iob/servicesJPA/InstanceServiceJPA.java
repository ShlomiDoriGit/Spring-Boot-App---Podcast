package iob.servicesJPA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import iob.InstancesAPI.CreationWindow;
import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;
import iob.UsersRelatedAPI.UserId;
import iob.converters.InstanceConverter;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.errors.BadRequestException;
import iob.errors.ForbiddenRequestException;
import iob.errors.NotFoundException;
import iob.logic.EnhancedInstancesServiceWithPagination;

@Service
public class InstanceServiceJPA implements EnhancedInstancesServiceWithPagination {

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
				throw new BadRequestException("Could not create an instance withot user's domail");
			}
			if (userEmail == null || userEmail == "") {
				throw new BadRequestException("Could not create an instance without user's email");
			}
			if (instance == null)
				throw new BadRequestException("Could not create an instance without instance boundary");

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
		throw new BadRequestException("There is no user with domain: " + userDomain + " email: " + userEmail);// NullPointerException

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
			throw new BadRequestException("There is no user with domain: " + userDomain + " email: " + userEmail);// NullPointerException
		}
		if (user.getRole() != UserRole.MANAGER) {
			throw new BadRequestException("Only MANAGER can update Instances");
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
			
			
			if (update.getInstanceAttributes() != null) {
//				entity.setInstanceAttributes(update.getInstanceAttributes());
				for (Entry<String, Object> entry : update.getInstanceAttributes().entrySet()) {
					String key = entry.getKey();
					Object val = entry.getValue();
					try {
					entity.getInstanceAttributes().replace(key, val);
					}
					catch(Exception e) {
						entity.getInstanceAttributes().put(key, val);
					}
				}

				
			}

			entity = this.instanceDao.save(entity);
			return this.instanceConverter.convertToBoundary(entity);

		} else {
			// No update for InstanceId because not found
			throw new iob.errors.NotFoundException("Could not find instance");

		}
	}

	@Override
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail) {
		throw new BadRequestException("Uninmplemented deprecated operation");
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail, int page, int size) {

		Direction direction = Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, "createdTimestamp");
		Page<InstanceEntity> resultPage = this.instanceDao.findAll(pageable);

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(userDomain, userEmail));
		UserEntity user;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			if (user.getRole() == UserRole.ADMIN) {
				throw new ForbiddenRequestException("Admin does not have permission to get instances");
			}
		} else
			throw new BadRequestException("Cannot find the user");

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
				throw new ForbiddenRequestException("Admin does not have permission to get instances");
		} else
			throw new BadRequestException("Cannot find the user");

		Optional<InstanceEntity> optionalEntity = this.instanceDao.findById(new InstanceId(InstanceDomain, instanceId));
		if (optionalEntity.isPresent()) {
			InstanceEntity entity = optionalEntity.get();
			if (user.getRole() == UserRole.PLAYER && entity.getActive() == false) {
				throw new NotFoundException("Instance not found " + instanceId);
			}
			return this.instanceConverter.convertToBoundary(entity);
		} else {
			throw new BadRequestException("Could not find instance " + instanceId);// NullPointerException
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
				throw new ForbiddenRequestException("Only user with ADMIN role can delete all Instance");
		} else
			throw new BadRequestException("Can't find user with domian : " + adminDomain + " and id : " + adminEmail);

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
		child.addParent(origion);
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
				throw new ForbiddenRequestException("Admin does not have permission to get Instance");
		} else
			throw new BadRequestException("Can't find user with domian : " + user_domain + " and id : " + email);

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
				throw new ForbiddenRequestException("Admin does not have permission to get Instance");
		} else
			throw new BadRequestException("Can't find user with domian : " + user_domain + " and id : " + email);

//		InstanceEntity child = instanceConverter
//				.convertToEntity(getSpecificInstance(user_domain, email, instance_domain, instanceId));
//
//		return child.getParents().stream().map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());

		InstanceId childId = new InstanceId(instance_domain, instanceId);
		InstanceEntity child = this.instanceDao.findById(childId).orElse(null);
		if (child == null) {
			throw new NotFoundException(
					"Can't find instance with domian : " + instance_domain + " and id : " + instanceId);
		}

		Iterable<InstanceEntity> parents = child.getParents();
		return (ArrayList<InstanceBoundary>) StreamSupport.stream(parents.spliterator(), false)
				.map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());
	}

//	SPRINT 5

	private List<InstanceBoundary> entitiesToBoundaries(List<InstanceEntity> entities) {
		return entities.stream().map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> searchByName(String name, int size, int page) {
		List<InstanceEntity> entities = this.instanceDao.findByName(name,
				PageRequest.of(page, size, Direction.DESC, "name", "createdTimestamp", "insanceId"));
		return entitiesToBoundaries(entities);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> searchByType(String type, int size, int page) {
		List<InstanceEntity> entities = this.instanceDao.findByType(type,
				PageRequest.of(page, size, Direction.DESC, "type", "createdTimestamp", "insanceId"));
		return entitiesToBoundaries(entities);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> searchByLocation(double lat, double lng, int size, int page) {
		List<InstanceEntity> entities = this.instanceDao.findByLatAndLng(lat, lng,
				PageRequest.of(page, size, Direction.DESC, "lat", "lng", "createdTimestamp", "insanceId"));
		return entitiesToBoundaries(entities);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> searchByCreationWindow(String creationWindow, int size, int page) {
		List<InstanceEntity> entities;
		CreationWindow creationWindowEnum;
		try {
			creationWindowEnum = CreationWindow.valueOf(creationWindow);
		} catch (Exception e) {
			creationWindowEnum = CreationWindow.LAST_30_DAYS;
		}
		
		Date startTimestamp; // NOW - X TIME
		Date endTimestamp = new Date(); //NOW
		
		
		long day = 1000 * 60 * 60 * 24;
		
		switch (creationWindowEnum) {
		case LAST_HOUR:
			startTimestamp = new Date(endTimestamp.getTime() - (day / 24));
			break;
		case LAST_24_HOURS:
			startTimestamp = new Date(endTimestamp.getTime() - day);
			break;
		case LAST_7_DAYS:
			startTimestamp = new Date(endTimestamp.getTime() - (day * 7));
			break;
		case LAST_30_DAYS:
			startTimestamp = new Date(endTimestamp.getTime() - (day * 30));
			break;
		default:
			throw new BadRequestException("There is no Option to find by" + creationWindow); 
		}
		entities = this.instanceDao.findAllByCreatedTimestampBetween(startTimestamp, endTimestamp, PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "insanceId"));
		return entitiesToBoundaries(entities);
	}

//	END OF SPRINT 5 //
}
