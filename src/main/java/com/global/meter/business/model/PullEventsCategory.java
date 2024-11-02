package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "pull_events_category")
public class PullEventsCategory implements Serializable {

	private static final long serialVersionUID = -1888735372262635883L;

	@PrimaryKey
 	private String owner_name;
    private Date created;
    private Date modified;
    private String source;
    private String created_by;
    private String updated_by;
    private Set<String> critical_event_list;
    private Set<String> non_critical_event_list;
    
    
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public Set<String> getCritical_event_list() {
		return critical_event_list;
	}
	public void setCritical_event_list(Set<String> critical_event_list) {
		this.critical_event_list = critical_event_list;
	}
	public Set<String> getNon_critical_event_list() {
		return non_critical_event_list;
	}
	public void setNon_critical_event_list(Set<String> non_critical_event_list) {
		this.non_critical_event_list = non_critical_event_list;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	@Override
	public String toString() {
		return "PullEventsCategory [owner_name=" + owner_name + ", created=" + created + ", modified=" + modified
				+ ", source=" + source + ", created_by=" + created_by + ", updated_by=" + updated_by
				+ ", critical_event_list=" + critical_event_list + ", non_critical_event_list="
				+ non_critical_event_list + "]";
	}
	
}
