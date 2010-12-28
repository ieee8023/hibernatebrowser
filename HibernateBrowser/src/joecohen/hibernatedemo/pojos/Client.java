package joecohen.hibernatedemo.pojos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Client{
	private static final long serialVersionUID = 1L;
	
	public Client() {
		
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;	
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
    private Long custNumber;
	
	@Column(nullable = false)
    private Date dateEntered;

	@org.hibernate.annotations.CollectionOfElements
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@Column(nullable = true)
    private Set<Matter> matter = new HashSet<Matter>();	

    public Client(Long clientID, String name) {
    	
    	this.dateEntered = new Date();
    	this.name = name;
    	this.custNumber = clientID;
	}

	public Long getId() {
		
        return this.id;
    }

    @SuppressWarnings("unused")
	private void setId(Long id) {
    	
        this.id = id;
    }
    
	public void setCustNumber(Long custNumber) {
		
		this.custNumber = custNumber;
	}

	public Long getCustNumber() {
		
		return custNumber;
	}

	public void setName(String name) {
		
		this.name = name;
	}

	public String getName() {
		
		return this.name;
	}

	public void setDateEntered(Date dateEntered) {
		
		this.dateEntered = dateEntered;
	}

	public Date getDateEntered() {
		
		return this.dateEntered;
	}
	
	@Override
	public String toString(){
		
		return this.name + " (" + this.custNumber + ")";
	}

	public void setMatter(Set<Matter> matter) {
		
		this.matter = matter;
	}

	public Set<Matter> getMatters() {
		
		return this.matter;
	}
}