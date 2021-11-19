package integrative.converters;

import org.springframework.stereotype.Component;

import integrative.InstancesAPI.InstanceBoundary;
import integrative.data.InstanceEntity;


@Component
public class InstanceConverter {
	
	public InstanceEntity convertToEntity(InstanceBoundary boundary) {
		InstanceEntity entity = new InstanceEntity();
		//TODO
		return entity;
	}
	
	public InstanceBoundary convertToBoundary(InstanceEntity entity) {
		InstanceBoundary boundary = new InstanceBoundary();
		//TODO
		return boundary;
	}

}
