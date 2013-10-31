package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

/**
 * Definition of a Parent
 */
@SuppressWarnings("serial")
@Entity 
public class ParentCase3 extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;
	
	@OneToMany(targetEntity=models.ChildCase3.class, mappedBy = "parent", cascade=CascadeType.ALL)
	public List<ChildCase3> children;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,ParentCase3> find = new Model.Finder(String.class, ParentCase3.class);
    
    public static List<ParentCase3> findAll() {
        return find.all();
    }

    public static ParentCase3 findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public List<ChildCase3> findChildren() {
        return ChildCase3.findByParentName(name);
    }

    public String toString() {
        return "Parent(" + name + ")";
    }

}

