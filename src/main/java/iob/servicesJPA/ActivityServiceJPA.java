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

import iob.ActivitiesAPI.ActivityBoundary;
import iob.ActivitiesAPI.ActivityId;
import iob.Dao.ActivityDao;
import iob.Dao.InstanceDao;
import iob.Dao.UserDao;
import iob.converters.ActivityConverter;
import iob.data.ActivityEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.ActivitiesService;

@Service
public class ActivityServiceJPA implements ActivitiesService {

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
	public Object invokeActivity(ActivityBoundary activity) {

		Optional<UserEntity> optionalUser = this.userDao.findById(activity.getInvokedBy().getUserId().getDomain() + "@@"
				+ activity.getInvokedBy().getUserId().getEmail());

		if (optionalUser.isPresent()) {
			if (optionalUser.get().getRole().equals(UserRole.PLAYER) == false)
				throw new RuntimeException("Only a player can make activities");
		} else
			throw new RuntimeException(
					"Can't find user with domain : " + activity.getInvokedBy().getUserId().getDomain() + "and id : "
							+ activity.getInvokedBy().getUserId().getEmail());

		String newId = UUID.randomUUID().toString();
		activity.setActivityId(new ActivityId(appName, newId));
		activity.setCreatedTimestamp(new Date());
		if (activity.getInstance() == null)
			throw new RuntimeException("Can't invoke activity with null instance");

		else if (activity.getInstance().getInstanceId() == null)
			throw new RuntimeException("Can't invoke activity with null instance id");

		else if (activity.getInstance().getInstanceId().getDomain() == null
				|| activity.getInstance().getInstanceId().getId() == null)
			throw new RuntimeException("Can't invoke activity with null instance id or domain");

		if (activity.getInvokedBy() == null)
			throw new RuntimeException("Can't invoke activity with null user");

		else if (activity.getInvokedBy().getUserId() == null)
			throw new RuntimeException("Can't invoke activity with null user id");

		else if (activity.getInvokedBy().getUserId().getEmail() == null
				|| activity.getInvokedBy().getUserId().getDomain() == null)
			throw new RuntimeException("Can't invoke activity with null user domain or email");

		ActivityEntity entity = this.activityConverter.convertToEntity(activity);
		entity = this.activityDao.save(entity);
		return this.activityConverter.convertToBoundary(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities(String adminDomain, String adminEmail) {

		Optional<UserEntity> optionalUser = this.userDao.findById(adminDomain + "@@" + adminEmail);
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN)) {
				Iterable<ActivityEntity> allEntities = this.activityDao.findAll();

				return StreamSupport.stream(allEntities.spliterator(), false) // get stream from iterable
						.map(this.activityConverter::convertToBoundary).collect(Collectors.toList());
			}

			else
				throw new RuntimeException("Only user with ADMIN role can get all operations");
		} else
			throw new RuntimeException("Can't find user with domain : " + adminDomain + " and id : " + adminEmail);
	}

	@Override
	public void deleteAllActivities(String adminDomain, String adminEmail) {

		Optional<UserEntity> optionalUser = this.userDao.findById(adminDomain + "@@" + adminEmail);
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN))
				this.activityDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all operations");
		} else
			throw new RuntimeException("Can't find user with domain : " + adminDomain + " and id : " + adminEmail);

	}

}
