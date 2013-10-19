package com.abookadabra.utils.amazon.api.models.answerelements;

public class ItemImage {
	public enum SizeOfImage {
		THUMBNAIL("ThumbnailImage"),
		TINY("TinyImage"),
		SMALL("SmallImage"),
		MEDIUM("MediumImage"),
		LARGE("LargeImage"),
		SWACTH("SwatchImage");
		
		final String value;
		SizeOfImage(String value) {
			this.value = value;
		}
	}
	
	private SizeOfImage size;
	private String url;
	private long height;
	private String heightUnit;
	private long width;
	private String widthUnit;
	
	public ItemImage() {
		
	}
}
