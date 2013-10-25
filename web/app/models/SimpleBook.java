package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

import com.abookadabra.utils.StringHelper;

/**
 * Definition of a SimpleBook
 */
@SuppressWarnings("serial")
@Entity 
public class SimpleBook extends Model {

	@Id
	public Long id;
	
	@Column(unique=true)
	public String isbn;
	@Column(length = 10000)
	public String description;
	public String title;
	public String imageUrl;
	public List<String> authors;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,SimpleBook> find = new Model.Finder(String.class, SimpleBook.class);
    
    public static List<SimpleBook> findAll() {
        return find.all();
    }

    public static SimpleBook findByIsbn(String isbn) {
        return find.where().eq("isbn", isbn).findUnique();
    }

    public static SimpleBook create(SimpleBook newBook) {
    	if (newBook.doesNotExistInDB())
    		newBook.save();
        return newBook;
    }

    public static SimpleBook updateIt(SimpleBook bookToUpdate) {
    	if (bookToUpdate.alreadyExistsInDB())
    		bookToUpdate.update();
    	return bookToUpdate;
    }

    public static void remove(SimpleBook bookToRemove) {
    	if (bookToRemove.alreadyExistsInDB())
    		bookToRemove.delete();
    }
    
    public static void removeByIsbn(String isbn) {
    	remove(findByIsbn(isbn));
    }
    
    private boolean alreadyExistsInDB() {
    	return (findByIsbn(this.isbn) != null);
    }
    
    private boolean doesNotExistInDB() {
    	return !alreadyExistsInDB();
    }

    
    public String toString() {
        return "SimpleBook(" + isbn + " - " + title + " - " + StringHelper.truncateWithEndPattern(description, 30) + ")";
    }
    

}

