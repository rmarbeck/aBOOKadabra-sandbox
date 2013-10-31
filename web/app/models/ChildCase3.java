package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

/**
 * Definition of a Child
 */
@SuppressWarnings("serial")
@Entity 
public class ChildCase3 extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;

	@ManyToOne(cascade=CascadeType.PERSIST)
	public ParentCase3 parent;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,ChildCase3> find = new Model.Finder(String.class, ChildCase3.class);
    
    public static List<ChildCase3> findAll() {
        return find.all();
    }

    public static ChildCase3 findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public static List<ChildCase3> findByParentName(String name) {
        return find.fetch("parent").where().eq("parent.name", name).findList();
    }
    
    public String toString() {
        return "ChildCase3(" + name + ")";
    }

}

