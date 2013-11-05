package mx.ait.crro.kuwindasearchapp;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.search.Link;

public interface IResultsListFragment {
	public void onResultItemSelected(ArrayList<Link> links);
	public void onLinkItemSelected(String url);
}
