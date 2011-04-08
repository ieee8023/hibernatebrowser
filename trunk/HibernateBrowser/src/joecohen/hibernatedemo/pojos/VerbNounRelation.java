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
public class VerbNounRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Verb verb;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Noun noun;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SubNoun subNoun;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SubSubNoun subSubNoun;
		
	@SuppressWarnings("unused")
	private void setId(long id) {
		
		this.id = id;
	}
	
	public long getId() {
		
		return this.id;
	}
	
	public void setVerb(Verb verb) {
		
		this.verb = verb;
	}
	
	public Verb getVerb() {
		
		return this.verb;
	}
	
	public void setNoun(Noun noun) {
		
		this.noun = noun;
	}
	
	public Noun getNoun() {
		
		return this.noun;
	}
	
	public void setSubNoun(SubNoun subNoun) {
		
		this.subNoun = subNoun;
	}
	
	public SubNoun getSubNoun() {
		
		return subNoun;
	}
	
	public void setSubSubNoun(SubSubNoun subSubNoun) {
		
		this.subSubNoun = subSubNoun;
	}
	
	public SubSubNoun getSubSubNoun() {
		
		return this.subSubNoun;
	}	
	
	@Override
	public String toString(){
		
		return verb.getVerb() + " - " + noun.getNoun() + " - " + subNoun.getNoun() + " - " + subSubNoun.getNoun();
	}
}
