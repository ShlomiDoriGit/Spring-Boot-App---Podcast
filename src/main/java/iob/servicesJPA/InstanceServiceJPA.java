package iob.servicesJPA;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.Dao.InstanceDao;
import iob.Dao.UserDao;
import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;
import iob.converters.InstanceConverter;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.EnhancedInstancesService;

@Service
public class InstanceServiceJPA implements EnhancedInstancesService{

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

		Optional<UserEntity> optionalUser = this.userDao.findById(userDomain + "@@" + userEmail);
		if (optionalUser.isPresent())
			if (!(optionalUser.get().getRole().equals(UserRole.MANAGER)))
				throw new RuntimeException("Only a manager can create instance ");// NullPointerException

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
		entity.setInstanceDomain(appName);
		entity.setInstanceId(UUID.randomUUID().toString());
		// Date
		entity.setCreatedTimestamp(new Date());
		entity = this.instanceDao.save(entity);
		return this.instanceConverter.convertToBoundary(entity);

	}

	@Override
	@Transactional
	public InstanceBoundary updateInstance(String userDomain, String userEmail, String instanceDomain,
			String InstanceId, InstanceBoundary update) {

		// Permission check
		Optional<UserEntity> optionalUser = this.userDao.findById(userDomain + "@@" + userEmail);
		if (optionalUser.isPresent())
			if (!(optionalUser.get().getRole().equals(UserRole.MANAGER)))
				throw new RuntimeException("Only a manager can update item ");// NullPointerException

		Optional<InstanceEntity> optionalEntity = this.instanceDao.findById(instanceDomain + "@@" + InstanceId);
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
		Iterable<InstanceEntity> allEntities = this.instanceDao.findAll();

		return StreamSupport.stream(allEntities.spliterator(), false) // get stream from iterable
				.map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());
	}

	
	
	
	
	@Override
	public InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String InstanceDomain,
			String instanceId) {
		
		Optional<UserEntity> optionalUser = this.userDao.findById(userDomain + "@@" + userEmail);
		UserEntity user;
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
			if(user.getRole().equals(UserRole.ADMIN))
				throw new RuntimeException("Only Admin have permission to get instances");
		}else
			throw new RuntimeException("Can't find user with domain : " + userDomain + "and id : "+ userEmail);
		
		Optional<InstanceEntity> optionalEntity = this.instanceDao.findById(InstanceDomain+"@@"+instanceId);
		if(optionalEntity.isPresent()) {
			InstanceEntity entity = optionalEntity.get();
		
			if(entity.getActive())
				return this.instanceConverter.convertToBoundary(entity);
			
			else if(user.getRole().equals(UserRole.MANAGER))
				return  this.instanceConverter.convertToBoundary(entity);
			else
				throw new RuntimeException("Could not find instance " + instanceId);
			
		}else {
			throw new RuntimeException("Could not find instance " + instanceId);// NullPointerException
		}
			
	}
	
	
	@Override
	public void deleteAllInstances(String adminDomain, String adminEmail) {
		
		Optional<UserEntity> optionalUser = this.userDao.findById(adminDomain + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN))
				this.instanceDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all items");
		}else
			throw new RuntimeException("Can't find user with space : " + adminDomain + 
					" and id : " + adminEmail);

	}

	@Override
	public void bindExistingInstanceToExistingChildInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId) {
		InstanceEntity child =  instanceConverter.convertToEntity(getSpecificInstance(user_domain, email, instanceIdBoundary.getDomain(), instanceIdBoundary.getId()));
		InstanceEntity origion = instanceConverter.convertToEntity(getSpecificInstance(user_domain, email, instance_domain, instanceId));
		origion.addChildren(child);
		child.addOrigin(origion);
	}

	@Override
	public List<InstanceBoundary> getAllChildrensOfExistingInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId) {
		InstanceEntity origion =  instanceConverter.convertToEntity(getSpecificInstance(user_domain, email, instanceIdBoundary.getDomain(), instanceIdBoundary.getId()));
		return origion.getchildrens().stream().map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());
	}

	@Override
	public List<InstanceBoundary> getInstanceParents(InstanceId instanceIdBoundary, String user_domain, String email,
			String instance_domain, String instanceId) {
		InstanceEntity child =  instanceConverter.convertToEntity(getSpecificInstance(user_domain, email, instanceIdBoundary.getDomain(), instanceIdBoundary.getId()));
		return child.getOrigins().stream().map(this.instanceConverter::convertToBoundary).collect(Collectors.toList());
	}
	
	

}
