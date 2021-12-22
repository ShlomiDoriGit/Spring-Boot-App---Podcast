package iob.converters;

import java.util.Map;

import iob.UsersRelatedAPI.UserBoundary;
import iob.data.UserEntity;

public interface  EntityConverter {
	
	public UserBoundary toBoundary(UserEntity entity);

	public UserEntity fromBoundary(UserBoundary boundary);
	
    public String fromMapToJson (Map<String, Object> value); // marshalling: Java->JSON
	
	public Map<String, Object> fromJsonToMap (String json); // unmarshalling: JSON->Java

}
