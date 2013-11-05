package mx.ait.crro.kuwindasearchapp.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.ResultsActivity;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.categories.Category;
import mx.ait.crro.kuwindasearchapp.data.websites.WebsiteModel;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import mx.ait.crro.kuwindasearchapp.datastorage.DbConstants;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriesAdapter extends CursorAdapter{
	//TODO: We need to implement the holde pattern here
	private int _rowIdIndex;
	private HashMap<String, Boolean> _expandable;
	private Context _ctx;
	private CategoriesLoader _catLoader;
	
	public CategoriesAdapter(Context context, Cursor c) {
		super(context, c, false);
		
		System.out.println("We create a new adapter");
		_ctx = context;
		_rowIdIndex = c.getColumnIndex(DbConstants.Categories.KEY_ROWID);
		_expandable = new HashMap<String, Boolean>();
		_catLoader = KuwindaApplication.getCategoriesLoader();
		if (c.moveToFirst()) {
			do {
				String catName = CategoriesLoader.getNameByCursor(c);
				_expandable.put(catName, Boolean.valueOf(false));
			} while (c.moveToNext());
		}
	}

	@Override
	public void bindView(View view,Context context, Cursor cursor) {
		boolean expandedView = false;
		TextView categoryName = (TextView) view.findViewById(R.id.categoryName);

		Category cat = CategoriesLoader.getCategoryByCursor(cursor);
		
//		Boolean expandableBool = _expandable.get(cat.getName());
//		if (expandableBool != null) {
//			boolean boolVal = expandableBool.booleanValue();
//			if (boolVal) {
//				searchBar.setVisibility(View.VISIBLE);
//				searchBtn.setVisibility(View.VISIBLE);
//				deleteBtn.setVisibility(View.VISIBLE);
//			} else {
//				searchBar.setVisibility(View.GONE);
//				searchBtn.setVisibility(View.GONE);
//				deleteBtn.setVisibility(View.GONE);
//			}
//		} else {
//			searchBar.setVisibility(View.GONE);
//			searchBtn.setVisibility(View.GONE);
//			deleteBtn.setVisibility(View.GONE);
//			_expandable.put(cat.getName(), Boolean.valueOf(false));
//		}
//		if (_expandable.get(Integer.valueOf(cursor.getInt(_rowIdIndex))).booleanValue()) {
//			searchBar.setVisibility(View.VISIBLE);
//			searchBtn.setVisibility(View.VISIBLE);
//			deleteBtn.setVisibility(View.VISIBLE);
//		} else {
//			searchBar.setVisibility(View.GONE);
//			searchBtn.setVisibility(View.GONE);
//			deleteBtn.setVisibility(View.GONE);
//		}
		
		
		
		categoryName.setTag(Boolean.valueOf(expandedView));
		categoryName.setText(cat.getName());
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View row = inflater.inflate(R.layout.categoriesrow, null);
		bindView(row, context, cursor);
		return row;
	}
	
	

	public boolean areEqual(Category c1, Category c2) {
		boolean val1 = c1.getKeyword().equals(c2.getKeyword());
		boolean val2 =  c1.getName().equals(c2.getName());
		if (val1 && val2) {
			return true;
		} else {
			return false;
		}
	}
	
	private class DeleteOnClickListener implements OnClickListener {
		private Cursor _cursor;
		private Context _ctx;
		private Button _sButon;
		private EditText _sBar;
		private TextView _catName;
		
		public DeleteOnClickListener(Cursor c, Context cx, EditText sBar, Button sButon, TextView catName) {
			_cursor = c;
			_ctx = cx;
			_sBar = sBar;
			_sButon = sButon;
			_catName = catName;
			
		}
		
		public void onClick(View v) {
			CategoriesLoader catLoader = KuwindaApplication.getCategoriesLoader();
			String categoryName = _catName.getText().toString();
			//int rowId = _cursor.getInt(_cursor.getColumnIndex(DbConstants.Categories.KEY_ROWID));
			Integer rowId = (Integer) v.getTag();
			catLoader.deleteCategory(rowId.intValue());
			Intent catChanged = new Intent(KuwindaConstants.ACTION_CATEGORIES_CHANGED);
			Intent defChanged = new Intent(KuwindaConstants.UPDATE_DEFAULT_WEBSITE);
			LocalBroadcastManager.getInstance(_ctx).sendBroadcast(catChanged);
			LocalBroadcastManager.getInstance(_ctx).sendBroadcast(defChanged);
			//_expandable.put(categoryName, Boolean.valueOf(false));
			
			 
			v.setVisibility(View.GONE);
			_sBar.setVisibility(View.GONE);
			_sButon.setVisibility(View.GONE);
			
			notifyDataSetChanged();
		}
		
	}
	
	private class SearchOnClickListener implements OnClickListener {
		private Context _ctx;
		private Cursor _cursor;
		private EditText _searchBar;
		private String _searchQuery;
		
		public SearchOnClickListener(Context context, Cursor c, EditText sBar) {
			_ctx = context;
			_cursor = c;
			_searchBar = sBar;
		}
		
		public void onClick(View v) {
			_searchQuery = _searchBar.getText().toString().trim();
			if (_searchQuery.equals("")) { 
				Toast.makeText(_ctx, "Please enter a search query", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Intent resultsIntent = new Intent();
			resultsIntent.setClass(_ctx, ResultsActivity.class);
			 
			Category category = CategoriesLoader.getCategoryByCursor(_cursor);
			
			System.out.println(category.getName());
			
			resultsIntent.putExtra(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY, _searchQuery);
			resultsIntent.putExtra(KuwindaConstants.WEBSITES_BUNDLE_KEY, category.getWebsitesStringArray());
			_ctx.startActivity(resultsIntent);
		}
	}

}
