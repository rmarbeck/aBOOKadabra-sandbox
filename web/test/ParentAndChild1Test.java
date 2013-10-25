import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.BookCategory;
import models.ChildCase1;
import models.ParentCase1;
import models.ChildCase2;
import models.ParentCase2;

import org.fest.assertions.Fail;
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
public class ParentAndChild1Test {
	
	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
	
	

	@Test
    public void ManualAddingAndRemoveCase1Test() {
		// Nothing is supposed to exist
		assertThat(ParentCase1.findAll().size()).isEqualTo(0);
		assertThat(ChildCase1.findAll().size()).isEqualTo(0);
		
		ChildCase1 child1 = new ChildCase1();
		child1.name = "junior0";
		child1.save();
		child1.name = "junior";
		child1.update();
		
		assertThat(ChildCase1.findAll().size()).isEqualTo(1);
		
		ParentCase1 parent1 = new ParentCase1();
		parent1.name = "senior";
		Exception saveFails = null;
		try {
			parent1.save();
		} catch (RuntimeException e) {
			saveFails = new Exception(e);
		}
		assertThat(saveFails).isNotNull();
		
		child1.parents = new ArrayList<ParentCase1>();
		
		child1.parents.add(parent1);

		child1.update();
		
		assertThat(ParentCase1.findAll().size()).isEqualTo(1);
		parent1.delete();
		child1.name = "tata";
		
		child1.update();
		child1.delete();
		
	}

	@Test
    public void ManualAddingAndRemoveCase2WithoutRenamingTest2TX() {
		// Nothing is supposed to exist
		assertThat(ParentCase2.findAll().size()).isEqualTo(0);
		assertThat(ChildCase2.findAll().size()).isEqualTo(0);
		
		ParentCase2 parent1 = new ParentCase2();
		parent1.name = "senior";
		parent1.save();
		assertThat(ParentCase2.findAll().size()).isEqualTo(1);
		
		ChildCase2 child1 = new ChildCase2();
		child1.name = "junior0";
		child1.parents = new ArrayList<ParentCase2>();
		child1.parents.add(parent1);
		child1.save();
		assertThat(ChildCase2.findAll().size()).isEqualTo(1);
		
		Ebean.beginTransaction();  
		try {  
			child1.delete();
			assertThat(ChildCase2.findAll().size()).isEqualTo(0);
			assertThat(ParentCase2.findAll().size()).isEqualTo(1);
			parent1.delete();
			assertThat(ParentCase2.findAll().size()).isEqualTo(0);
		  
		    Ebean.commitTransaction();  
		      
		} finally {  
		    Ebean.endTransaction();  
		}  
	
	}
	
	@Test
    public void ManualAddingAndRemoveCase2WithRenamingParentBeforeLikingToAChildTest2TX() {
		// Nothing is supposed to exist
		assertThat(ParentCase2.findAll().size()).isEqualTo(0);
		assertThat(ChildCase2.findAll().size()).isEqualTo(0);
		
		ParentCase2 parent1 = new ParentCase2();
		parent1.name = "senior";
		parent1.save();
		parent1.name = "senior2";
		parent1.update();
		assertThat(ParentCase2.findAll().size()).isEqualTo(1);

		ChildCase2 child1 = new ChildCase2();
		child1.name = "junior0";
		child1.save();
		List<ParentCase2> parentsOfChild1 = new ArrayList<ParentCase2>();
		parentsOfChild1.add(ParentCase2.findByName("senior2"));
		child1.parents = parentsOfChild1;
		child1.update();
		assertThat(ChildCase2.findAll().size()).isEqualTo(1);
		child1.name = "junior2";
		child1.update();
		
		assertThat(ChildCase2.findByParentName("senior2").get(0).name).isEqualTo("junior2");
		Ebean.beginTransaction();  
		try {  
			parent1.delete();
			assertThat(ParentCase2.findAll().size()).isEqualTo(0);

			assertThat(ChildCase2.findAll().size()).isEqualTo(1);
			child1.delete();
			assertThat(ChildCase2.findAll().size()).isEqualTo(0);
		  
		    Ebean.commitTransaction();  
		      
		} finally {  
		    Ebean.endTransaction();  
		}  
		
	}
	
	
	
	
	@Test
    public void ManualAddingAndRemoveCase2WithoutRenamingTest2() {
		// Nothing is supposed to exist
		assertThat(ParentCase2.findAll().size()).isEqualTo(0);
		assertThat(ChildCase2.findAll().size()).isEqualTo(0);
		
		ParentCase2 parent1 = new ParentCase2();
		parent1.name = "senior";
		parent1.save();
		assertThat(ParentCase2.findAll().size()).isEqualTo(1);
		
		ChildCase2 child1 = new ChildCase2();
		child1.name = "junior0";
		child1.parents = new ArrayList<ParentCase2>();
		child1.parents.add(parent1);
		child1.save();
		assertThat(ChildCase2.findAll().size()).isEqualTo(1);
		
		child1.delete();
		assertThat(ChildCase2.findAll().size()).isEqualTo(0);
		assertThat(ParentCase2.findAll().size()).isEqualTo(1);
		parent1.delete();
		assertThat(ParentCase2.findAll().size()).isEqualTo(0);
  
	
	}
	
	@Test
    public void ManualAddingAndRemoveCase2WithRenamingParentBeforeLikingToAChildTest2() {
		// Nothing is supposed to exist
		assertThat(ParentCase2.findAll().size()).isEqualTo(0);
		assertThat(ChildCase2.findAll().size()).isEqualTo(0);
		
		ParentCase2 parent1 = new ParentCase2();
		parent1.name = "senior";
		parent1.save();
		parent1.name = "senior2";
		parent1.update();
		assertThat(ParentCase2.findAll().size()).isEqualTo(1);

		ChildCase2 child1 = new ChildCase2();
		child1.name = "junior0";
		child1.save();
		List<ParentCase2> parentsOfChild1 = new ArrayList<ParentCase2>();
		parentsOfChild1.add(ParentCase2.findByName("senior2"));
		child1.parents = parentsOfChild1;
		child1.update();
		assertThat(ChildCase2.findAll().size()).isEqualTo(1);
		child1.name = "junior2";
		child1.update();
		
		parent1.delete();
		assertThat(ParentCase2.findAll().size()).isEqualTo(0);

		assertThat(ChildCase2.findAll().size()).isEqualTo(1);
		child1.delete();
		assertThat(ChildCase2.findAll().size()).isEqualTo(0);

		
	}
	

}
