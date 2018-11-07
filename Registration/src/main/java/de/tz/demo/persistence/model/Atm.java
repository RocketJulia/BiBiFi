package de.tz.demo.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "atm")
public class Atm {
	
	@Id
	@Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long atmId;

	@Column
	private int  atmNumber;
	
	@Column(nullable = false)
	private int money;
	
	@Column(nullable = false)
	private long bankId;

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getAtmNumber() {
		return atmNumber;
	}

	public void setAtmNumber(int atmNumber) {
		this.atmNumber = atmNumber;
	}

	public long getAtmId() {
		return atmId;
	}

	public void setAtmId(long atmId) {
		this.atmId = atmId;
	}
	
	

	
}
