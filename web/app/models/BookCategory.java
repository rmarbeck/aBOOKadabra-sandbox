package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

import com.abookadabra.utils.StringHelper;

/**
 * Definition of a BookCategory
 */
@SuppressWarnings("serial")
@Entity 
public class BookCategory extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;
	@Column(length = 10000)
	public String description;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "parents", joinColumns = @JoinColumn(name = "child", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "parent", referencedColumnName = "id"))
	public List<BookCategory> parents;
	
	@ManyToMany(mappedBy = "parents", cascade = CascadeType.ALL)
	public List<BookCategory> children;
	
	private static int MAX_LENGTH_OF_DESCRIPTION_IN_TO_STRING = 10;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,BookCategory> find = new Model.Finder(String.class, BookCategory.class);
    
    public static List<BookCategory> findAll() {
        return find.all();
    }

    public static BookCategory findByName(String name) {
        return find.fetch("children").fetch("parents").where().eq("name", name).findUnique();
    }
    
    public List<BookCategory> findChildren() {
        return find.fetch("parents").where().eq("parent", id).findList();
    }
    
    public static List<BookCategory> findChildren(BookCategory category) {
        return category.findChildren();
    }

    public static BookCategory create(BookCategory newBookCategory) {
    	if (newBookCategory.doesNotExistInDB())
    		newBookCategory.save();
        return newBookCategory;
    }

    public static BookCategory updateIt(BookCategory bookCategoryToUpdate) {
    	if (bookCategoryToUpdate.alreadyExistsInDB())
    		bookCategoryToUpdate.update();
    	return bookCategoryToUpdate;
    }

    public static void remove(BookCategory bookCategoryToRemove) {
    	if (bookCategoryToRemove.alreadyExistsInDB())
    		bookCategoryToRemove.delete();
    }
    
    public static void removeByName(String name) {
    	remove(findByName(name));
    }
    
    private boolean alreadyExistsInDB() {
    	return (findByName(this.name) != null);
    }
    
    private boolean doesNotExistInDB() {
    	return !alreadyExistsInDB();
    }

    
    public String toString() {
    	int numberOfChildren = 0;
    	if (this.parents!=null)
    		numberOfChildren = this.parents.size();
        return "BookCategory(" + name + " - " + StringHelper.truncateWithEndPattern(description, MAX_LENGTH_OF_DESCRIPTION_IN_TO_STRING) +" - " + numberOfChildren + ")";
    }
    

}

