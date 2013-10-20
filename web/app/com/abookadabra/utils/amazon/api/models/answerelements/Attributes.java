package com.abookadabra.utils.amazon.api.models.answerelements;

import java.util.*;

public class Attributes {
	private List<String> authors;
	private String ean;
	private String isbn;
	private String binding;
	private String manufacturer;
	private String label;
	private String edition;
	private long numberOfPages;
	private String productGroup;
	private String productTypeName;
	private Date publicationDate;
	private Date releaseDate;
	private String publisher;
	private String sku;
	private String studio;
	private String title;
	private ListPrice listPrice;
	private List<Language> languages;

	public static class Language {
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		private String name;
		private String type;
		
		public Language(String name, String type) {
			this.name = name;
			this.type = type;
		}
	}
	
	public static class ListPrice {
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}

		public String getFormattedPrice() {
			return formattedPrice;
		}

		public void setFormattedPrice(String formattedPrice) {
			this.formattedPrice = formattedPrice;
		}

		private String amount;
		private String currencyCode;
		private String formattedPrice;
		
		public ListPrice(String amount, String currencyCode, String formattedPrice) {
			this.amount = amount;
			this.currencyCode = currencyCode;
			this.formattedPrice = formattedPrice;
		}
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public long getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(long numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ListPrice getListPrice() {
		return listPrice;
	}

	public void setListPrice(ListPrice listPrice) {
		this.listPrice = listPrice;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}
