import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.List;
import java.util.Map;

import models.BookCategory;
import models.Resonance;

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
public class BookCategoryTest2 {
	
	List<Object> categories;
	List<Object> resonances;
	
	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("bookcategorytest-data.yml");
            resonances = all.get("resonances");
            categories = all.get("bookCategories");
        }
    }
	
	@Test
    public void resonances() {
		Ebean.save(resonances);
		assertThat(Resonance.findAll().size()).isEqualTo(2);
	}

	@Test
    public void addingManuallyCategoriesToExistingOnes() {
		Ebean.save(categories);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		assertThat(Resonance.findAll().size()).isEqualTo(1);
		
		BookCategory categoryRoot = BookCategory.findByName("Root");
		assertThat(categoryRoot.findChildren().size()).isEqualTo(1);
		
		// Adding to Thriller
		BookCategory categoryThriller = BookCategory.findByName("Thriller");
		assertThat(categoryThriller.children.size()).isEqualTo(4);
		BookCategory newAttachedToThriller1 = new BookCategory();
		newAttachedToThriller1.name = "newAttachedToThriller1";
		newAttachedToThriller1.parent = categoryThriller;
		BookCategory newAttachedToThriller2 = new BookCategory();
		newAttachedToThriller2.name = "newAttachedToThriller2";
		newAttachedToThriller2.parent = categoryThriller;
		
		assertThat(categoryThriller.findChildren().size()).isEqualTo(4);
		
		newAttachedToThriller1.save();
		assertThat(categoryThriller.findChildren().size()).isEqualTo(5);
		newAttachedToThriller2.save();
		assertThat(categoryThriller.findChildren().size()).isEqualTo(6);
		
		
		newAttachedToThriller2.delete();
		assertThat(categoryThriller.findChildren().size()).isEqualTo(5);
		
		BookCategory.findByName("Thriller").delete();

		assertThat(categoryRoot.findChildren().size()).isEqualTo(0);
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		assertThat(Resonance.findAll().size()).isEqualTo(1);
	}
	
	@Test
    public void removingCategoryCheckingResonance() {
		Ebean.save(categories);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		assertThat(Resonance.findAll().size()).isEqualTo(1);
		Ebean.save(resonances);
		assertThat(Resonance.findAll().size()).isEqualTo(2);
		
		BookCategory categorySerial = BookCategory.findByName("Serial");
		assertThat(categorySerial.resonances.size()).isEqualTo(1);
		BookCategory.findByName("Thriller").delete();

		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		assertThat(Resonance.findAll().size()).isEqualTo(2);
	}
	
	@Test
    public void removingResonanceCheckingCategories() {
		Ebean.save(categories);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		assertThat(Resonance.findAll().size()).isEqualTo(1);
		Ebean.save(resonances);
		assertThat(Resonance.findAll().size()).isEqualTo(2);
		
		BookCategory categorySerial = BookCategory.findByName("Serial");
		assertThat(categorySerial.resonances.size()).isEqualTo(1);
		categorySerial.resonances.get(0).delete();

		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		assertThat(Resonance.findAll().size()).isEqualTo(1);
	}

	@Test
    public void getCategoriesForAResonance() {
		Ebean.save(categories);
		
		BookCategory categorySerial = BookCategory.findByName("Serial");
		assertThat(categorySerial.resonances.size()).isEqualTo(1);

		Resonance resonanceOfSerial = categorySerial.resonances.get(0);
		assertThat(resonanceOfSerial.categories.size()).isEqualTo(1);

		assertThat(resonanceOfSerial.categories.get(0).name).isEqualTo("Serial");
	}
	
	@Test
    public void addingNewResonanceToACategory() {
		Ebean.save(categories);
		
		// Adding to Thriller
		BookCategory categorySerial = BookCategory.findByName("Serial");
		assertThat(categorySerial.resonances.size()).isEqualTo(1);

		Resonance newResonance = new Resonance();
		newResonance.name = "newResonance";
		
		categorySerial.resonances.add(newResonance);
		
		newResonance.save();
		
		assertThat(categorySerial.resonances.size()).isEqualTo(2);

		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		assertThat(Resonance.findAll().size()).isEqualTo(2);
	}
	
	@Test
    public void bookCategory() {
		Ebean.save(categories);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		BookCategory categoryRoot = BookCategory.findByName("Root");
		assertThat(categoryRoot.parent).isNull();
		assertThat(categoryRoot.children.size()).isEqualTo(1);
		assertThat(categoryRoot.findChildren().size()).isEqualTo(1);
		BookCategory categoryThriller = categoryRoot.children.get(0);
		assertThat(categoryThriller.name).isEqualTo("Thriller");
		assertThat(categoryThriller.findChildren().size()).isEqualTo(4);
		BookCategory categoryNoir = BookCategory.findByName("Noir");
		assertThat(categoryNoir.children.size()).isEqualTo(1);
		BookCategory categoryNeoPolar = categoryNoir.children.get(0);
		assertThat(categoryNeoPolar.children.size()).isEqualTo(0);
	}

}