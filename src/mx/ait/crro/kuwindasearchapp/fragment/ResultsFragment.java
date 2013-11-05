package mx.ait.crro.kuwindasearchapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.ResultsActivity;
import mx.ait.crro.kuwindasearchapp.adapter.ResultsAdapter;
import mx.ait.crro.kuwindasearchapp.data.websites.Amazon;
import mx.ait.crro.kuwindasearchapp.data.websites.BestBuy;
import mx.ait.crro.kuwindasearchapp.data.websites.Gizmodo;
import mx.ait.crro.kuwindasearchapp.data.websites.TheVerge;
import mx.ait.crro.kuwindasearchapp.data.websites.Website;
import mx.ait.crro.kuwindasearchapp.search.GetWebsiteResults;
import mx.ait.crro.kuwindasearchapp.search.Link;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsFragment extends ListFragment {
	public static final String TAG = "ResultsListFragment";

	private HashMap<String, Link> _results;
	private String[] _websiteNames;
	private ArrayList<Website> _websites;
	private String _searchQuery;
	private ResultsAdapter _resultsAdapter;
	private LocalBroadcastManager _lbm;
	private ArrayList<GetWebsiteResults> _searchingWebsites;
	
	private BroadcastReceiver _updateWebsite = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent) {
			if (isVisible()) {
				getListView().invalidateViews();
			}
			String errorMessage = intent.getStringExtra(KuwindaConstants.WEBSITE_CHANGED_MESSAGE);
			if (errorMessage != null) {
				Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			}
		};
	};
	

	//Creates an instance of the fragment
	public static ResultsFragment newInstance(String searchQuery, String[] websites) {
		ResultsFragment result = new ResultsFragment();
		
		Bundle args = new Bundle();
		args.putString(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY, searchQuery);
		args.putStringArray(KuwindaConstants.WEBSITES_BUNDLE_KEY, websites);
		
		result.setArguments(args);
		
		return result;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_searchingWebsites = new ArrayList<GetWebsiteResults>();
		
		_lbm = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter filter = new IntentFilter(KuwindaConstants.ACTION_WEBSITES_CHANGED);
		_lbm.registerReceiver(_updateWebsite, filter);
		
		Bundle args = getArguments();
		_websiteNames = args.getStringArray(KuwindaConstants.WEBSITES_BUNDLE_KEY);
		_searchQuery = args.getString(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY);
		_websites = new ArrayList<Website>();
		_resultsAdapter = new ResultsAdapter(_websites, getActivity());
		setListAdapter(_resultsAdapter);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.results_fragment, null);
		
		return v;	
	}
	
	public void onStart() {
		super.onStart();
		if (_websites.size() == 0) {
			int size = _websiteNames.length;
			for (int i = 0; i < size; i++) {
				if (_websiteNames[i] == null) {
					continue;
				}
				Website wb = null;
				if (_websiteNames[i].equals("TheVerge")) {
					wb = new TheVerge(_searchQuery);
				} else if (_websiteNames[i].equals("Amazon")) {
					wb = new Amazon(_searchQuery);
				} else if (_websiteNames[i].equals("BestBuy")) {
					wb = new BestBuy(_searchQuery);
				} else if (_websiteNames[i].equals("Gizmodo")) {
					wb = new Gizmodo(_searchQuery);
				} 
				if (wb != null) {
					System.out.println("Adding website and running");
					_websites.add(wb);
					GetWebsiteResults gWR = new GetWebsiteResults(getListView(), getActivity());
					_searchingWebsites.add(gWR);
					gWR.execute(wb);
				} else {
					//Toast.makeText(getActivity(), _websiteNames[i] + " is not available", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Website wb = _websites.get(position);
		if (wb.isReady()) {
			((ResultsActivity) getActivity()).onResultItemSelected(wb.getResultLinks());
		} else {
			Toast.makeText(getActivity(), "Kuwinda is loading your results", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		_lbm.unregisterReceiver(_updateWebsite);
		for (GetWebsiteResults gWR : _searchingWebsites) {
			gWR.cancel(true);
		}
	}
	
}
