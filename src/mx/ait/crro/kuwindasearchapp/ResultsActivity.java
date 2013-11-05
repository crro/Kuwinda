package mx.ait.crro.kuwindasearchapp;

import java.util.ArrayList;
import java.util.HashMap;

import mx.ait.crro.kuwindasearchapp.data.websites.Gizmodo;
import mx.ait.crro.kuwindasearchapp.data.websites.TheVerge;
import mx.ait.crro.kuwindasearchapp.data.websites.Website;
import mx.ait.crro.kuwindasearchapp.fragment.LinksFragment;
import mx.ait.crro.kuwindasearchapp.fragment.ResultsFragment;
import mx.ait.crro.kuwindasearchapp.fragment.WebFragment;
import mx.ait.crro.kuwindasearchapp.search.GetWebsiteResults;
import mx.ait.crro.kuwindasearchapp.search.Link;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ResultsActivity extends FragmentActivity implements IResultsListFragment {
	private HashMap<String, Link> _results;
	private String[] _websiteNames;
	private String _searchQuery;
	private ListView _lv;
	private FragmentManager _fm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		Intent i = getIntent();
		_searchQuery = i.getStringExtra(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY);
		_websiteNames = i.getStringArrayExtra(KuwindaConstants.WEBSITES_BUNDLE_KEY);
		
		
		_fm = getSupportFragmentManager();
		FragmentTransaction ft = _fm.beginTransaction();
		ft.replace(R.id.ResultsFragmentContent, ResultsFragment.newInstance(_searchQuery, _websiteNames), LinksFragment.TAG);
		ft.commit();
		
	}

	@Override
	public void onResultItemSelected(ArrayList<Link> links) {
		_fm = getSupportFragmentManager();
		FragmentTransaction ft = _fm.beginTransaction();
		ft.replace(R.id.ResultsFragmentContent, LinksFragment.newInstance(links), LinksFragment.TAG);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onLinkItemSelected(String url) {
		_fm = getSupportFragmentManager();
		FragmentTransaction ft = _fm.beginTransaction();
		ft.replace(R.id.ResultsFragmentContent, WebFragment.newInstance(url), LinksFragment.TAG);
		ft.addToBackStack(null);
		ft.commit();
	}
}
