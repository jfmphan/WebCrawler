/*
 * Justin Phan 63777127
 *
 * Extends the WebCrawler.
 * Does most of the crawling work.
 * Contains lists of urls that it has crawled.
 *
 */
package src.ir.assignments.three;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import src.ir.assignments.three.helpers.Packet;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;


public class Crawler extends WebCrawler
{
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private static ArrayList<Packet> urls;
	private static ArrayList<String> crawledUrls;

	public Crawler()
	{
		urls = new ArrayList<Packet>();
		crawledUrls = new ArrayList<String>();
	}


	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 *
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
	 */
	public static Collection<String> crawl(String seedURL)
	{
		String USERAGENT = "";
		String STORAGEFOLDER = "C:\\asp\\crawler4jStorage";
		int POLITENESSDELAY = 300;

		CrawlConfig config = new CrawlConfig();
		config.setUserAgentString(USERAGENT);
		config.setCrawlStorageFolder(STORAGEFOLDER);
		config.setPolitenessDelay(POLITENESSDELAY);
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		try
		{
			Controller control = new Controller(config, pageFetcher, robotstxtServer);
			control.addSeed(seedURL);

			control.start(Crawler.class, 1);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return crawledUrls;
	}


	 @Override
     public boolean shouldVisit(WebURL url)
	 {
             String href = url.getURL().toLowerCase();
             boolean visit = false;

             if(!FILTERS.matcher(href).matches())
             {
            	 visit = true;

             }

             return visit;
	 }


	 @Override
	 public void visit(Page page)
	 {
		 String url = page.getWebURL().getURL();
		 crawledUrls.add(url);

		 if(page.getParseData() instanceof HtmlParseData)
		 {
			 HtmlParseData data = (HtmlParseData)page.getParseData();
			 String text = data.getText();
			 String html = data.getHtml();

			 Controller.parser.parse(text, url);
			 int numberOfLinks = Controller.parser.parseHtml(html);

			 urls.add(new Packet(url, numberOfLinks));

		 }
	 }


	 /*
	  * Sorts the URLs into alphabetical order.
	  */
	 private static void sortURLS()
	 {
		 Collections.sort(urls, new Comparator<Packet>() {
				public int compare(Packet w1, Packet w2)
				{
					return w1.getUrl().compareTo(w2.getUrl());
				}});
	 }

	 /*
	  * Removes the seed URL and returns all the urls it has crawled.
	  */
	 public static ArrayList<Packet> getURLS()
	 {
		 urls.remove(0);
		 sortURLS();
		 return urls;
	 }

}
