package mx.ait.crro.kuwindasearchapp.datastorage;

public class DbConstants {
	// Local Broadcast Action that is fired when database is changed 
		public static final String ACTION_DATABASE_CATEGORIES_CHANGED = "mx.ait.crro.todobasicfragment.DATABASE_CATEGORIES_CHANGED"; 
		public static final String ACTION_DATABASE_WEBSITES_CHANGED = "mx.ait.crro.todobasicfragment.DATABASE_WEBSITES_CHANGED";
		// filename of the (binary) database 
		public static final String DATABASE_NAME = "data.db";
		// version number 
		public static final int DATABASE_VERSION = 1; // Full DB create script (simply concatenate all table create scripts of innerclasses) 
		public static String DATABASE_CREATE_WEBSITES = Websites.DATABASE_CREATE;
		public static String DATABASE_CREATE_CATEGORIES = Categories.DATABASE_CREATE;
		
		// // Full DB drop script 
		public static String DATABASE_DROP_WEBSITES =Websites.DATABASE_DROP;
		public static String DATABASE_DROP_CATEGORIES = Categories.DATABASE_DROP;
		
		/* DB constants of Todo class */
		public static class Categories {
			// table name of Todo records 
			public static final String DATABASE_TABLE = "categories"; 
			
			// column names 
			public static final String KEY_ROWID = "_id"; 
			public static final String KEY_NAME = "name"; 
			public static final String KEY_WEBSITES = "websites";
			public static final String KEY_KEYWORD = "keyword";
			
			// Database table schema creator script 
			public static final String DATABASE_CREATE =
			"create table if not exists "+ DATABASE_TABLE +" ( " + KEY_ROWID
			+" integer primary key autoincrement, " + KEY_NAME + " text not null, "
			+ KEY_WEBSITES + " text, " + KEY_KEYWORD + " text" + "); "; 
			//primary key: unique id
			//autoincrement: let the database decide the id
			
			// table drop script 
			public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE + "; "; 
		}
		
		public static class Websites {
			// table name of Todo records 
			public static final String DATABASE_TABLE = "websites"; 
			
			// column names 
			public static final String KEY_ROWID = "_id"; 
			public static final String KEY_NAME = "name"; 
			public static final String KEY_EXECUTION_CODE = "execution";
			public static final String KEY_URL = "url";

			
			// Database table schema creator script 
			public static final String DATABASE_CREATE =
			"create table if not exists "+ DATABASE_TABLE +" ( " + KEY_ROWID
			+" integer primary key autoincrement, " + KEY_NAME + " text not null, " + KEY_EXECUTION_CODE + " integer," 
			+ KEY_URL + " text" + "); "; 
			//primary key: unique id
			//autoincrement: let the database decide the id
			
			// table drop script 
			public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE + "; "; 
		}
}
