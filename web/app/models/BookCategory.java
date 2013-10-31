package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

import com.abookadabra.utils.StringHelper;
import com.avaje.ebean.annotation.EnumValue;

/**
 * Definition of a BookCategory
 */
@Entity 
public class BookCategory extends Model {
	private static final long serialVersionUID = 1L;

	public enum TypeOfCategory {
        @EnumValue("R")
        Real,
        
        @EnumValue("F")
        Fictive,
    }

	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;
	
	public TypeOfCategory type = TypeOfCategory.Real;
	@Column(length = 10000)
	public String description;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Resonance> resonances;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name = "parent", joinColumns = @JoinColumn(name = "parent", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "child", referencedColumnName = "id"))
	public BookCategory parent;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
	public List<BookCategory> children;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	public List<BookCategoryOwnership> ownership;
	
	private static int MAX_LENGTH_OF_DESCRIPTION_IN_TO_STRING = 10;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,BookCategory> find = new Model.Finder(String.class, BookCategory.class);
    
    public static List<BookCategory> findAll() {
        return find.all();
    }

    public static BookCategory findByName(String name) {
        return find.fetch("children").where().eq("name", name).findUnique();
    }
    
    public List<BookCategory> findChildren() {
    	return BookCategory.findByParentName(name);
    }

    public static List<BookCategory> findByParentName(String name) {
        return find.where().eq("parent.name", name).findList();
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

    @Override
    public void delete() {
    	remove(this);
    }
    
    public static void remove(BookCategory bookCategoryToRemove) {
    	//Recursively and manually remove as there is a bug in ebean
    	if (bookCategoryToRemove.alreadyExistsInDB()) {
    		List<BookCategory> children = bookCategoryToRemove.findChildren();
    		for (BookCategory child: children)
    			remove(child);
    	}
    	bookCategoryToRemove.deleteFromDB();
    }

    
    private void deleteFromDB() {
    	super.delete();
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
        return "BookCategory(" + name + " - " + StringHelper.truncateWithEndPattern(description, MAX_LENGTH_OF_DESCRIPTION_IN_TO_STRING) +")";
    }
    

}

