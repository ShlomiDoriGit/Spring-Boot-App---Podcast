package iob.converters;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import iob.Boundaries.Location;
import iob.InstancesAPI.CreatedBy;
import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;
import iob.UsersRelatedAPI.UserId;
import iob.data.InstanceEntity;

@Component
public class InstanceConverter {

	private String appName;
	
	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}
	
	public InstanceEntity convertToEntity(InstanceBoundary boundary) {
		InstanceEntity entity = new InstanceEntity();
		// default
		entity.setInsanceId(new InstanceId());
		entity.getInsanceId().setDomain(appName);
		entity.getInsanceId().setId("352");
		entity.setType("dummyInstanceType");
		entity.setCreatedByUserDomain(appName);
		entity.setCreatedByUserEmail("user2@@demo.com");
		entity.setLat(32.115139);
		entity.setLng(34.817804);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("key1", "can be set to any value you wish");
		m.put("key2","you can also name the attributes any name you like");
		m.put("key3", 6.2);
		m.put("key4", false);
		
		entity.setInstanceAttributes(m);
		if (boundary.getInstanceId() != null) {
			if (boundary.getInstanceId().getDomain() != null) {
				entity.getInsanceId().setDomain(appName);
			}
			if (boundary.getInstanceId().getId() != null) {
				entity.getInsanceId().setId(boundary.getInstanceId().getId());
			}

		}
		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		}
		if (boundary.getName() != null) {
			entity.setName(boundary.getName());
		}
		if (boundary.getActive() != null) {
			entity.setActive(boundary.getActive());
		}
		if (boundary.getCreatedTimestamp() != null) {
			entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		}	
		if (boundary.getCreatedBy() != null && boundary.getCreatedBy().getUserId() != null) {
			if (boundary.getCreatedBy().getUserId().getDomain() != null) {
				entity.setCreatedByUserDomain(boundary.getCreatedBy().getUserId().getDomain());
			}
			if (boundary.getCreatedBy().getUserId().getEmail() != null) {
				entity.setCreatedByUserEmail(boundary.getCreatedBy().getUserId().getEmail());
			}
			if (boundary.getLocation() != null) {
				if (boundary.getLocation().getLat() != null) {
					entity.setLat(boundary.getLocation().getLat());
				}
				if (boundary.getLocation().getLng() != null) {
					entity.setLng(boundary.getLocation().getLng());
				}
				if (boundary.getInstanceAttributes() != null) {
					entity.setInstanceAttributes(boundary.getInstanceAttributes());
				}
			}
		}
		return entity;
	}

	public InstanceBoundary convertToBoundary(InstanceEntity entity) {
		InstanceBoundary boundary = new InstanceBoundary();
		boundary.setInstanceId(new InstanceId(this.appName, entity.getInsanceId().getId()));
		boundary.setType(entity.getType());
		boundary.setName(entity.getName());
		boundary.setActive(entity.getActive());
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		boundary.setCreatedBy(new CreatedBy(new UserId(entity.getCreatedByUserDomain(), entity.getCreatedByUserEmail())));
		boundary.setLocation(new Location(entity.getLat(), entity.getLng()));
		boundary.setInstanceAttributes(entity.getInstanceAttributes());
		return boundary;
	}

}
