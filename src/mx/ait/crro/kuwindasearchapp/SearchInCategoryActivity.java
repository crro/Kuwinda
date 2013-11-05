package mx.ait.crro.kuwindasearchapp;

import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.categories.Category;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchInCategoryActivity extends Activity {

	private CategoriesLoader _catLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_in_category);
		
		Intent i = getIntent();
		
		final Long rowId = i.getLongExtra(KuwindaConstants.ROWID_CAT_BUNDLE_KEY, 0);
		String categoryName = i.getStringExtra(KuwindaConstants.CATEGORY_NAME_BUNDLE_KEY);
		
		_catLoad = KuwindaApplication.getCategoriesLoader();
		final Category cat = _catLoad.fetchCategory(rowId);
		
		TextView catName = (TextView) findViewById(R.id.tvCategoryName);
		
		catName.setText(categoryName);
		
		final EditText searchBar = (EditText) findViewById(R.id.etSearchInCat);
		
		//Search Button functionality
		Button searchBtn = (Button) findViewById(R.id.searchBtnInCat);
		
		searchBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String searchQuery = searchBar.getText().toString().trim();
				
				if (searchQuery.equals("")) {
					Toast.makeText(SearchInCategoryActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent resultsIntent = new Intent();
				resultsIntent.setClass(SearchInCategoryActivity.this, ResultsActivity.class);
				
				String[] web = cat.getWebsitesStringArray();
				for (int i = 0; i < web.length; i++ ) {
					System.out.println(web[i]);
				}
				
				resultsIntent.putExtra(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY, searchQuery);
				resultsIntent.putExtra(KuwindaConstants.WEBSITES_BUNDLE_KEY, web);
				startActivity(resultsIntent);
				
			}
		});
		
		//Delete button functionality
		
		Button deleteBtn = (Button) findViewById(R.id.deleteBtnInCat);
		
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				_catLoad.deleteCategory(rowId);
				Intent updateCategories = new Intent(KuwindaConstants.ACTION_CATEGORIES_CHANGED);
				LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(SearchInCategoryActivity.this);
				lbm.sendBroadcast(updateCategories);
				
				try {
					finish();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	

}
