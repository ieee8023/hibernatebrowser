package joecohen.hibernatedemo.pojos;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class SubNoun extends Noun{

	public SubNoun(){
		
		super();
	}
	
	public SubNoun(String subnoun){
		
		super(subnoun);
	}
	
	@Override
	public String toString() {
		
		return "SubNoun [id=" + getId() + ", subNoun=" + getNoun() + "]";
	}
}
