package com.abookadabra.utils;

import models.Author;


/**
 * Helper for manipulating Authors.
 * 
 * @author Raphael
 *
 */

public class AuthorHelper {
	public static enum ScoreType {
		BestSeller,
		Classical,
		Ico,
		Hard
	}
	
	public static int getScore(Author authorToLookScoreFor, ScoreType typeOfScore) {
		switch(typeOfScore) {
			case BestSeller:
				return authorToLookScoreFor.score.bestseller;
			case Classical:
				return authorToLookScoreFor.score.classical;
			case Hard:
				return authorToLookScoreFor.score.hard;
			case Ico:
				return authorToLookScoreFor.score.ico;
			default:
				return authorToLookScoreFor.score.bestseller;
		}
	}
}
