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
import models.BookCategoryOwnership;
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
public class AuthorAndBookCategoryTest {
	
	List<Object> authors;
	List<Object> categories;
	
	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("author-and-bookcategorytest-data.yml");
            authors = all.get("authors");
            categories = all.get("bookCategories");
        }
    }

	@Test
    public void authorAndCategoriesLoading() {
		Ebean.save(authors);
		Ebean.save(categories);
		assertThat(Author.findAll().size()).isEqualTo(2);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
	}
	
	@Test
    public void findingAuthorByCategory() {
		Ebean.save(authors);
		Ebean.save(categories);
		assertThat(BookCategoryOwnership.findAll().size()).isEqualTo(3);
		assertThat(BookCategory.findByName("Noir")).isNotNull();
		assertThat(Author.findByCategory(BookCategory.findByName("Noir")).size()).isEqualTo(1);
		assertThat(Author.findByCategory(BookCategory.findByName("Noir")).get(0).fullname.name).isEqualTo("King");
	}
	
	@Test
    public void removingAuthorCheckingCategory() {
		Ebean.save(authors);
		Ebean.save(categories);
		assertThat(BookCategoryOwnership.findAll().size()).isEqualTo(3);
		assertThat(BookCategory.findByName("Noir")).isNotNull();
		assertThat(Author.findByCategory(BookCategory.findByName("Noir")).size()).isEqualTo(1);
		assertThat(Author.findByCategory(BookCategory.findByName("Noir")).get(0).fullname.name).isEqualTo("King");
		
		Author.findByCategory(BookCategory.findByName("Noir")).get(0).delete();
		assertThat(BookCategoryOwnership.findAll().size()).isEqualTo(1);
		assertThat(BookCategory.findByName("Noir")).isNotNull();
		assertThat(Author.findByCategory(BookCategory.findByName("Noir")).size()).isEqualTo(0);
	}
	
	@Test
    public void removingCategoryCheckingAuthor() {
		Ebean.save(authors);
		Ebean.save(categories);
		assertThat(Author.findAll().size()).isEqualTo(2);
		assertThat(BookCategoryOwnership.findAll().size()).isEqualTo(3);
		assertThat(BookCategory.findByName("Noir")).isNotNull();
		BookCategory.findByName("Noir").delete();
		assertThat(Author.findAll().size()).isEqualTo(2);
		assertThat(BookCategory.findAll().size()).isEqualTo(5);
		assertThat(BookCategoryOwnership.findAll().size()).isEqualTo(2);
	}
}