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
