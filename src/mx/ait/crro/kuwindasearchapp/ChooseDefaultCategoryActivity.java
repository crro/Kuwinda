package mx.ait.crro.kuwindasearchapp;

import mx.ait.crro.kuwindasearchapp.adapter.CategoriesAdapter;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseDefaultCategoryActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CategoriesLoader catLoader = KuwindaApplication.getCategoriesLoader();
		CategoriesAdapter catAdapter = new CategoriesAdapter(this, catLoader.fetchAll());
		setListAdapter(catAdapter);
		
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView tvName = (TextView) v.findViewById(R.id.categoryName);
		
		
		
		Intent defaultIntent = new Intent();
		defaultIntent.putExtra(KuwindaConstants.DEFAULT_WEBSITE_KEY, tvName.getText().toString());
		defaultIntent.putExtra(KuwindaConstants.DEFAULT_WEBSITE_ROWID_KEY, Integer.valueOf((int) id));
		setResult(KuwindaConstants.DEFAULT_WEBSITE_SELECTED, defaultIntent);
		try {
			finish();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
