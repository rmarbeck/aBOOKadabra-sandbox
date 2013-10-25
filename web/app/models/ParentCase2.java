package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

/**
 * Definition of a Parent
 */
@SuppressWarnings("serial")
@Entity 
public class ParentCase2 extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;
	
	@ManyToMany(targetEntity=models.ChildCase2.class, mappedBy = "parents", cascade=CascadeType.ALL)
	public List<ParentCase2> children;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,ParentCase2> find = new Model.Finder(String.class, ParentCase2.class);
    
    public static List<ParentCase2> findAll() {
        return find.all();
    }

    public static ParentCase2 findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }
    
    public String toString() {
        return "Parent(" + name + ")";
    }

}

