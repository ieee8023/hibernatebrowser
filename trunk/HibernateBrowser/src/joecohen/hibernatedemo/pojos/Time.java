/* 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

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
