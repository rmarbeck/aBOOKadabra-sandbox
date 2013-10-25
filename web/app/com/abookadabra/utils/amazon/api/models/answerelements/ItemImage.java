package com.abookadabra.utils.amazon.api.models.answerelements;

public class ItemImage {
	private String url;
	private long height;
	private String heightUnit;
	private long width;
	private String widthUnit;
	
	public ItemImage(String url,long height, String heightUnit, long width, String widthUnit) {
		this.url = url;
		this.height = height;
		this.heightUnit = heightUnit;
		this.width = width;
		this.widthUnit = widthUnit;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public String getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(String heightUnit) {
		this.heightUnit = heightUnit;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public String getWidthUnit() {
		return widthUnit;
	}

	public void setWidthUnit(String widthUnit) {
		this.widthUnit = widthUnit;
	}


}
