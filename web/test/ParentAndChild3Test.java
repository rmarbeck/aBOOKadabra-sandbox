import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.ArrayList;
import java.util.List;

import models.ChildCase1;
import models.ChildCase2;
import models.ChildCase3;
import models.ParentCase1;
import models.ParentCase2;
import models.ParentCase3;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ParentAndChild3Test {
	
	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
	
	

	@Test
    public void ManualAddingAndRemoveCase3Test() {
		// Nothing is supposed to exist
		assertThat(ParentCase3.findAll().size()).isEqualTo(0);
		assertThat(ChildCase3.findAll().size()).isEqualTo(0);
		
		ChildCase3 child1 = new ChildCase3();
		child1.name = "junior0";
		child1.save();
		child1.name = "junior";
		child1.update();
		
		assertThat(ChildCase3.findAll().size()).isEqualTo(1);
		
		ParentCase3 parent1 = new ParentCase3();
		parent1.name = "senior";
		
		child1.parent = parent1;

		child1.update();
		
		assertThat(ParentCase3.findAll().size()).isEqualTo(1);
		//parent1.delete();
		
		//assertThat(ChildCase3.findAll().size()).isEqualTo(1);
		child1.name = "tata";
		
		child1.update();
		child1.delete();
		
	}
	
	@Test
    public void ManualAddingAndRemoveCase3Test2() {
		// Nothing is supposed to exist
		assertThat(ParentCase3.findAll().size()).isEqualTo(0);
		assertThat(ChildCase3.findAll().size()).isEqualTo(0);
		
		ChildCase3 child1 = new ChildCase3();
		child1.name = "junior0";
		child1.save();
		child1.name = "junior";
		child1.update();
		
		ChildCase3 orphean = new ChildCase3();
		orphean.name = "orphean";
		orphean.save();
		
		assertThat(ChildCase3.findAll().size()).isEqualTo(2);
		
		ParentCase3 parent1 = new ParentCase3();
		parent1.name = "senior";
		
		child1.parent = parent1;

		child1.update();
		
		assertThat(ParentCase3.findAll().size()).isEqualTo(1);
		
		List<ChildCase3> children = parent1.findChildren();
		
		assertThat(children.size()).isEqualTo(1);
		parent1.delete();
		
		assertThat(ChildCase3.findAll().size()).isEqualTo(1);
		/*child1.name = "tata";
		
		child1.update();
		child1.delete();
		
		assertThat(ParentCase3.findAll().size()).isEqualTo(1);*/
		
	}


}
