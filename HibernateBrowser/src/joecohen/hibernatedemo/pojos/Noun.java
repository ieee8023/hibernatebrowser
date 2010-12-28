package joecohen.hibernatedemo.pojos;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Noun {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String noun;
	
	public Noun() {
		
		super();
	}
	
	public Noun(String n) {
		
		super();
		this.noun = n;
	}

	@SuppressWarnings("unused")
	private void setId(long id) {
		
		this.id = id;
	}
	public long getId() {
		
		return this.id;
	}

	public void setNoun(String noun) {
		
		this.noun = noun;
	}

	public String getNoun() {
		
		return this.noun;
	}

	@Override
	public String toString() {
		
		return "Noun [id=" + id + ", noun=" + noun + "]";
	}
}
