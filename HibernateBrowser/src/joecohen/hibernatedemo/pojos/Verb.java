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
