package mx.ait.crro.kuwindasearchapp.fragment;

import mx.ait.crro.kuwindasearchapp.ChooseDefaultCategoryActivity;
import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.ResultsActivity;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.categories.Category;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment {
	private Category _category;
	private int _defaultCategoryRowId;
	private String _defaultCategoryName;
	private TextView _tvDefault;
	private CategoriesLoader _catLoad;
	private LocalBroadcastManager _lbm;
	
	private BroadcastReceiver _updateDefault = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			System.out.println("WEBSITE UPDATE CHECKING");
			if (_tvDefault != null && _catLoad != null) {
				Category defCat = _catLoad.fetchCategory(_defaultCategoryRowId);
				if (defCat == null) {
					_tvDefault.setText("The default category is:");
				}
			}
		}
		
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_lbm = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter filter = new IntentFilter(KuwindaConstants.UPDATE_DEFAULT_WEBSITE);
		_lbm.registerReceiver(_updateDefault, filter);
		
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.search_fragment, null);
		Button searchBtn = (Button) v.findViewById(R.id.searchBtn);
		Button chooseDefaultBtn = (Button) v.findViewById(R.id.btnChooseDef);
		_tvDefault = (TextView) v.findViewById(R.id.tvDefaultCat);
		
		SharedPreferences sp = getActivity().getSharedPreferences(KuwindaConstants.DEFAULT_WEBSITES_PREFERENCES, Activity.MODE_PRIVATE);
		_defaultCategoryRowId = sp.getInt(KuwindaConstants.DEFAULT_WEBSITE, -9999);
		
		_catLoad = KuwindaApplication.getCategoriesLoader();
		Category defCat = _catLoad.fetchCategory(_defaultCategoryRowId);
		
		
		
		if (defCat != null) {
			_defaultCategoryName = defCat.getName();
			_tvDefault.setText("The default category is: " + _defaultCategoryName);
		}
		
		
		
		chooseDefaultBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent defaultIntent = new Intent();
				defaultIntent.setClass(getActivity(), ChooseDefaultCategoryActivity.class);
				
				startActivityForResult(defaultIntent, KuwindaConstants.CHOOSE_DEFAULT_WEBSITE_SELECTED);
			}
		});
		
		final EditText etSearch = (EditText) v.findViewById(R.id.etSearch);
		
		searchBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String textS = etSearch.getText().toString();
				String searchQuery = textS.trim();
				
				//TODO: More parsing needed
				if (searchQuery.equals("")) {
					Toast.makeText(getActivity(), "Please enter a search query", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (_defaultCategoryName == null) {
					Toast.makeText(getActivity(), "Please select a default category", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent resultsIntent = new Intent();
				resultsIntent.setClass(getActivity(), ResultsActivity.class);
				
				CategoriesLoader _cLoader = KuwindaApplication.getCategoriesLoader();
				
				_category = _cLoader.fetchCategory(_defaultCategoryRowId);
				String[] web = _category.getWebsitesStringArray();
				for (int i = 0; i < web.length; i++ ) {
					System.out.println(web[i]);
				}
				
				resultsIntent.putExtra(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY, searchQuery);
				resultsIntent.putExtra(KuwindaConstants.WEBSITES_BUNDLE_KEY, web);
				startActivity(resultsIntent);
			}
		});
		
		SharedPreferences spMessage = getActivity().getSharedPreferences(KuwindaConstants.WELCOME_MESSAGE_PREFERENCES, Activity.MODE_PRIVATE);
		boolean messageShow = spMessage.getBoolean(KuwindaConstants.WELCOME_MESSAGE_SHOW, true);
		TextView tvMessage = (TextView) v.findViewById(R.id.tvWelcomeMessage);
		
		if (messageShow) {
			tvMessage.setVisibility(View.VISIBLE);
			Editor editor = spMessage.edit();
			editor.putBoolean(KuwindaConstants.WELCOME_MESSAGE_SHOW, false);
			editor.commit();
		} else {
			tvMessage.setVisibility(View.GONE);
		}
		
		return v;
	}
	
	public void onResume() {
		super.onResume();
		Category defCat = _catLoad.fetchCategory(_defaultCategoryRowId);
		if (defCat == null) {
			_tvDefault.setText("The default category is:");
		}
		IntentFilter filter = new IntentFilter(KuwindaConstants.UPDATE_DEFAULT_WEBSITE);
		_lbm.registerReceiver(_updateDefault, filter);
	}
	
	public void onPause() {
		super.onPause();
		_lbm.unregisterReceiver(_updateDefault);
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case KuwindaConstants.CHOOSE_DEFAULT_WEBSITE_SELECTED:
				if (resultCode == KuwindaConstants.DEFAULT_WEBSITE_SELECTED) {
					_defaultCategoryName = data.getStringExtra(KuwindaConstants.DEFAULT_WEBSITE_KEY);
					Integer i = data.getIntExtra(KuwindaConstants.DEFAULT_WEBSITE_ROWID_KEY, 0);
					_defaultCategoryRowId = i.intValue();
					_tvDefault.setText("The default category is: " + _defaultCategoryName);
					SharedPreferences sp = getActivity().getSharedPreferences(KuwindaConstants.DEFAULT_WEBSITES_PREFERENCES, Activity.MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putInt(KuwindaConstants.DEFAULT_WEBSITE, _defaultCategoryRowId);
					editor.commit();
				}
				break;
	
			default:
				break;
			}
	}
}
