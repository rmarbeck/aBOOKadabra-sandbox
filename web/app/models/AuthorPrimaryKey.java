package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 * Definition of an Author PrimaryKey
 */
@Embeddable
public class AuthorPrimaryKey implements Serializable  {
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Column(name = "NAME", nullable = false)
	public String name;
	@Column(name = "FIRSTNAME", nullable = true)
	public String firstname;
	
	public AuthorPrimaryKey(String firstname, String name) {
		this.name = name;
		this.firstname = firstname;
	}

    public String toString() {
        return firstname + " " + name;
    }
}

