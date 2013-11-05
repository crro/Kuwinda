package mx.ait.crro.kuwindasearchapp;

import mx.ait.crro.kuwindasearchapp.adapter.MainMenuFragmentAdapter;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.websites.Gizmodo;
import mx.ait.crro.kuwindasearchapp.data.websites.TheVerge;
import mx.ait.crro.kuwindasearchapp.data.websites.WebsiteModel;
import mx.ait.crro.kuwindasearchapp.datastorage.WebsiteLoader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

public class MainMenuActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		//We check on the saved preferences
		SharedPreferences sp = getSharedPreferences(KuwindaConstants.WEBSITES_PREFERENCES, MODE_PRIVATE);
		boolean websitesReady = sp.getBoolean(KuwindaConstants.WEBSITES_BOOLEAN, false);
		
		if (!websitesReady) {
			this.setUpWebsites();
			websitesReady = true;
			Editor editor = sp.edit();
			editor.putBoolean(KuwindaConstants.WEBSITES_BOOLEAN, websitesReady);
			editor.commit();
		}
		
		
		MainMenuFragmentAdapter adapter = new MainMenuFragmentAdapter(getSupportFragmentManager());
		
		ViewPager p = (ViewPager) findViewById(R.id.pager);
		p.setAdapter(adapter);
		p.setCurrentItem(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public void setUpWebsites() {
		WebsiteLoader wB = KuwindaApplication.getWebsiteLoader();
		wB.createWebsite(new WebsiteModel("TheVerge", 1, "http://theVerge.com"));
		wB.createWebsite(new WebsiteModel("Amazon", 6, "http://www.amazon.com"));
		wB.createWebsite(new WebsiteModel("Gizmodo", 2, "http://gizmodo.com"));
		wB.createWebsite(new WebsiteModel("BestBuy", 8, "http://www.bestbuy.com"));
		Cursor c = wB.fetchAll();
		if (c.moveToFirst()) {
			do {
				WebsiteModel w = wB.getWebsiteModelByCursor(c);
				Log.d("Websites", w.getName());
			} while (c.moveToNext());
		}
		
	}
}
