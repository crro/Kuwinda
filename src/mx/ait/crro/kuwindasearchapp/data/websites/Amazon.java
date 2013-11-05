package mx.ait.crro.kuwindasearchapp.data.websites;

import java.io.IOException;
import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.search.Link;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Amazon extends Website {
	

	public Amazon(String sQuery) {
		super("Amazon", 6, "http://www.amazon.com", "http://www.amazon.com/s/ref=nb_sb_ss_i_1_6?url=search-alias%3Daps&field-keywords={query}");
		String[] words = sQuery.split("\\s");
		StringBuilder sb = new StringBuilder("http://www.amazon.com/s/ref=nb_sb_ss_i_1_6?url=search-alias%3Daps&field-keywords=");
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(""))
				continue;
			sb.append(words[i]);
			if (!(i == words.length-1)) {
				sb.append("+");
			}
		}
		_searchQuery = sb.toString();
		System.out.println(_searchQuery);
	}
		
	public boolean getLinks(Document doc) {
		Elements divs = doc.select(".newaps");
		
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
