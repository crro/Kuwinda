package mx.ait.crro.kuwindasearchapp.fragment;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.ResultsActivity;
import mx.ait.crro.kuwindasearchapp.adapter.LinksAdapter;
import mx.ait.crro.kuwindasearchapp.search.Link;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LinksFragment extends ListFragment{
	
	private ArrayList<Link> _links;
	LinksAdapter _linkAdapter;
	
	public static final String TAG = "LinksFragment";
	
	public static LinksFragment newInstance(ArrayList<Link> links) {
		LinksFragment result = new LinksFragment();
		
		Bundle args = new Bundle();
		args.putParcelableArrayList(KuwindaConstants.LINKS_BUNDLE_KEY, links);
		result.setArguments(args);
		
		return result;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		_links = args.getParcelableArrayList(KuwindaConstants.LINKS_BUNDLE_KEY);
		_linkAdapter = new LinksAdapter(_links, getActivity());
		setListAdapter(_linkAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		((ResultsActivity) getActivity()).onLinkItemSelected(_links.get(position).getUrl());
		
	}
	
}
