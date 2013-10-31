package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

/**
 * Definition of a Resonance
 */
@SuppressWarnings("serial")
@Entity 
public class Resonance extends Model {
	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;

	@Column(length = 10000)
	public String description;
	
	@ManyToMany(mappedBy = "resonances", cascade = CascadeType.ALL)
	public List<BookCategory> categories;
    
    // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,Resonance> find = new Model.Finder(String.class, Resonance.class);
    
    public static List<Resonance> findAll() {
        return find.all();
    }

    public static Resonance findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public static Resonance create(Resonance newResonance) {
    	if (newResonance.doesNotExistInDB())
    		newResonance.save();
        return newResonance;
    }

    public static Resonance updateIt(Resonance resonanceToUpdate) {
    	if (resonanceToUpdate.alreadyExistsInDB())
    		resonanceToUpdate.update();
    	return resonanceToUpdate;
    }

    public static void remove(Resonance resonanceToRemove) {
    	if (resonanceToRemove.alreadyExistsInDB())
    		resonanceToRemove.delete();
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
        return "Resonance(" + name + ")";
    }
    

}

