package com.abookadabra.algo.category;

import static com.abookadabra.utils.AuthorHelper.getScore;

import java.util.List;

import models.Author;
import models.RecommendationScore;

import com.abookadabra.utils.AuthorHelper.ScoreType;

public class AuthorContainer {
	private int targetSize;
	private InverseSortedMapByIntegerWithDuplicates<Author> authors;

	public AuthorContainer(int targetSize) {
		this.targetSize = targetSize;
		authors = new InverseSortedMapByIntegerWithDuplicates<Author>();
	}
	
	public List<Author> getListOfAuthors() {
		return authors.getListOfContent();
	}

	public int size() {
		return authors.getNumberOfElements();
	}
	
	public boolean isItFull() {
		return authors.getNumberOfElements() >= targetSize;
	}

	public boolean shouldWeTryToAddElementForScoreMultiplierLowerThan(float scoreMultiplier) {
		if (isItNotFull())
			return true;
		if (scoreOfWorstAuthor() < scoreMultiplier*RecommendationScore.MAX_SCORE)
			return true;
		return false;
	}

	private boolean isItNotFull() {
		return !isItFull();
	}

	private int scoreOfWorstAuthor() {
		if (authors.getNumberOfElements() < targetSize)
			return RecommendationScore.MIN_SCORE;
		return authors.lastKey();
	}
	
	public void addAuthor(Author authorToAdd, ScoreType typeOfScore, float scoreMultiplier) {
		addAuthor(authorToAdd, getScore(authorToAdd, typeOfScore), scoreMultiplier);
	}
	
	public void addAuthorList(List<Author> authorsToAdd, ScoreType typeOfScore, float scoreMultiplier) {
		for(Author currentAuthorToAdd : authorsToAdd) {
			addAuthor(currentAuthorToAdd, typeOfScore, scoreMultiplier);	
		}
	}
	
	public void addAuthor(Author authorToAdd, ScoreType typeOfScore) {
		addAuthor(authorToAdd, getScore(authorToAdd, typeOfScore), 1);
	}
	
	public void addAuthorList(List<Author> authorsToAdd, ScoreType typeOfScore) {
		for(Author currentAuthorToAdd : authorsToAdd) {
			addAuthor(currentAuthorToAdd, typeOfScore, 1);	
		}
	}

	private void addAuthor(Author authorToAdd, int score, float scoreMultiplier) {
		addAuthor(authorToAdd, (int)(score*scoreMultiplier));
	}
	
	private void addAuthor(Author authorToAdd, int score) {
		if (shouldAddThisAuthor(authorToAdd, score)) {
			//Logger.debug("about to add : " + authorToAdd.fullname + " with score of "+score);
			authors.put(score, authorToAdd);
		}
		removeLastAuthorIfNeeded();
	}

	private boolean shouldAddThisAuthor(Author authorToAdd, int score) {
		return (scoreOfWorstAuthor() < score);
	}

	private void removeLastAuthorIfNeeded() {
		if (isMaxSizeExceeded())
			authors.removeOneElementForKey(authors.lastKey());
	}
	
	private boolean isMaxSizeExceeded() {
		return authors.getNumberOfElements() > targetSize;
	}
	
}
