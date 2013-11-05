package mx.ait.crro.kuwindasearchapp.data.websites;

import java.io.IOException;
import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.search.Link;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BestBuy extends Website{
	
	public BestBuy(String sQuery) {
		super("BestBuy", 8, "http://www.bestbuy.com", "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=ISO-8859-1&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=all+Categories&ks=960&fs=saas&saas=saas&st={query}");
		String[] words = sQuery.split("\\s");
		StringBuilder sb = new StringBuilder("http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=ISO-8859-1&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=all+Categories&ks=960&fs=saas&saas=saas&st=");
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

	public boolean getLinks(Document doc) {
		Elements divs = doc.select(".name");
		
		ArrayList<Link> links = new ArrayList<Link>();
		
		for (Element div : divs) {
			
			Elements el = div.select("a[href]");
			links.add(new Link(el.text(), "http://www.bestbuy.com" + el.attr("href")));
			
		}
		this.setResultLinks(links);
		return true;
	}

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
