package iob.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import iob.InstancesAPI.InstanceId;
import iob.converters.IobMapToJsonConverter;
import lombok.Data;

/*
 * INSTANCES
 * ------------------------------------------------------------------------------------------------------------------------ -----------------------------------------------------------
 * INSTANCES_DOMAIN  | INSTANCES_ID   | TYPE         | NAME         |ACTIVE     | CREATED_TIMESTAMP | CREATED_BY_USER_DOMAIN | CREATED_BY_USER_EMAIL | LAT     | LNG    | INSTANCE_ATRRIBUTES | ORIGIN_ID   |
 * VARCHAR(255)      | VARCHAR(255)   | VARCHAR(255) | VARCHAR(255) | BOOLEAN   | TIMESTAMP         | VARCHAR(255)           | VARCHAR(255)          | DOUBL   | DOUBLE | CLOB                | VARCHAR(255)|
 * <PK>              | <PK>           |              |              |           |                   |                        |                       |         |                              | <FK>        |
 */

/*
 * TYPE: PODCAST, USER
 * 
 */
@Entity
@Table(name = "INSTANCES")
@Data
public class InstanceEntity  {
	
	@EmbeddedId
	private InstanceId insanceId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ACTIVE")
	private boolean active;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTimestamp;
	
	@Column(name = "CREATE_BY_USER_DOMAIN")
	private String createdByUserDomain;
	
	@Column(name = "CREATE_BY_USER_EMAIL")
	private String createdByUserEmail;
	
	@Column(name = "LAT")
	private double lat;
	
	@Column(name = "LNG")
	private double lng;
	
	@Convert(converter = IobMapToJsonConverter.class)
	@Lob
	private Map<String, Object> instanceAttributes;
	/*
	 * | 	USER	
	 * | ?INTERESETS?
	 * 		
	 * |	PODCAST				
	 * | GENRES, AUTHOR/S, ?URL?  
	 * 
	 */
	
//	@ManyToMany(mappedBy="childrens")
	@ManyToMany(fetch = FetchType.LAZY) // Archive
	private Set<InstanceEntity> parents = new HashSet<>();
	/*
	 * | 	USER	
	 * | NOT RELAVANT
	 * 		
	 * |	PODCAST				
	 * | USERS_LISTNERS 
	 * 
	 */
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(
//			name="Instance_Many2Many", 
//			joinColumns={@JoinColumn(name="origin")}, 
//			inverseJoinColumns={@JoinColumn(name="child")})
	@ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY) // Archive
	private Set<InstanceEntity> childrens = new HashSet<>();
	/*
	 * | 	USER	
	 * | PODCAST LISTENING
	 * 		
	 * |	PODCAST				
	 * | NOT RELAVANT 
	 * 
	 */

	
	public boolean getActive() {
		return this.active;
	}
	
	public void addParent(InstanceEntity parent) {
		this.parents.add(parent);
	}
	
	public void addChildren(InstanceEntity children) {
		this.childrens.add(children);
		children.addParent(this);
	}
}
