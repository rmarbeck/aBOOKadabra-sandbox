import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.BookCategory;

import org.junit.Before;
import org.junit.Test;

import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ModelsTest {
	
	List<Object> categories;
	
	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
			Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("test-bookcat-and-authors-data.yml");
            categories = all.get("bookCategories");
        }
    }
	
	

	@Test
    public void bookCategoryTestManualAddingAndRemove() {
		// Nothing is supposed to exist
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
		BookCategory category1 = new BookCategory();
		category1.name = "Test1";
		category1.description = "Test1 desc";
		
		// Still nothing
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
		
		category1.save();
		// Now 1 is there
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		assertThat(BookCategory.findByName("Test1").parents.size()).isEqualTo(0);
		
		BookCategory category2 = new BookCategory();
		category2.name = "Test2";
		category2.description = "Test2 desc";
		category2.parents = new ArrayList<BookCategory>();
		category2.parents.add(category1);
		
		category2.save();
		
		assertThat(BookCategory.findByName("Test2").parents.size()).isEqualTo(1);
		
		BookCategory category3 = new BookCategory();
		category3.name = "Test3";
		category3.description = "Test3 desc";
		category3.parents = new ArrayList<BookCategory>();
		category3.parents.add(category1);
		
		category3.save();

		category1.refresh();
		assertThat(category1.children.size()).isEqualTo(2);
		
		assertThat(BookCategory.findByName("Test3").parents.get(0).name).isEqualTo("Test1");
		assertThat(category1.findChildren().size()).isEqualTo(2);
		assertThat(category1.findChildren().get(0).name).isEqualTo("Test2");
		assertThat(category1.findChildren().get(1).name).isEqualTo("Test3");

		assertThat(category1.children.size()).isEqualTo(2);
		assertThat(BookCategory.findByName("Test1").children.size()).isEqualTo(2);
		
		category3.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(2);
		
		//category2.parents.remove(0);
		//category2.update();
		
		category1.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		
		category2.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
	}
	
	@Test
    public void bookCategoryTestRemovingHeadFirst() {
		// Nothing is supposed to exist
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
		BookCategory category1 = new BookCategory();
		BookCategory category2 = new BookCategory();
		BookCategory category3 = new BookCategory();
		category1.name = "Test1";
		category2.name = "Test2";
		category2.parents = new ArrayList<BookCategory>();
		category2.parents.add(category1);
		category3.name = "Test3";
		category3.parents = new ArrayList<BookCategory>();
		category3.parents.add(category1);

		category1.save();
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		category2.save();
		category3.save();
		assertThat(BookCategory.findAll().size()).isEqualTo(3);
		
		category1.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(2);
		category2.delete();
		category3.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
	}
	
	@Test
    public void bookCategoryTestRemovingTailFirst() {
		// Nothing is supposed to exist
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
		BookCategory category1 = new BookCategory();
		BookCategory category2 = new BookCategory();
		BookCategory category3 = new BookCategory();
		category1.name = "Test1";
		category2.name = "Test2";
		category2.parents = new ArrayList<BookCategory>();
		category2.parents.add(category1);
		category3.name = "Test3";
		category3.parents = new ArrayList<BookCategory>();
		category3.parents.add(category1);

		category1.save();
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		category2.save();
		category3.save();
		assertThat(BookCategory.findAll().size()).isEqualTo(3);
		
		category3.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(2);
		category2.delete();
		category1.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
	}
	
	@Test
    public void bookCategoryTestAddingParentAfterFirstSave() {
		// Nothing is supposed to exist
		assertThat(BookCategory.findAll().size()).isEqualTo(0);
		BookCategory category1 = new BookCategory();
		BookCategory category2 = new BookCategory();
		category1.name = "Test1";
		category2.name = "Test2";
		category2.save();
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
		category2.parents = new ArrayList<BookCategory>();
		category2.parents.add(category1);
		category2.update();
		//category1 has been saved
		assertThat(BookCategory.findAll().size()).isEqualTo(2);
		
		category1.delete();
		assertThat(BookCategory.findAll().size()).isEqualTo(1);
	}

	

	@Test
    public void bookCategory() {
		Ebean.save(categories);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
		BookCategory categoryRoot = BookCategory.findByName("Root");
		assertThat(categoryRoot.children.size()).isEqualTo(1);
		BookCategory categoryThriller = categoryRoot.findChildren().get(0);
		assertThat(categoryThriller.name).isEqualTo("Thriller");
		assertThat(categoryThriller.children.size()).isEqualTo(4);
		BookCategory categoryNoir = BookCategory.findByName("Noir");
		assertThat(categoryNoir.children.size()).isEqualTo(1);
	}

}