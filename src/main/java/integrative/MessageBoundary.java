package integrative;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* JSON version: 
 {
	"message":"Hello World", 
	"user":{
	  "firstName":"Jane", 
	  "lastName":"Smith"
	},
	"critical":false,
	"messageCreationTimestamp":"2021-10-27T11:32:12.331+00:00",
	"magicNumber":7,
	"messageVersion":4.2,
	"status":"SUCCESS",
	"dynamicField":{
	  "x":"y",
	  "y":8,
	  "z":1.2,
	  "t":false,
	  "f":{
	    "f1":"hello"
	  }
	}
 }
*/
public class MessageBoundary {
	private String message;
	private UserBoundary user;
	private Boolean critical;
	private Date messageCreationTimestamp;
	private Long magicNumber;
	private Double messageVersion;
	private StatusEnum status;
	private Map<String, Object> dynamicField;

	public MessageBoundary() {
		this.user = new UserBoundary("Jane", "Smith");
		this.dynamicField = new HashMap<>();
		this.dynamicField.put("innerValue", new Date());
		this.dynamicField.put("innerValue2", "string");
		this.dynamicField.put("innerValue3", true);
		this.dynamicField.put("innerValue4", 1.2);
	}

	public MessageBoundary(String message) {
		this();
		this.message = message;
	}

	public MessageBoundary(
			String message, 
			Boolean critical, 
			Date messageCreationTimestamp, 
			Long magicNumber,
			Double messageVersion, 
			StatusEnum status) {
		this();
		this.message = message;
		this.critical = critical;
		this.messageCreationTimestamp = messageCreationTimestamp;
		this.magicNumber = magicNumber;
		this.messageVersion = messageVersion;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserBoundary getUser() {
		return user;
	}

	public void setUser(UserBoundary user) {
		this.user = user;
	}

	public Boolean getCritical() {
		return critical;
	}

	public void setCritical(Boolean critical) {
		this.critical = critical;
	}

	public Date getMessageCreationTimestamp() {
		return messageCreationTimestamp;
	}

	public void setMessageCreationTimestamp(Date messageCreationTimestamp) {
		this.messageCreationTimestamp = messageCreationTimestamp;
	}

	public Long getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(Long magicNumber) {
		this.magicNumber = magicNumber;
	}

	public Double getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(Double messageVersion) {
		this.messageVersion = messageVersion;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Map<String, Object> getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(Map<String, Object> dynamicField) {
		this.dynamicField = dynamicField;
	}

	
}
