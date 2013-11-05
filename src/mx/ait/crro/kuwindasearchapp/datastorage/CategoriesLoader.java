package mx.ait.crro.kuwindasearchapp.datastorage;

import mx.ait.crro.kuwindasearchapp.data.categories.Category;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoriesLoader {
	private Context _ctx;
	private DatabaseHelper _dbHelper;
	private SQLiteDatabase _mDb;
	
	public CategoriesLoader(Context ctx) {
		_ctx = ctx;
	}

	public void open() throws SQLException{ 
		//DatabaseHelper object 
		_dbHelper = new DatabaseHelper(_ctx, DbConstants.DATABASE_NAME); 
		// database object 
		_mDb = _dbHelper.getWritableDatabase(); //this calls the on create method
		
		// create schema if not exist yet 
		//_dbHelper.onCreate(_mDb); 
	}
	
	public void close(){ 
		_dbHelper.close(); 
	}

	// INSERT
	public long createCategory(Category category) {
		//ContentValues is used t wrap multiple data inside itself. and then put it in the database
		ContentValues values = new ContentValues();
		values.put(DbConstants.Categories.KEY_NAME, category.getName()); //column name that holds the title
		values.put(DbConstants.Categories.KEY_WEBSITES, category.getWebsitesString());
		values.put(DbConstants.Categories.KEY_KEYWORD, category.getKeyword());
		return _mDb.insert(DbConstants.Categories.DATABASE_TABLE, null, values);
	}

	//DELETE
	public boolean deleteCategory(long rowId) {
		//this eliminates a specific todo
		return _mDb.delete(DbConstants.Categories.DATABASE_TABLE, DbConstants.Categories.KEY_ROWID + "=" + rowId, null) > 0;
	}

	//DELETE ALL TODOs
	public boolean deleteAllCategories() {
		//this eliminates the whole database
		return _mDb.delete(DbConstants.Categories.DATABASE_TABLE, null, null) > 0;
	}

	//UPDATE
	public boolean updateCategory(long rowId, Category newCategory) {
		ContentValues values = new ContentValues();
		values.put(DbConstants.Categories.KEY_NAME, newCategory.getName()); //column name that holds the title
		values.put(DbConstants.Categories.KEY_WEBSITES, newCategory.getWebsitesString());
		values.put(DbConstants.Categories.KEY_KEYWORD, newCategory.getKeyword());
		return _mDb.update(DbConstants.Categories.DATABASE_TABLE, values,
				DbConstants.Categories.KEY_ROWID + "=" + rowId, null) > 0;
	}

	//read all Todos
	public Cursor fetchAll() {
		return _mDb.query( DbConstants.Categories.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Categories.KEY_ROWID, 
				DbConstants.Categories.KEY_NAME, 
				DbConstants.Categories.KEY_WEBSITES, 
				DbConstants.Categories.KEY_KEYWORD }, 
				null, null, null, null, 
				DbConstants.Categories.KEY_NAME);
	}

	//When we inject the database into the listView, the system gives the appropriate IDs to each list element
	//so when the user selects an element we have the appropriate id
	//When we create the list view, it will ask for the Key that identifies the column with the id
	public Category fetchCategory(long rowId){
		Cursor c = _mDb.query( DbConstants.Categories.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Categories.KEY_ROWID, 
				DbConstants.Categories.KEY_NAME, 
				DbConstants.Categories.KEY_WEBSITES, 
				DbConstants.Categories.KEY_KEYWORD 
		}, DbConstants.Categories.KEY_ROWID + "=" + rowId, 
		null, null, null, DbConstants.Categories.KEY_NAME);
		// if the Category is found with a given ID
		if(c.moveToFirst()) {
			return getCategoryByCursor(c);
		}
		//otherwise return null
		return null;
	}
	
	public Cursor getCursorOfCategory(long rowId) {
		Cursor c = _mDb.query( DbConstants.Categories.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Categories.KEY_ROWID, 
				DbConstants.Categories.KEY_NAME, 
				DbConstants.Categories.KEY_WEBSITES, 
				DbConstants.Categories.KEY_KEYWORD 
		}, DbConstants.Categories.KEY_ROWID + "=" + rowId, 
		null, null, null, DbConstants.Categories.KEY_NAME);
		// if the Category is found with a given ID
		if(c.moveToFirst()) {
			return c;
		}
		//otherwise return null
		return null;
	}
	
	public static String getNameByCursor(Cursor c) {
		return c.getString(c.getColumnIndex(DbConstants.Categories.KEY_NAME));
	}

	public static Category getCategoryByCursor(Cursor c) {
		String webString = c.getString(c.getColumnIndex(DbConstants.Categories.KEY_WEBSITES));
		String[] webSArray = webString.split(",");
		return new Category(c.getString(c.getColumnIndex(DbConstants.Categories.KEY_NAME)),
				webSArray,
				c.getString(c.getColumnIndex(DbConstants.Categories.KEY_KEYWORD)));
	}
}
