package iob.logic;

import java.util.Date;
import java.util.List;
import iob.InstancesAPI.InstanceBoundary;

public interface EnhancedInstancesServiceWithPagging extends EnhancedInstancesService {


	public List<InstanceBoundary> searchByName(
			String name,
			int size,
			int page);

	public List<InstanceBoundary> searchByType(String type,
			int size,
			int page);

	public List<InstanceBoundary> searchByLocation(
			double lat,
			double lng,
			int size,
			int page);

	public List<InstanceBoundary> searchByCreate(
			Date createdTimestamp,
			int size,
			int page);
}
