package mx.ait.crro.kuwindasearchapp.data.categories;

import java.util.ArrayList;
import java.util.Arrays;

public class Category {
	private String _name;
	private ArrayList<String> _websites;
	private String _keyword;
	private ArrayList<Integer> _webDBIds;
	private String[] _websitesStringArray;
	
	public Category(String name, String[] websites, String keyword) {
		_name = name;
		_websites = new ArrayList<String>(Arrays.asList(websites));
		_keyword = keyword;
		_websitesStringArray = websites;
	}
	
	
	public void setWebsiteDBIds(ArrayList<Integer> rowIds) {
		_webDBIds = rowIds;
	}
	
	public ArrayList<Integer> getWebsiteDBIds() {
		return _webDBIds;
	}

	public String getName() {
		return _name;
	}

	public ArrayList<String> getWebsites() {
		return _websites;
	}
	
	public String getWebsitesString() {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < _websites.size(); i++) {
			if (i == _websites.size()-1) {
				sb.append(_websites.get(i));
			} else {
				sb.append(_websites.get(i)+ ",");
			}
		}
		
		return sb.toString();
	}

	public String getKeyword() {
		return _keyword;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setWebsites(ArrayList<String> websites) {
		_websites = websites;
	}

	public void setKeyword(String keyword) {
		_keyword = keyword;
	}


	public String[] getWebsitesStringArray() {
		return _websitesStringArray;
	}


	public void setWebsitesStringArray(String[] websitesStringArray) {
		_websitesStringArray = websitesStringArray;
	}


}
