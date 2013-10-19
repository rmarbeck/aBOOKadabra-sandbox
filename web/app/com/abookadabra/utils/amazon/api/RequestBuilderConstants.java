package com.abookadabra.utils.amazon.api;

class RequestBuilderConstants {
	static String AMAZON_SERVICE_PARAM 						= "Service";
	static String AMAZON_SERVICE_PARAM_VALUE 				= "AWSECommerceService";
	
	static String AMAZON_OPERATION_PARAM 					= "Operation";
	static String AMAZON_OPERATION_PARAM_ITEM_LOOKUP 		= "ItemLookup";
	static String AMAZON_OPERATION_PARAM_ITEM_SEARCH 		= "ItemSearch";
	static String AMAZON_OPERATION_PARAM_ITEM_SIMILARITY	= "SimilarityLookup";
	static String AMAZON_OPERATION_PARAM_BROWSE_NODE		= "BrowseNodeLookup";
	
	static String AMAZON_ASSOCIATE_TAG_PARAM 				= "AssociateTag";
	public static String AMAZON_ASSOCIATE_TAG_PARAM_VALUE 	= "abookadabra-21";
	
	static String AMAZON_ID_TYPE_PARAM    					= "IdType";
	static String AMAZON_ID_TYPE_PARAM_EAN_VALUE			= "EAN";
	static String AMAZON_ID_TYPE_PARAM_ISBN_VALUE			= "ISBN";
	static String AMAZON_ID_TYPE_PARAM_ASIN_VALUE			= "ASIN";
	static String AMAZON_ITEM_ID_PARAM 						= "ItemId";
	static String AMAZON_SEARCH_INDEX_PARAM 				= "SearchIndex";
	static String AMAZON_SEARCH_INDEX_FOR_BOOKS_PARAM_VALUE = "Books";
	static String AMAZON_AUTHOR_PARAM 						= "Author";
	static String AMAZON_TITLE_PARAM 						= "Title";
	static String AMAZON_KEYWORDS_PARAM 					= "Keywords";
	static String AMAZON_SORT_PARAM		 					= "Sort";
	static String AMAZON_SORT_PARAM_SALES_RANK_VALUE		= "salesrank";
	static String AMAZON_SORT_PARAM_PRICE_RANK_VALUE		= "pricerank";
	static String AMAZON_SORT_PARAM_INVERSE_PRICE_RANK_VALUE= "inverse-pricerank";
	static String AMAZON_SORT_PARAM_OLD_TO_NEW__DATE_RANK_VALUE		= "-daterank";
	static String AMAZON_SORT_PARAM_A_TO_Z_TITLE_RANK_VALUE	= "titlerank";
	static String AMAZON_SORT_PARAM_Z_TO_A_TITLE_RANK_VALUE	= "-titlerank";
	
	static String AMAZON_ITEM_PAGE_PARAM					= "ItemPage";
	static int ITEM_PAGE_MIN								= 1;
	static int ITEM_PAGE_MAX								= 10;
	
	static String AMAZON_RESPONSE_GROUP_PARAM 				= "ResponseGroup";
	static String AMAZON_RESPONSE_GROUP_PARAM_LARGE_VALUE	= "Large";
	static String AMAZON_RESPONSE_GROUP_PARAM_MEDIUM_VALUE	= "Medium";
	static String AMAZON_RESPONSE_GROUP_PARAM_SMALL_VALUE	= "Small";
	
	static String AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_MOST_GIFTED		= "MostGifted";
	static String AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_NEW_RELEASES		= "NewReleases";
	static String AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_MOST_WISHED_FOR	= "MostWishedFor";
	static String AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_TOP_SELLERS		= "Top Sellers";

	
	static String AMAZON_VERSION_PARAM 						= "Version";
	static String AMAZON_VERSION_PARAM_VALUE 				= "2011-08-01";
	
	static String AMAZON_VARIATION_PAGE_PARAM				= "VariationPage";
	static int VARIATION_PAGE_MIN							= 1;
	static int VARIATION_PAGE_MAX							= 150;
	
	static String AMAZON_BROWSE_NODE_PARAM					= "BrowseNodeId";
	static int BROWSE_NODE_MIN								= 1;
	
	static String AMAZON_CONDITION_PARAM					= "Condition";
	static String AMAZON_CONDITION_PARAM_NEW_VALUE			= "New";
	static String AMAZON_CONDITION_PARAM_USED_VALUE			= "Used";
	static String AMAZON_CONDITION_PARAM_COLLECTIBLE_VALUE	= "Collectible";
	static String AMAZON_CONDITION_PARAM_REFURB_VALUE		= "Refurbished";
	static String AMAZON_CONDITION_PARAM_ALL_VALUE			= "All";
	
	static String AMAZON_BROWSE_NODE_ID_PARAM				= "BrowseNodeId";
	static String AMAZON_SIMILARY_TYPE_PARAM				= "SimilarityType";
	static String AMAZON_SIMILARY_TYPE_PARAM_RANDOM_VALUE	= "Random";
	static String AMAZON_SIMILARY_TYPE_PARAM_INTERSECTION_VALUE	= "Intersection";
}
