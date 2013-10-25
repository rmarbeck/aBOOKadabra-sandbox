package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * Definition of a Parent
 */
@SuppressWarnings("serial")
@Entity 
public class ParentCase1 extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,ParentCase1> find = new Model.Finder(String.class, ParentCase1.class);
    
    public static List<ParentCase1> findAll() {
        return find.all();
    }

    public static ParentCase1 findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }
    
    public String toString() {
        return "Parent(" + name + ")";
    }

}

