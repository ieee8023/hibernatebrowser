package joecohen.hibernatedemo.pojos;

import java.util.Date;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Time {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false,unique = true)
	private Date startTime;
	
	public Time(Date startTime){
		
		this.startTime = startTime;
	}

	public Date getStartTime() {
		
		return startTime;
	}

	public void setStartTime(Date startTime) {
		
		this.startTime = startTime;
	}

	public Date getEndTime() {
		
		return endTime;
	}

	public void setEndTime(Date endTime) {
		
		this.endTime = endTime;
	}
	
	private Date endTime;
	
	@SuppressWarnings("unused")
	private void setId(long id) {
		
		this.id = id;
	}
	public long getId() {
		
		return this.id;
	}
	
	@Override
	public String toString(){
		long duration = getElapsedTime();
		return "Duration" +" :"+duration/60000 + ":"+(duration/1000);
	}
	
	public boolean isComplete(){
		
		return startTime!=null && endTime!=null;
	}
	
	public Long getElapsedTime(){
		
		if (startTime!=null && endTime==null)
			return new Date().getTime() - startTime.getTime();
		else if (startTime!=null && endTime!=null)
			return endTime.getTime() - startTime.getTime();
		else 
			return new Long(0);
	}
}
