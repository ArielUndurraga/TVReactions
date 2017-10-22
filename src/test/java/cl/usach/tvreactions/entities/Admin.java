package cl.usach.tvreactions.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="admin")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM a")
public class Admin implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="admin_id", unique=true, nullable = false)
	private int adminId;
	
	@Column(name="admin_user", nullable=false, length=45)
	private String adminUser;
	
	@Column(name="admin_pass", nullable=false, length=45)
	private String adminPass;
	
	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;
	
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
}
