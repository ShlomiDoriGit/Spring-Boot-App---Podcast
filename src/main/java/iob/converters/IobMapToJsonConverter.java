package iob.converters;

import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IobMapToJsonConverter implements AttributeConverter<Map<String, Object>, String>{
	private ObjectMapper jackson;
	
	public IobMapToJsonConverter() {
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public String convertToDatabaseColumn(Map<String, Object> mapFromEntity) {
		try {
			return this.jackson
				.writeValueAsString(mapFromEntity);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String json) {
		try {
			return this.jackson
				.readValue(json, Map.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
