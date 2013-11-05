package mx.ait.crro.kuwindasearchapp.data.websites;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.search.Link;

import org.jsoup.nodes.Document;

public abstract class Website {
	protected String _name;
	private int _executionCode;
	protected String _url;
	protected String _searchURL;
	protected String _searchQuery;
	protected ArrayList<Link> _resultLinks;
	protected Document _doc;
	private boolean _isReady;
	
	public Website(String name, int exCode, String url, String searchURL) {
		_name = name;
		_executionCode = exCode;
		_url = url;
		setSearchURL(searchURL);
		_isReady = false;
	}

	public String getURL() {
		return _url;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public int getExecutionCode() {
		return _executionCode;
	}

	public void setExecutionCode(int executionCode) {
		this._executionCode = executionCode;
	}

	public void setURL(String url) {
		this._url = url;
	}

	public Document getDoc() {
		return _doc;
	}

	public void setDoc(Document doc) {
		_doc = doc;
	}

	public abstract boolean getLinks(Document doc);
	
	//makes the connection and returns the result from getLinks();
	public abstract boolean search();

	public String getSearchURL() {
		return _searchURL;
	}

	public void setSearchURL(String searchURL) {
		_searchURL = searchURL;
	}

	public String getSearchQuery() {
		return _searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		_searchQuery = searchQuery;
	}

	public ArrayList<Link> getResultLinks() {
		return _resultLinks;
	}

	public void setResultLinks(ArrayList<Link> resultLinks) {
		_resultLinks = resultLinks;
	}

	public synchronized boolean isReady() {
		return _isReady;
	}

	public synchronized void setReady(boolean isReady) {
		_isReady = isReady;
	}

}
