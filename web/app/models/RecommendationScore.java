package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

/**
 * Definition of a RecommendationScore
 */
@Entity
public class RecommendationScore extends Model {
	public static final int MAX_SCORE = 50;
	public static final int MIN_SCORE = -50;
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;

	public int bestseller;
	public int classical;
	public int ico;
	public int hard;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	public Author author;
	
	 // -- Queries
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Model.Finder<String,RecommendationScore> find = new Model.Finder(String.class, RecommendationScore.class);
    
    public static List<RecommendationScore> findAll() {
        return find.all();
    }

    public static RecommendationScore findByAuthorName(String name) {
    	return find.fetch("author").where().eq("author.fullname.name", name).findUnique();
    }
	
    public String toString() {
    	return "RecommendationScore ("+bestseller+" - "+classical+" - "+ico+" - "+hard+", for "+author.fullname;
    }
}

