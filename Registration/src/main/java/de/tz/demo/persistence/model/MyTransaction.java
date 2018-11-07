package de.tz.demo.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class MyTransaction {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String userEmail;
	
	private String transactionstring;

	public String getTransactionString() {
		return transactionstring;
	}

	public void setTransactionString(String transactionString) {
		this.transactionstring = transactionString;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String useremail) {
		this.userEmail = useremail;
	}
	
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyTransaction transaction = (MyTransaction) obj;
        if (!id.equals(transaction.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Transaction [id=").append(id).append(", useremail=").append(userEmail).append(", transactionstring=").append(transactionstring).append("]");
        return builder.toString();
    }

}
