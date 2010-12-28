package joecohen.hibernatedemo.pojos;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class SubSubNoun extends SubNoun{
	
	public SubSubNoun() {
		
		super();
	}
	
	public SubSubNoun(String subSubNoun) {
		
		super(subSubNoun);
	}

	@Override
	public String toString() {
		
		return "SubSubNoun [id=" + getId() + ", subSubNoun=" + getNoun() + "]";
	}
	
	
}
