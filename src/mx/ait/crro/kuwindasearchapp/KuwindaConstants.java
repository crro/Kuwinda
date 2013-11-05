package mx.ait.crro.kuwindasearchapp;

public class KuwindaConstants {
	//Fragments
	public static final String CATEGORIES_FRAGMENT_TAG = "Categories";
	public static final String CREATCAT_FRAGMENT_TAG = "Create a new cateory";
	public static final String SEARCH_FRAGMENT_TAG = "Search";
	
	//key for search query in Bundle
	public static final String SEARCH_QUERY_BUNDLE_KEY = "search query";
	//key for category in Bundle
	public static final String WEBSITES_BUNDLE_KEY = "the category to search in";
	//key for websites in Bundle 
	public static final String LINKS_BUNDLE_KEY = "the links with results";
	//key for url in Bundle
	public static final String URL_BUNDLE_KEY = "the url of the link";
	//key for the rowId in bundle
	public static final String ROWID_CAT_BUNDLE_KEY = "the rowID of the selected item";
	//key for the Category name in bundle
	public static final String CATEGORY_NAME_BUNDLE_KEY = "the category name selected";
	
	//DefaultWebsite selected
	public static final String DEFAULT_WEBSITE_KEY = "the default website";
	//RowId of default website selected
	public static final String DEFAULT_WEBSITE_ROWID_KEY = "the default website row id";
	//Intent action for default website selected
	public static final int CHOOSE_DEFAULT_WEBSITE_SELECTED = 34;
	//Rsult code for web sleected
	public static final int DEFAULT_WEBSITE_SELECTED = 35;
	
	
	
	//Request code for choosing websites
	public static final int REQUEST_CODE_WEBSITE = 5;
	
	//ResultCode for successfully getting websites
	public static final int RESULT_WEBSITES_SELECTED = 10;
	
	//Key for the bundle returned after a user selected websites
	public static final String GET_WEBSITES = "Websites_selected";
	
	//SharedPreference
	//for setting up the websites
	public static final String WEBSITES_PREFERENCES = "MyWebPreferences";
	public static final String WEBSITES_BOOLEAN = "website_boolean";
	//for the default category
	public static final String DEFAULT_WEBSITES_PREFERENCES = "MyDefWebPreferences";
	public static final String DEFAULT_WEBSITE = "defautl_website";
	//for the welcoming message
	public static final String WELCOME_MESSAGE_PREFERENCES = "MyWelcomingMessagePreferences";
	public static final String WELCOME_MESSAGE_SHOW = "welcome_message";
	
	//LocalBroadcastManager
		//For Categories 
	public static final String ACTION_CATEGORIES_CHANGED = "The categories changed";
		//For Websites not currently used
	public static final String ACTION_WEBSITES_CHANGED = "The websites changed";
		//sending a message
	public static final String WEBSITE_CHANGED_MESSAGE = "message from the web";
		//For updating the default_website
	public static final String UPDATE_DEFAULT_WEBSITE = "update the default website";
}
