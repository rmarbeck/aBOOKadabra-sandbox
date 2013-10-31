package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

/**
 * Definition of an Author
 */
@Entity 
public class Author extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@Column(unique=true)
	@Embedded
	public AuthorPrimaryKey fullname;
	
	@OneToMany(mappedBy= "author", cascade = CascadeType.ALL)
	public List<AuthorAlias> alias;
	
	@OneToMany(mappedBy= "author", cascade = CascadeType.ALL)
	public List<BookCategoryOwnership> categories;
	
	@OneToOne(mappedBy= "author", cascade = CascadeType.ALL)
	public RecommendationScore score;

    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,Author> find = new Model.Finder(String.class, Author.class);
    
    public static List<Author> findAll() {
        return find.all();
    }

    public static Author findByName(String name) {
        return find.fetch("score").where().eq("fullname.name", name).findUnique();
    }

    public static List<Author> findByCategory(BookCategory category) {
        return find.fetch("score").where().eq("categories.category.id", category.id).findList();
    }
    
    public static Author create(Author newAuthor) {
    	if (newAuthor.doesNotExistInDB())
    		newAuthor.save();
        return newAuthor;
    }

    public static Author updateIt(Author authorToUpdate) {
    	if (authorToUpdate.alreadyExistsInDB())
    		authorToUpdate.update();
    	return authorToUpdate;
    }

    public static void remove(Author authorToRemove) {
    	if (authorToRemove.alreadyExistsInDB())
    		authorToRemove.delete();
    }
    
    public static void removeByName(String name) {
    	remove(findByName(name));
    }
    
    private boolean alreadyExistsInDB() {
    	return (findByName(this.fullname.name) != null);
    }
    
    private boolean doesNotExistInDB() {
    	return !alreadyExistsInDB();
    }

    
    public String toString() {
        return "Author(" + fullname.firstname + " " + fullname.name +")";
    }
    

}

