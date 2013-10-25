package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

/**
 * Definition of a Child
 */
@SuppressWarnings("serial")
@Entity 
public class ChildCase2 extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;

	@ManyToMany(cascade=CascadeType.ALL)
	public List<ParentCase2> parents;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,ChildCase2> find = new Model.Finder(String.class, ChildCase2.class);
    
    public static List<ChildCase2> findAll() {
        return find.all();
    }

    public static ChildCase2 findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public static List<ChildCase2> findByParentName(String name) {
        return find.fetch("parents").where().eq("parents.name", name).findList();
    }
    
    public String toString() {
        return "Parent(" + name + ")";
    }

}

