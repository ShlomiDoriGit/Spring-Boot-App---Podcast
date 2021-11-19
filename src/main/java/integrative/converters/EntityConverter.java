package integrative.converters;

import java.util.Map;

import integrative.UsersRelatedAPI.UserBoundary;
import integrative.data.UserEntity;

public interface  EntityConverter {
	
	public UserBoundary toBoundary(UserEntity entity);

	public UserEntity fromBoundary(UserBoundary boundary);
	
    public String fromMapToJson (Map<String, Object> value); // marshalling: Java->JSON
	
	public Map<String, Object> fromJsonToMap (String json); // unmarshalling: JSON->Java

}
