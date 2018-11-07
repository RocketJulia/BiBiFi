package de.tz.demo.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bankId;

    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private int creditline;

	public int getCreditline() {
		return creditline;
	}

	public void setCreditline(int creditline) {
		this.creditline = creditline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}


    
}
