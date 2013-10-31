import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.List;
import java.util.Map;

import models.Author;
import models.AuthorAlias;
import models.AuthorPrimaryKey;
import models.BookCategory;
import models.RecommendationScore;

import org.junit.Before;
import org.junit.Test;

import play.libs.Yaml;

import com.avaje.ebean.Ebean;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class AuthorTest {
	
	List<Object> authors;
	List<Object> scores;
	
	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("authortest-data.yml");
            authors = all.get("authors");
            scores = all.get("score");
        }
    }

	@Test
    public void authorLoading() {
		Ebean.save(authors);
		assertThat(Author.findAll().size()).isEqualTo(2);
		assertThat(Author.findByName("King").fullname.toString()).isEqualTo("Stephen King");
	}
	
	@Test
    public void authorScoreLoading() {
		Ebean.save(authors);
		Ebean.save(scores);
		assertThat(RecommendationScore.findByAuthorName("King").bestseller).isEqualTo(50);
		assertThat(RecommendationScore.findByAuthorName("King").classical).isEqualTo(0);
	}

	@Test
    public void authorDeleting() {
		Ebean.save(authors);
		Author.findByName("King").delete();
		assertThat(Author.findAll().size()).isEqualTo(1);
	}
	
	
	@Test
    public void authorScoreDeleting() {
		Ebean.save(authors);
		Ebean.save(scores);
		RecommendationScore bestSellerScore = RecommendationScore.findByAuthorName("King");
		assertThat(Author.findAll().size()).isEqualTo(2);
		bestSellerScore.delete();
		assertThat(Author.findAll().size()).isEqualTo(2);
		assertThat(RecommendationScore.findByAuthorName("King")).isNull();
	}

	@Test
    public void authorDeletingCheckingScore() {
		Ebean.save(authors);
		Ebean.save(scores);
		assertThat(RecommendationScore.findAll().size()).isEqualTo(4);
		Author.findByName("King").delete();
		assertThat(RecommendationScore.findAll().size()).isEqualTo(3);
	}
	
	
	@Test
    public void authorScoreDeletingAndRecreate() {
		Ebean.save(authors);
		Ebean.save(scores);
		Author king = Author.findByName("King");
		RecommendationScore bestSeller = RecommendationScore.findByAuthorName(king.fullname.name);
		assertThat(RecommendationScore.findAll().size()).isEqualTo(4);
		bestSeller.delete();
		assertThat(Author.findAll().size()).isEqualTo(2);
		assertThat(RecommendationScore.findByAuthorName("King")).isNull();
		
		RecommendationScore bestSeller2 = new RecommendationScore();
		bestSeller2.bestseller = 40;
		bestSeller2.author = king;
		bestSeller2.save();
		
		assertThat(RecommendationScore.findByAuthorName("King")).isNotNull();
		assertThat(RecommendationScore.findByAuthorName("King").bestseller).isEqualTo(40);
	}


	@Test
    public void authorScoreModification() {
		Ebean.save(authors);
		Ebean.save(scores);
		assertThat(RecommendationScore.findByAuthorName("King").bestseller).isEqualTo(50);
		assertThat(RecommendationScore.findByAuthorName("King").classical).isEqualTo(0);
		RecommendationScore bestSeller = RecommendationScore.findByAuthorName("King");
		bestSeller.bestseller = 20;
		bestSeller.update();

		assertThat(RecommendationScore.findByAuthorName("King").bestseller).isEqualTo(20);
	}
	
	@Test
    public void authorCreation() {
		assertThat(Author.findAll().size()).isEqualTo(0);
		Author newAuthor = new Author();
		newAuthor.fullname = new AuthorPrimaryKey("Stephen", "King");
		
		newAuthor.save();
		
		assertThat(Author.findByName("King")).isNotNull();
	}
	
	@Test
    public void authorCreationAndAddingRecommendation() {
		assertThat(Author.findAll().size()).isEqualTo(0);
		Author newAuthor = new Author();
		newAuthor.fullname = new AuthorPrimaryKey("Stephen", "King");
		newAuthor.save();
		
		RecommendationScore bestSeller = new RecommendationScore();
		bestSeller.bestseller = 40;
		bestSeller.classical = 40;
		bestSeller.ico = 40;
		bestSeller.hard = 40;
		bestSeller.save();
		assertThat(RecommendationScore.findByAuthorName(newAuthor.fullname.name)).isNull();
		bestSeller.author = newAuthor;
		bestSeller.update();
		assertThat(RecommendationScore.findByAuthorName(newAuthor.fullname.name)).isNotNull();
		
		assertThat(RecommendationScore.findByAuthorName("King")).isNotNull();
		
		RecommendationScore scoreOfKing = RecommendationScore.findByAuthorName("King");
		
		scoreOfKing.bestseller = 20;
		
		scoreOfKing.update();
		
		RecommendationScore.findByAuthorName("King").update();
		assertThat(RecommendationScore.findByAuthorName("King").bestseller).isEqualTo(20);
	}
	
	@Test
	public void aliasCheckTest() {
		Ebean.save(authors);
		assertThat(Author.findByName("King").alias.size()).isEqualTo(1);
		assertThat(Author.findByName("King").alias.get(0).alias).isEqualTo("S King");
	}
	
	@Test
	public void aliasDeletionOfAuthorCheck() {
		Ebean.save(authors);
		assertThat(AuthorAlias.findAll().size()).isEqualTo(1);
		Author.findByName("King").delete();
		assertThat(AuthorAlias.findAll().size()).isEqualTo(0);
	}

	@Test
	public void aliasDeletionCheckAuthor() {
		Ebean.save(authors);
		assertThat(AuthorAlias.findAll().size()).isEqualTo(1);
		assertThat(Author.findAll().size()).isEqualTo(2);
		Author.findByName("King").alias.get(0).delete();
		assertThat(AuthorAlias.findAll().size()).isEqualTo(0);
		assertThat(Author.findAll().size()).isEqualTo(2);
	}
	

	@Test
	public void aliasAddCheckAuthor() {
		Ebean.save(authors);
		assertThat(AuthorAlias.findAll().size()).isEqualTo(1);
		AuthorAlias newAuthorAlias = new AuthorAlias("F Thil");
		
		Author thilliez = Author.findByName("Thilliez");
		thilliez.alias.add(newAuthorAlias);
		
		thilliez.update();
		
		assertThat(AuthorAlias.findAll().size()).isEqualTo(2);
		
		Author.findByName("King").alias.get(0).delete();
		assertThat(AuthorAlias.findAll().size()).isEqualTo(1);
		assertThat(Author.findAll().size()).isEqualTo(2);
	}
}