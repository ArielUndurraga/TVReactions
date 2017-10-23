package cl.usach.tvreactions.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="tvchannel")
@NamedQuery(name="Channel.findAll", query="SELECT a FROM Channel a")
public class Channel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="channel_id", unique=true, nullable = false)
	private int channelId;
	
	@Column(name="channel_name", nullable=false, length=45)
	private String channelName;
	
	@Column(name="channel_frequency", nullable=false)
	private int channelFrequency;
	
	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getChannelFrequency() {
		return channelFrequency;
	}

	public void setChannelFrequency(int channelFrequency) {
		this.channelFrequency = channelFrequency;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
