package com.abookadabra.algo.category;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Author;
import models.BookCategory;

import org.junit.BeforeClass;
import org.junit.Test;

import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class AlgoLevel2Test {
	static List<Object> authors;
	static List<Object> categories;

	@BeforeClass
    public static void oneTimeSetUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("algolevel2test-data.yml");
            authors = all.get("authors");
            categories = all.get("bookCategories");
        }
    }

    @Test
    public void loadingTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		assertThat(Author.findAll().size()).isEqualTo(6);
		assertThat(BookCategory.findAll().size()).isEqualTo(7);
    }

    @Test
    public void basicForThrillerTest() throws Exception {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		List<Author> bestSeller = (List<Author>) ByCategoryLevel2.start(BookCategory.findByName("Thriller"));
		assertThat(bestSeller.size()).isEqualTo(5);
		assertThat(bestSeller.get(0).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(1).fullname.name).isEqualTo("Grisham");
		List<String> possibleResultsForRank2and3 = new ArrayList<String>();
		possibleResultsForRank2and3.add("Christie");
		possibleResultsForRank2and3.add("Thilliez");
		assertThat(bestSeller.get(2).fullname.name).isIn(possibleResultsForRank2and3);
		assertThat(bestSeller.get(3).fullname.name).isIn(possibleResultsForRank2and3);
		assertThat(bestSeller.get(4).fullname.name).isEqualTo("Patterson");
    }
    
    @Test
    public void basicForNoirTest() throws Exception {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		List<Author> bestSeller = (List<Author>) ByCategoryLevel2.start(BookCategory.findByName("Noir"));
		assertThat(bestSeller.size()).isEqualTo(5);
		assertThat(bestSeller.get(0).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(1).fullname.name).isEqualTo("Christie");
		assertThat(bestSeller.get(2).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(3).fullname.name).isEqualTo("Grisham");
		List<String> possibleResultsForRank4 = new ArrayList<String>();
		possibleResultsForRank4.add("Christie");
		possibleResultsForRank4.add("Thilliez");
		assertThat(bestSeller.get(4).fullname.name).isIn(possibleResultsForRank4);
    }
    
    @Test
    public void basicForRootTest() throws Exception {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		List<Author> bestSeller = (List<Author>) ByCategoryLevel2.start(BookCategory.findByName("Root"));
		assertThat(bestSeller.size()).isEqualTo(5);
		assertThat(bestSeller.get(0).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(1).fullname.name).isEqualTo("Grisham");
		List<String> possibleResultsForRank2and3 = new ArrayList<String>();
		possibleResultsForRank2and3.add("Christie");
		possibleResultsForRank2and3.add("Thilliez");
		assertThat(bestSeller.get(2).fullname.name).isIn(possibleResultsForRank2and3);
		assertThat(bestSeller.get(3).fullname.name).isIn(possibleResultsForRank2and3);
		assertThat(bestSeller.get(4).fullname.name).isEqualTo("Patterson");
    }
    
    @Test
    public void basicForNeoPolarTest() throws Exception {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		List<Author> bestSeller = (List<Author>) ByCategoryLevel2.start(BookCategory.findByName("Neo Polar"));
		assertThat(bestSeller.size()).isEqualTo(5);
		assertThat(bestSeller.get(0).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(1).fullname.name).isEqualTo("Christie");
		assertThat(bestSeller.get(2).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(3).fullname.name).isEqualTo("Grisham");
		List<String> possibleResultsForRank4 = new ArrayList<String>();
		possibleResultsForRank4.add("Christie");
		possibleResultsForRank4.add("Thilliez");
		assertThat(bestSeller.get(4).fullname.name).isIn(possibleResultsForRank4);
    }
    
    @Test
    public void basicForJudiciaireTest() throws Exception {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		List<Author> bestSeller = (List<Author>) ByCategoryLevel2.start(BookCategory.findByName("Judiciaire"));
		assertThat(bestSeller.size()).isEqualTo(5);
		assertThat(bestSeller.get(0).fullname.name).isEqualTo("King");
		assertThat(bestSeller.get(1).fullname.name).isEqualTo("Grisham");
		List<String> possibleResultsForRank2and3 = new ArrayList<String>();
		possibleResultsForRank2and3.add("Christie");
		possibleResultsForRank2and3.add("Thilliez");
		assertThat(bestSeller.get(2).fullname.name).isIn(possibleResultsForRank2and3);
		assertThat(bestSeller.get(3).fullname.name).isIn(possibleResultsForRank2and3);
		assertThat(bestSeller.get(4).fullname.name).isEqualTo("Patterson");
    }
}
