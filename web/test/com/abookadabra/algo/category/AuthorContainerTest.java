package com.abookadabra.algo.category;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.List;
import java.util.Map;

import models.Author;
import models.BookCategory;
import models.RecommendationScore;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.Logger;
import play.libs.Yaml;

import com.abookadabra.utils.AuthorHelper;
import com.abookadabra.utils.AuthorHelper.ScoreType;
import com.abookadabra.utils.amazon.api.models.answerelements.Book;
import com.avaje.ebean.Ebean;

public class AuthorContainerTest {
	static List<Object> authors;
	static List<Object> categories;

	@BeforeClass
    public static void oneTimeSetUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Ebean.find(BookCategory.class).findRowCount() == 0) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("authorcontainertest-data.yml");
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
    public void checkScoreTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		assertThat(RecommendationScore.findAll().size()).isEqualTo(6);

		assertThat(RecommendationScore.findByAuthorName("King").bestseller).isEqualTo(50);
		assertThat(Author.findByName("King").score.bestseller).isEqualTo(50);
		assertThat(Author.findByName("King").score.classical).isEqualTo(40);
		assertThat(Author.findByName("Thilliez").score.bestseller).isEqualTo(40);
		assertThat(Author.findByName("Thilliez").score.classical).isEqualTo(0);
		
    }

    @Test
    public void checkCategoryTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		assertThat(Author.findByCategory(BookCategory.findByName("Thriller")).size()).isEqualTo(6);
    }

    @Test
    public void basicSortingTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		AuthorContainer container = new AuthorContainer(2);
		assertThat(container.isItFull()).isFalse();
		assertThat(container.shouldWeTryToAddElementForScoreMultiplierLowerThan(1)).isTrue();
		container.addAuthor(Author.findByName("King"), ScoreType.BestSeller, 1);
		assertThat(container.size()).isEqualTo(1);
		assertThat(container.isItFull()).isFalse();
		assertThat(container.shouldWeTryToAddElementForScoreMultiplierLowerThan(1)).isTrue();
		container.addAuthor(Author.findByName("Thilliez"), ScoreType.BestSeller, 1);
		assertThat(container.size()).isEqualTo(2);
		assertThat(container.isItFull()).isTrue();
		assertThat(container.shouldWeTryToAddElementForScoreMultiplierLowerThan(1)).isTrue();
		assertThat(container.getListOfAuthors().get(0).fullname.name).isEqualTo("King");
    }

    @Test
    public void sortingBestSellerTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		AuthorContainer container = new AuthorContainer(5);
		container.addAuthor(Author.findByName("King"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Thilliez"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Christie"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Patterson"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Grisham"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Simenon"), ScoreType.BestSeller, 1);
		assertThat(container.getListOfAuthors().get(0).fullname.name).isEqualTo("King");
		assertThat(container.getListOfAuthors().get(4).fullname.name).isEqualTo("Patterson");
		assertThat(container.getListOfAuthors().contains(Author.findByName("Simenon"))).isFalse();
    }
    
    @Test
    public void sortingBestSellerListTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		AuthorContainer container = new AuthorContainer(5);
		container.addAuthorList(Author.findByCategory(BookCategory.findByName("Thriller")), ScoreType.BestSeller, 1);
		assertThat(container.getListOfAuthors().get(0).fullname.name).isEqualTo("King");
		assertThat(container.getListOfAuthors().get(4).fullname.name).isEqualTo("Patterson");
		assertThat(container.getListOfAuthors().contains(Author.findByName("Simenon"))).isFalse();
    }
    
    @Test
    public void sortingBestSellerWithMultiplierTest() {
    	Ebean.save(authors);
		Ebean.save(categories);
		
		AuthorContainer container = new AuthorContainer(5);
		container.addAuthor(Author.findByName("Thilliez"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Christie"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Patterson"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Grisham"), ScoreType.BestSeller, 1);
		container.addAuthor(Author.findByName("Simenon"), ScoreType.BestSeller, 1);
		assertThat(container.getListOfAuthors().get(4).fullname.name).isEqualTo("Simenon");
		assertThat(container.shouldWeTryToAddElementForScoreMultiplierLowerThan(0.5f)).isTrue();
		container.addAuthor(Author.findByName("King"), ScoreType.BestSeller, 0.5f);
		assertThat(container.getListOfAuthors().get(4).fullname.name).isEqualTo("King");
		assertThat(container.getListOfAuthors().contains(Author.findByName("Simenon"))).isFalse();
		assertThat(container.shouldWeTryToAddElementForScoreMultiplierLowerThan(0.5f)).isFalse();
    }
}
