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

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Verb {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false,unique = true)
	private String verb;

	public Verb() {
		
		super();
	}
	
	public Verb(String s){
		
		super();
		this.verb = s;
	}
	
	public String getVerb() {
		
		return verb;
	}
	public void setVerb(String verb) {
		
		this.verb = verb;
	}
	
	@SuppressWarnings("unused")
	private void setId(long id) {
		
		this.id = id;
	}
	public long getId() {
		
		return id;
	}

	@Override
	public String toString() {
		
		return "Verb [id=" + id + ", verb=" + verb + "]";
	}
	
}
