package mx.ait.crro.kuwindasearchapp.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String name) {
		super(context, name, null, DbConstants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DbConstants.DATABASE_CREATE_WEBSITES);
		db.execSQL(DbConstants.DATABASE_CREATE_CATEGORIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DbConstants.DATABASE_DROP_CATEGORIES);
		db.execSQL(DbConstants.DATABASE_DROP_WEBSITES);
		db.execSQL(DbConstants.DATABASE_CREATE_WEBSITES);
		db.execSQL(DbConstants.DATABASE_CREATE_CATEGORIES);
	}
	
}
