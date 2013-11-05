package mx.ait.crro.kuwindasearchapp.application;

import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import mx.ait.crro.kuwindasearchapp.datastorage.WebsiteLoader;
import android.app.Application;

public class KuwindaApplication extends Application {
	/*
	 * this class will mantain the DB connection so that we dont have to instantiate a new TodoDbLoader in every activity
	 */
	private static WebsiteLoader _wbLoader;
	private static CategoriesLoader _catLoader;

	public static WebsiteLoader getWebsiteLoader() {
		return _wbLoader;
	}
	
	public static CategoriesLoader getCategoriesLoader() {
		return _catLoader;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		_wbLoader = new WebsiteLoader(this);
		_catLoader =  new CategoriesLoader(this);
		_wbLoader.open();
		_catLoader.open();
	}
	
	@Override public void onTerminate() { 
		// Close the internal db 
		super.onTerminate();
		_wbLoader.close();
		_catLoader.close();
	}
}
