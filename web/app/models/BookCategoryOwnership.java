package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.Logger;
import play.db.ebean.Model;

/**
 * Definition of a BookCategoryOwnership
 */
@Entity
public class BookCategoryOwnership extends Model {
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;

	@OneToOne(cascade = CascadeType.PERSIST)
	public BookCategory category;

	public int percentage;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	public Author author;
	
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,BookCategoryOwnership> find = new Model.Finder(String.class, BookCategoryOwnership.class);
    
    public static List<BookCategoryOwnership> findAll() {
        return find.all();
    }

    public static List<Author> findAuthorsByCategory(BookCategory category) {
    	List<BookCategoryOwnership> categoriesMatching = findByCategory(category);
    	List<Author> authors = new ArrayList<Author>();
    	for(BookCategoryOwnership currentCategory: categoriesMatching) {
    		authors.add(currentCategory.author);
    	}
        return authors;
    }
    
    public static List<BookCategoryOwnership> findByCategory(BookCategory category) {
    	Logger.debug("Category is " + category.id);
        return find.where().eq("category.id", category.id).findList();
    }
    
}

