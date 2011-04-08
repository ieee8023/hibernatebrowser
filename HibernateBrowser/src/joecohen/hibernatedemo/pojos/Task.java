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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Task{

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
		@Column(nullable = false)
	private Date entered;			
		
		@Column(nullable = true)
	private String description;
	
		@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private Matter matter;
	
		@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private Client customer;
		
		@ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private Time currentTime;		
	
		@org.hibernate.annotations.CollectionOfElements
		@ManyToMany(fetch = FetchType.EAGER)
		@Column(nullable = true)	
	private Set<VerbNounRelation> verbNounRelations = new HashSet<VerbNounRelation>();
	
		@org.hibernate.annotations.CollectionOfElements
		@ManyToMany(fetch = FetchType.EAGER)
		@Column(nullable = true)	
	private Set<Time> times = new HashSet<Time>();	
		
	public Task(){
	}
		
	public Task(Date d){
		this.entered = d;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setCustomer(Client customer) {
		this.customer = customer;
	}
	public Client getCustomer() {
		return customer;
	}
	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setVerbNounRelations(Set<VerbNounRelation> verbNounRelations) {
		this.verbNounRelations = verbNounRelations;
	}
	public Set<VerbNounRelation> getVerbNounRelations() {
		return verbNounRelations;
	}
	public void setMatter(Matter matter) {
		this.matter = matter;
	}
	public Matter getMatter() {
		return matter;
	}
	@Override
	public boolean equals(Object o)
	{
		boolean result = false;
		Task t = (Task)o;
		if (o!=null && o.getClass().equals(Task.class) &&
				(customer==t.getCustomer() || customer.equals(t.getCustomer())) &&
				(description==t.getDescription() || description.equals(t.getDescription())) &&
				(entered==t.getEntered() || entered.equals(t.getEntered())) &&
				(matter==t.getMatter() || matter.equals(t.getMatter())) &&
				(times==t.getTimes() || times.equals(t.getTimes())) &&
				(verbNounRelations==t.getVerbNounRelations() || verbNounRelations.equals(t.getVerbNounRelations())));
			result = true;
		return result;

	}
	public void setTimes(Set<Time> times) {
		this.times = times;
	}
	public Set<Time> getTimes() {
		return times;
	}
	public void setEntered(Date entered) {
		this.entered = entered;
	}
	public Date getEntered() {
		return entered;
	}

	public void setCurrentTime(Time currentTime) {
		this.currentTime = currentTime;
	}

	public Time getCurrentTime() {
		return currentTime;
	}
	
}
