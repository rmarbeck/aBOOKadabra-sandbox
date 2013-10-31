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

import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class InitialDataTest {
	static List<Object> authors;
	static List<Object> categories;

	@BeforeClass
    public static void oneTimeSetUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("categoryandauthorsinittest-data.yml");
            categories = all.get("bookCategories");
            authors = all.get("authors");
        }
    }

    @Test
    public void loadingTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		assertThat(Author.findAll().size()).isEqualTo(159);
		assertThat(BookCategory.findAll().size()).isEqualTo(31);
    }
    
    @Test
    public void level2ThrillerTest() throws Exception {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		List<Author> bestSeller = (List<Author>) ByCategoryLevel2.start(BookCategory.findByName("Thriller"));
		assertThat(bestSeller.size()).isEqualTo(5);
		assertThat(bestSeller.get(0).fullname.name).isEqualTo("Larsson");
		List<String> possibleResultsForRank2to4 = new ArrayList<String>();
		possibleResultsForRank2to4.add("Winslow");
		possibleResultsForRank2to4.add("P. Pelecanos");
		possibleResultsForRank2to4.add("Leonard");
		assertThat(bestSeller.get(1).fullname.name).isIn(possibleResultsForRank2to4);
		assertThat(bestSeller.get(2).fullname.name).isIn(possibleResultsForRank2to4);
		assertThat(bestSeller.get(3).fullname.name).isIn(possibleResultsForRank2to4);
		List<String> possibleResultsForRank5 = new ArrayList<String>();
		possibleResultsForRank5.add("Scalese");
		possibleResultsForRank5.add("Hunter");
		assertThat(bestSeller.get(4).fullname.name).isIn(possibleResultsForRank5);
    }
}
