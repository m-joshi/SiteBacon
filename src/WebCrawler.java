import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebCrawler
{
	private HashSet<String> links;
	private static final int MAX_DEPTH = 2;
	
	public WebCrawler()
	{
		links = new HashSet<String>();
	}
	
	public void getPageLinks(String URL, int depth)
	{
		if((!links.contains(URL)) && (depth < MAX_DEPTH))
		{
			try
			{
				if(links.add(URL))
					System.out.println(URL + " depth = " + depth);
				
				Document document = Jsoup.connect(URL).get();
				Elements linksOnPage = document.select("a[href]");
				
				depth++;
				for(Element page : linksOnPage)
				{
					getPageLinks(page.attr("abs:href"), depth);
				}
			}catch(IOException e) {
				System.err.println("For " + URL + " : " + e.getMessage());
			}
		}
	}
	
	
	public static void main(String args[])
	{
		WebCrawler webCrawler = new WebCrawler();
		webCrawler.getPageLinks("http://www.mkyong.com", 0);
	}
}
