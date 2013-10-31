package com.abookadabra.algo.category;

import static com.abookadabra.algo.category.ByCategoryConstants.GO_DOWN_SCORE_MULTIPLIER;
import static com.abookadabra.algo.category.ByCategoryConstants.GO_UP_SCORE_MULTIPLIER;
import static com.abookadabra.algo.category.ByCategoryConstants.NB_OF_AUTHORS_PER_CATEGORY;

import java.util.List;

import play.Logger;
import models.Author;
import models.BookCategory;

import com.abookadabra.utils.AuthorHelper.ScoreType;

public class ByCategoryLevel2 {
	private BookCategory category;
	private List<Author> authorsOfThisCategory;
	private List<Author> bestSellerAuthors;
	private List<Author> classicalAuthors;
	private List<Author> icoAuthors;
	private List<Author> hardAuthors;
	private List<Author> ourChoiceAuthors;
	
	private ByCategoryLevel2(BookCategory category) {
		this.category = category;
	}
	
	public static Object start(BookCategory category) throws Exception {
		return new ByCategoryLevel2(category).start();
	}
	
	private Object start() throws Exception {
		loadAuthorsFromCategory();
		findBestSellerFromCategory();
		findClassicalFromCategory();
		findIcoFromCategory();
		findHardFromCategory();
		findOurChoiceFromCategory();
		return bestSellerAuthors;
	}
	
	private void findHardFromCategory() {
		// TODO Auto-generated method stub
		
	}

	private void findOurChoiceFromCategory() {
		// TODO Auto-generated method stub
		
	}

	private void findIcoFromCategory() {
		// TODO Auto-generated method stub
		
	}

	private void findClassicalFromCategory() {
		// TODO Auto-generated method stub
		
	}

	private void loadAuthorsFromCategory() {
		authorsOfThisCategory = Author.findByCategory(category);
	}
	
	private void findBestSellerFromCategory() {
		AuthorContainer bestSellerContainer = new AuthorContainer(NB_OF_AUTHORS_PER_CATEGORY);
		
		BookCategoryBrowser currentBrowser = new BookCategoryBrowser(category, ScoreType.BestSeller);
		
		currentBrowser.addDirectlyAttachedAuthorsToContainer(bestSellerContainer);
		
		goUpAndAddAuthors(currentBrowser, bestSellerContainer);
		if (! bestSellerContainer.isItFull())
			goDownAndAddAuthors(currentBrowser, bestSellerContainer);
		
		bestSellerAuthors = bestSellerContainer.getListOfAuthors();
	}
	
	private static void goUpAndAddAuthors(BookCategoryBrowser browser, AuthorContainer container) {
		float currentMultiplier = GO_UP_SCORE_MULTIPLIER;
		int deepness = 1;
		while (canFindParentWithAuthorWithBetterRank(browser, deepness, container, currentMultiplier)) {
			browser.addAuthorsForParentOfLevel(deepness++, container, currentMultiplier);
			currentMultiplier = GO_UP_SCORE_MULTIPLIER/deepness;
		}
	}
	
	private static void goDownAndAddAuthors(BookCategoryBrowser browser, AuthorContainer container) {
		float currentMultiplier = GO_DOWN_SCORE_MULTIPLIER;
		int deepness = 1;
		while (canFindChildWithAuthorWithBetterRank(browser, deepness, container, currentMultiplier)) {
			browser.addAuthorsForChildrenOfLevel(deepness++, container, currentMultiplier);
			currentMultiplier = GO_DOWN_SCORE_MULTIPLIER/deepness;
		}
	}
	
	private static void goAndAddAuthors(BookCategoryBrowser browser, AuthorContainer container, float multiplier) {
		float currentMultiplier = multiplier;
		int deepness = 1;
		while (canFindParentWithAuthorWithBetterRank(browser, deepness, container, currentMultiplier)) {
			browser.addAuthorsForParentOfLevel(deepness++, container, currentMultiplier);
			currentMultiplier = GO_DOWN_SCORE_MULTIPLIER/deepness;
		}
	}
	
	private static boolean canFindParentWithAuthorWithBetterRank(BookCategoryBrowser currentBrowser, int levelOfParent, AuthorContainer container, float multiplier) {
		if (!currentBrowser.hasParentOfLevel(levelOfParent))
			return false;
		if (!container.shouldWeTryToAddElementForScoreMultiplierLowerThan(multiplier))
			return false;
		return true;
	}
	
	private static boolean canFindChildWithAuthorWithBetterRank(BookCategoryBrowser currentBrowser, int levelOfChildren, AuthorContainer container, float multiplier) {
		if (!currentBrowser.hasChildOfLevel(levelOfChildren))
			return false;
		if (!container.shouldWeTryToAddElementForScoreMultiplierLowerThan(multiplier))
			return false;
		return true;
	}

}
