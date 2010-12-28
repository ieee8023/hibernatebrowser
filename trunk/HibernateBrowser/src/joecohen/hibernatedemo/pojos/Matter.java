package joecohen.hibernatedemo.pojos;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Matter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private Long matterID;
	
	@Column(nullable = false)
	private String description;
	
	public Matter() {
		
		super();
	}
	
	public Long getMatterID() {
	
		return matterID;
	}
	
	public void setMatterID(Long matterID) {
	
		this.matterID = matterID;
	}
	
	public Matter(Long refid, String desc){
		
		this.description = desc;
		this.matterID = refid;
	}
	
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	@SuppressWarnings("unused")
	private void setId(long id) {
		
		this.id = id;
	}
	
	public long getId() {
		
		return id;
	}	
	
	@Override
	public String toString(){
		
		return this.description + " (" + this.matterID + ")";
	}
}
