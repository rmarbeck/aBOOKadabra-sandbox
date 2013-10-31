package com.abookadabra.algo.category;

import java.util.ArrayList;
import java.util.List;

import models.Author;
import models.BookCategory;

import com.abookadabra.utils.AuthorHelper.ScoreType;

public class BookCategoryBrowser {
	private BookCategory pivotCategory;
	private ScoreType typeOfScore;
	private float multiplier;
	private List<Author> authorsOfPivotCategory;
	
	public BookCategoryBrowser(BookCategory category, ScoreType typeOfScore, float multiplier) {
		this.pivotCategory = category;
		this.typeOfScore = typeOfScore;
		this.multiplier = multiplier;
		loadAuthorsFromCategory();
	}
	
	public BookCategoryBrowser(BookCategory category, ScoreType typeOfScore) {
		this(category, typeOfScore, 1);
	}
	
	private BookCategoryBrowser(BookCategory category) {
		this.pivotCategory = category;
	}

	private void loadAuthorsFromCategory() {
		authorsOfPivotCategory = Author.findByCategory(pivotCategory);
	}
	
	public void addDirectlyAttachedAuthorsToContainer(AuthorContainer container) {
		//Logger.debug("Attaching direct authors from "+pivotCategory.name);
		container.addAuthorList(authorsOfPivotCategory, typeOfScore, multiplier);
	}
	
	public boolean hasParentOfLevel(int levelOfParent) {
		if (levelOfParent <= 1) {
			return pivotCategory.parent != null;
		}
		if (pivotCategory.parent != null) {
			BookCategoryBrowser parent = new BookCategoryBrowser(pivotCategory.parent);
			return parent.hasParentOfLevel(levelOfParent-1);
		} else {
			return false;
		}
	}

	public boolean hasChildOfLevel(int levelOfChildren) {
		if (levelOfChildren <= 1) {
			return ! pivotCategory.findChildren().isEmpty();
		}
		if (! pivotCategory.findChildren().isEmpty()) {
			for(BookCategory currentChild : pivotCategory.findChildren()) {
				BookCategoryBrowser child = new BookCategoryBrowser(currentChild);
				if (child.hasChildOfLevel(levelOfChildren-1))
					return true;
			}
		}
		return false;
	}

	public void addAuthorsForParentOfLevel(int parentOfLevel, AuthorContainer container, float newMultiplier) {
		//Logger.debug("Going up from "+pivotCategory.name);
		BookCategoryBrowser categoryBrowserParent = getCurrentBrowserParent(newMultiplier);
		categoryBrowserParent.addAuthorsOrGoHigher(parentOfLevel, container);
	}

	
	public void addAuthorsForChildrenOfLevel(int childrenOfLevel, AuthorContainer container, float newMultiplier) {
		//Logger.debug("Going down from "+pivotCategory.name);
		List<BookCategoryBrowser> categoryBrowserChildren = getCurrentBrowserChildren(newMultiplier);
		forEachBrowserAddAuthors(categoryBrowserChildren, childrenOfLevel, container);
	}
	
	private List<BookCategoryBrowser> getCurrentBrowserChildren(float nextLevelMultiplier) {
		List<BookCategory> categoryChildren = pivotCategory.findChildren();
		List<BookCategoryBrowser> categoryBrowserChildren = new ArrayList<BookCategoryBrowser>();
		for (BookCategory currentCategory : categoryChildren) {
			categoryBrowserChildren.add(createBrowser(currentCategory, nextLevelMultiplier));
		}
		return categoryBrowserChildren;
	}
	
	private BookCategoryBrowser getCurrentBrowserParent(float previousLevelMultiplier) {
		BookCategory categoryParent = pivotCategory.parent;
		return createBrowser(categoryParent, previousLevelMultiplier); 
	}

	private BookCategoryBrowser createBrowser(BookCategory subCategory, float levelMultiplier) {
		return new BookCategoryBrowser(subCategory, typeOfScore, levelMultiplier);
	}

	private static void forEachBrowserAddAuthors(List<BookCategoryBrowser> categoryBrowserChildren, int childrenOfLevel, AuthorContainer container) {
		for (BookCategoryBrowser currentBrowser : categoryBrowserChildren) {
			currentBrowser.addAuthorsOrGoDeeper(childrenOfLevel, container);
		}
	}

	private void addAuthorsOrGoDeeper(int childrenOfLevel, AuthorContainer container) {
		if (shouldAddAuthorsFromCurrentLevel(childrenOfLevel)) {
			addDirectlyAttachedAuthorsToContainer(container);
		} else {
			goDeeperToAddAuthors(childrenOfLevel, container);	
		}
	}
	
	private void addAuthorsOrGoHigher(int parentOfLevel, AuthorContainer container) {
		if (shouldAddAuthorsFromCurrentLevel(parentOfLevel)) {
			addDirectlyAttachedAuthorsToContainer(container);
		} else {
			goHigherToAddAuthors(parentOfLevel, container);	
		}
	}

	private boolean shouldAddAuthorsFromCurrentLevel(int level) {
		return level == 1;
	}

	private void goDeeperToAddAuthors(int childrenOfLevel, AuthorContainer container) {
		addAuthorsForChildrenOfLevel(childrenOfLevel-1, container, multiplier);
	}
	
	private void goHigherToAddAuthors(int parentOfLevel, AuthorContainer container) {
		addAuthorsForParentOfLevel(parentOfLevel-1, container, multiplier);
	}
}
