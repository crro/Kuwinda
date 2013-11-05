package mx.ait.crro.kuwindasearchapp.datastorage;

import mx.ait.crro.kuwindasearchapp.data.websites.Website;
import mx.ait.crro.kuwindasearchapp.data.websites.WebsiteModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WebsiteLoader {
	private Context _ctx;
	private DatabaseHelper _dbHelper;
	private SQLiteDatabase _mDb;
	
	public WebsiteLoader(Context c) {
		_ctx = c;
	}
	
	public void open() throws SQLException{ 
		//DatabaseHelper object 
		_dbHelper = new DatabaseHelper(_ctx, DbConstants.DATABASE_NAME); 
		// database object 
		_mDb = _dbHelper.getWritableDatabase(); 
		// create schema if not exist yet 
		//_dbHelper.onCreate(_mDb); 
	}
	
	public void close(){ 
		_dbHelper.close(); 
	}
	
	//COUNT
	public long size() {
		return DatabaseUtils.queryNumEntries(_mDb, DbConstants.Websites.DATABASE_TABLE);
	}
	
	// INSERT
	public long createWebsite(WebsiteModel website) {
		ContentValues values = new ContentValues();
		values.put(DbConstants.Websites.KEY_NAME, website.getName()); //column name that holds the title
		values.put(DbConstants.Websites.KEY_EXECUTION_CODE, website.getExecutionCode());
		values.put(DbConstants.Websites.KEY_URL, website.getURL());
		return _mDb.insert(DbConstants.Websites.DATABASE_TABLE, null, values);
	}

	//DELETE
	public boolean deleteWebsite(long rowId) {
		//this eliminates a specific todo
		return _mDb.delete(DbConstants.Websites.DATABASE_TABLE, DbConstants.Websites.KEY_ROWID + "=" + rowId, null) > 0;
	}

	//DELETE ALL TODOs
	public boolean deleteAllWebsites() {
		//this eliminates the whole database
		return _mDb.delete(DbConstants.Websites.DATABASE_TABLE, null, null) > 0;
	}

	//UPDATE
	public boolean updateWebsite(long rowId, Website newWebsite) {
		ContentValues values = new ContentValues();
		values.put(DbConstants.Websites.KEY_NAME, newWebsite.getName()); //column name that holds the title
		values.put(DbConstants.Websites.KEY_EXECUTION_CODE, newWebsite.getExecutionCode());
		values.put(DbConstants.Websites.KEY_URL, newWebsite.getURL());
		return _mDb.update(DbConstants.Websites.DATABASE_TABLE, values,
				DbConstants.Websites.KEY_ROWID + "=" + rowId, null) > 0;
	}

	//read all Todos
	public Cursor fetchAll() {
		return _mDb.query( DbConstants.Websites.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Websites.KEY_ROWID, 
				DbConstants.Websites.KEY_NAME, 
				DbConstants.Websites.KEY_EXECUTION_CODE,
				DbConstants.Websites.KEY_URL }, 
				null, null, null, null, 
				DbConstants.Websites.KEY_NAME);
	}

	public String fetchExecutionCode(long rowId){
		Cursor c = _mDb.query( DbConstants.Websites.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Websites.KEY_ROWID, 
				DbConstants.Websites.KEY_NAME,
				DbConstants.Websites.KEY_EXECUTION_CODE,
				DbConstants.Websites.KEY_URL
		}, DbConstants.Websites.KEY_ROWID + "=" + rowId, 
		null, null, null, DbConstants.Websites.KEY_NAME);
		if(c.moveToFirst()) {
			return c.getString(c.getColumnIndex(DbConstants.Websites.KEY_EXECUTION_CODE));
		}
		//otherwise return null
		return null;
	}

	//When we inject the database into the listView, the system gives the appropriate IDs to each list element
	//so when the user selects an element we have the appropriate id
	//When we create the list view, it will ask for the Key that identifies the column with the id
	public WebsiteModel fetchWebsiteModel(long rowId){
		Cursor c = _mDb.query( DbConstants.Websites.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Websites.KEY_ROWID, 
				DbConstants.Websites.KEY_NAME,
				DbConstants.Websites.KEY_EXECUTION_CODE,
				DbConstants.Websites.KEY_URL
		}, DbConstants.Websites.KEY_ROWID + "=" + rowId, 
		null, null, null, DbConstants.Websites.KEY_NAME);
		// if the Todo is found with a given ID
		if(c.moveToFirst()) {
			return getWebsiteModelByCursor(c);
		}
		//otherwise return null
		return null;
	}

	public static WebsiteModel getWebsiteModelByCursor(Cursor c) {
		//TODO: Create a website constructor so that i can build a website from this info.
		return new WebsiteModel(c.getString(c.getColumnIndex(DbConstants.Websites.KEY_NAME)),
				c.getInt(c.getColumnIndex(DbConstants.Websites.KEY_EXECUTION_CODE)),
				c.getString(c.getColumnIndex(DbConstants.Websites.KEY_URL)));
	}
}
