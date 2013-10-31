package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

/**
 * Definition of a AuthorAlias
 */
@Entity
public class AuthorAlias extends Model {
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;

	public String alias;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	public Author author;
	
	public AuthorAlias(String alias) {
		this.alias = alias;
	}
	
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,AuthorAlias> find = new Model.Finder(String.class, AuthorAlias.class);
    
    public static List<AuthorAlias> findAll() {
        return find.all();
    }

    public static Author findAuthorByAlias(String alias) {
    	return Author.find.where().eq("alias", alias).findUnique();
    }

    public String toString() {
        return alias;
    }
}

