package mx.ait.crro.kuwindasearchapp.fragment;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.SearchInCategoryActivity;
import mx.ait.crro.kuwindasearchapp.adapter.CategoriesAdapter;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CategoriesFragment extends ListFragment {
	private CategoriesAdapter _catAdapter;
	private CategoriesLoader _catLoader;
	private GetAllCategories _getAllCategories;
	private LocalBroadcastManager _lbm;
	
	private BroadcastReceiver _udpateCategories = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			System.out.println("We are updating the categories");
			refreshList();
			if (isVisible()) {
				getListView().invalidateViews();
			}
		}
		
	};
	
	private class GetAllCategories extends AsyncTask<Void, Void, Cursor> {
		private static final String TAG = "GetAllCategories";

		@Override
		protected Cursor doInBackground(Void... params) {
			try {
				Cursor result = _catLoader.fetchAll();
				if (!isCancelled()) {
					return result;
				} else {
					Log.d(TAG, "Cancelled, closing cursor");
					if (result != null) {
						result.close();
					}
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Cursor result) {
			super.onPostExecute(result);
			Log.d(TAG, "Fetch completed, displaying cursor results!");
			try {
				if (_catAdapter == null) {
					_catAdapter = new CategoriesAdapter(getActivity()
							.getApplicationContext(), result);
					setListAdapter(_catAdapter);
					getListView().invalidateViews();
				} else {
					_catAdapter.changeCursor(result);
				}
				_getAllCategories = null;
			} catch (Exception e) {
			}
		}

	}
	
	

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_lbm = LocalBroadcastManager.getInstance(getActivity());
		_catLoader = KuwindaApplication.getCategoriesLoader();
		_catAdapter = new CategoriesAdapter(getActivity(), _catLoader.fetchAll());
		setListAdapter(_catAdapter);
		IntentFilter filter = new IntentFilter(KuwindaConstants.ACTION_CATEGORIES_CHANGED);
		_lbm.registerReceiver(_udpateCategories, filter);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getListView().setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (_getAllCategories != null) {
			_getAllCategories.cancel(false);
		}
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		_lbm.unregisterReceiver(_udpateCategories);
		// Close the cursor if there is any assigned to the adapter
		if (_catAdapter != null && _catAdapter.getCursor() != null) {
			_catAdapter.getCursor().close();
		}
	}

	public void refreshList() {
		if (_getAllCategories != null) {
			_getAllCategories.cancel(false);
		}
		_getAllCategories = new GetAllCategories();
		_getAllCategories.execute();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick( l, v, position, id);
		TextView catName = (TextView) v.findViewById(R.id.categoryName);
		
		String categoryName = catName.getText().toString();
		
		Cursor cursorOfRow = _catLoader.getCursorOfCategory(id);
		
		//I need to start making the start of activity
		
		Intent searchInCategoryIntent = new Intent(getActivity(), SearchInCategoryActivity.class);
		searchInCategoryIntent.putExtra(KuwindaConstants.ROWID_CAT_BUNDLE_KEY, id);
		searchInCategoryIntent.putExtra(KuwindaConstants.CATEGORY_NAME_BUNDLE_KEY, categoryName);
		startActivity(searchInCategoryIntent);
	}
}
