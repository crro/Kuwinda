package mx.ait.crro.kuwindasearchapp.data.websites;

import java.io.IOException;
import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.search.Link;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TheVerge extends Website {
	
	public TheVerge(String sQuery) {
		super("TheVerge", 1, "http://www.theverge.com", "http://www.theverge.com/search?q={query}");
		String[] words = sQuery.split("\\s");
		StringBuilder sb = new StringBuilder("http://www.theverge.com/search?q=");
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(""))
				continue;
			sb.append(words[i]);
			if (!(i == words.length-1)) {
				sb.append("+");
			}
		}
		_searchQuery = sb.toString().toLowerCase();
		
		
	}

	public TheVerge(String name, int exCode, String url, String searchURL) {
		super(name, exCode, url, searchURL);
	}

	@Override
	public boolean getLinks(Document doc) {
		
		Elements divs = doc.select(".result");
		
		ArrayList<Link> links = new ArrayList<Link>();
		
		for (Element div : divs) {
			Elements el = div.select("a[href]");
			links.add(new Link(el.text(), el.attr("href")));
		}
		this.setResultLinks(links);
		return true;
		
	}

	@Override
	public boolean search() {
		Document doc;
		try {
			doc = Jsoup.connect(_searchQuery).get();
			return this.getLinks(doc);
			
		} catch (IOException e) {
			return false;
		}
		
	}

}
