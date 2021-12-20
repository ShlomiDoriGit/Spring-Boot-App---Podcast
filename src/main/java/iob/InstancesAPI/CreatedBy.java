package iob.InstancesAPI;

import org.springframework.beans.factory.annotation.Autowired;

import iob.UsersRelatedAPI.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatedBy {
	private UserId userId;

}
