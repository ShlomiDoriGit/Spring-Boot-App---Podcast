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

import iob.ActivitiesAPI.ActivityBoundary;
import iob.ActivitiesAPI.ActivityId;
import iob.Dao.ActivityDao;
import iob.Dao.InstanceDao;
import iob.Dao.UserDao;
import iob.InstancesAPI.InstanceId;
import iob.UsersRelatedAPI.UserId;
import iob.converters.ActivityConverter;
import iob.data.ActivityEntity;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.errors.BadRequestException;
import iob.errors.ForbiddenRequestException;
import iob.errors.NotFoundException;
import iob.logic.EnhancedActivitiesService;

@Service
public class ActivityServiceJPA implements EnhancedActivitiesService {

	private UserDao userDao;
	private InstanceDao instanceDao;
	private ActivityDao activityDao;
	private String appName;
	private ActivityConverter activityConverter;

	public ActivityServiceJPA(UserDao userDao, InstanceDao instanceDao, ActivityDao activityDao) {
		super();
		this.userDao = userDao;
		this.instanceDao = instanceDao;
		this.activityDao = activityDao;
	}

	@Autowired
	public void setConverter(ActivityConverter activityConverter) {
		this.activityConverter = activityConverter;
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Override
	@Transactional
	public ActivityBoundary invokeActivity(ActivityBoundary activity) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(
				activity.getInvokedBy().getUserId().getDomain(), activity.getInvokedBy().getUserId().getEmail()));

		if (optionalUser.isPresent()) {
			if (optionalUser.get().getRole().equals(UserRole.PLAYER) == false)
				throw new ForbiddenRequestException("Only a player can make activities");
		} else
			throw new BadRequestException(
					"Can't find user with domain : " + activity.getInvokedBy().getUserId().getDomain() + "and id : "
							+ activity.getInvokedBy().getUserId().getEmail());

		String newId = UUID.randomUUID().toString();
		activity.setActivityId(new ActivityId(appName, newId));
		activity.setCreatedTimestamp(new Date());

		if (activity.getInstance() == null)
			throw new BadRequestException("Can't invoke activity with null instance");

		else if (activity.getInstance().getInstanceId() == null)
			throw new BadRequestException("Can't invoke activity with null instance id");

		else if (activity.getInstance().getInstanceId().getDomain() == null
				|| activity.getInstance().getInstanceId().getId() == null)
			throw new BadRequestException("Can't invoke activity with null instance id or domain");

		if (activity.getInvokedBy() == null)
			throw new BadRequestException("Can't invoke activity with null user");

		else if (activity.getInvokedBy().getUserId() == null)
			throw new BadRequestException("Can't invoke activity with null user id");

		else if (activity.getInvokedBy().getUserId().getEmail() == null
				|| activity.getInvokedBy().getUserId().getDomain() == null)
			throw new BadRequestException("Can't invoke activity with null user domain or email");

		else if (activity.getType() == null || activity.getType() == "")
			throw new BadRequestException("Activity type is undefiend");

		ActivityEntity entity = this.activityConverter.convertToEntity(activity);
		entity = this.activityDao.save(entity);
		return this.activityConverter.convertToBoundary(entity);

	}

	@Override
	@Transactional(readOnly = true)
	@Deprecated
	public List<ActivityBoundary> getAllActivities(String adminDomain, String adminEmail) {
		throw new BadRequestException("Uninmplemented deprecated operation");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities(String adminDomain, String adminEmail, int page, int size) {
		Direction direction = Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, direction, "createdTimestamp", "activityId");
		Page<ActivityEntity> resultPage = this.activityDao.findAll(pageable);

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(adminDomain, adminEmail));

		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN)) {
				Iterable<ActivityEntity> allEntities = resultPage;

				return ((Streamable<ActivityEntity>) allEntities).stream()
						.map(this.activityConverter::convertToBoundary).collect(Collectors.toList());

			}

			else
				throw new ForbiddenRequestException("Only user with ADMIN role can get all operations");
		} else
			throw new BadRequestException("Can't find user with domain : " + adminDomain + " and id : " + adminEmail);
	}

	@Override
	@Transactional
	public void deleteAllActivities(String adminDomain, String adminEmail) {

		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(adminDomain, adminEmail));
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN))
				this.activityDao.deleteAll();
			else
				throw new ForbiddenRequestException("Only user with ADMIN role can delete all operations");
		} else
			throw new BadRequestException("Can't find user with domain : " + adminDomain + " and id : " + adminEmail);

	}
	
	/*
	 * {"dynamicField":{"command":"upvote"}}
	 * {"dynamicField":{"command":"downvote"}}
	 * 
	 * {"dynamicField":{"command":"listen"}}
	 */

	@Override
	@Transactional
	public Object increasePodcastCommandByOne(ActivityBoundary command) {
		Object operation = command.getActivityAttributes().get("command");
		// when operation is not properly defined
		if (operation == null || !(operation instanceof String)){
			throw new BadRequestException("This operation is not properly defined");
		}
		ActivityId activityId = new ActivityId(command.getActivityId().getDomain(), command.getActivityId().getId());
		ActivityEntity activity = this.activityDao.findById(activityId).orElse(null);
		if (activity == null) {
			throw new NotFoundException("The Podcast did not found");
		}
		InstanceId instanceId = new InstanceId(activity.getActivityInstanceDomain(), activity.getActivityInstanceId());
		InstanceEntity instancePodcast = this.instanceDao.findById(instanceId).orElse(null);
		if (instancePodcast == null) {
			throw new NotFoundException("The Podcast did not found");
		}
		
		
		
		try {
			String operation_key = operation.toString();
			int operation_new_value = (int)activity.getActivityAttributes().get(operation_key) + 1;
			activity.getActivityAttributes().replace(operation_key, operation_new_value);
		} catch (Exception e) {
			throw new BadRequestException("This operation is not properly defined");
		}
		
		
		this.activityDao.save(activity);
		return this.activityConverter.convertToBoundary(activity);
	}
	

}
