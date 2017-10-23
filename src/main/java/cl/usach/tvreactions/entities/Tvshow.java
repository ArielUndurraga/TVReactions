package cl.usach.tvreactions.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="tvshow")
@NamedQuery(name="Tvshow.findAll", query="SELECT a FROM Tvshow a")
public class Tvshow implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="tvshow_id", unique=true, nullable = false)
	private int tvshowId;
	
	@Column(name="tvshow_name", nullable=false, length=45)
	private String tvshowName;
	
	@Column(name="tvshow_frequency", nullable=false)
	private int tvshowFrequency;
	
	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;

	public int getTvshowId() {
		return tvshowId;
	}

	public void setTvshowId(int tvshowId) {
		this.tvshowId = tvshowId;
	}

	public String getTvshowName() {
		return tvshowName;
	}

	public void setTvshowName(String tvshowName) {
		this.tvshowName = tvshowName;
	}

	public int getTvshowFrequency() {
		return tvshowFrequency;
	}

	public void setTvshowFrequency(int tvshowFrequency) {
		this.tvshowFrequency = tvshowFrequency;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	

}
