/*
 * Justin Phan 63777127
 * 
 * Extends the CrawlController
 * Runs the crawler and contains a duration time and a list of all the words from the crawler.
 */
package src.ir.assignments.three;

import java.util.ArrayList;

import src.ir.assignments.three.helpers.HTMLParser;
import src.ir.assignments.three.helpers.Word;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller extends CrawlController
{
	private CrawlConfig config;
	private int numberOfCrawlers;
	private int pagesToFetch;
	private long duration;
	protected static HTMLParser parser;
	private ArrayList<Word> words;
	
	
	public Controller(CrawlConfig config, PageFetcher pageFetcher,RobotstxtServer robotstxtServer) 
	throws Exception 
	{
		super(config, pageFetcher, robotstxtServer);
		this.config = config;
		numberOfCrawlers = 1;
		pagesToFetch = 100;
		parser = new HTMLParser();
		parser.initialStopMap();
		duration = 0;
		
	}
	
	/*
	 * Starts the crawler
	 */
	public void go()
	{
		config.setMaxPagesToFetch(pagesToFetch);	
		
		long startTime = System.currentTimeMillis();
		start(Crawler.class, numberOfCrawlers);
		long endTime = System.currentTimeMillis();
		
		duration = endTime - startTime;
		
		words = parser.sort();	
		
	}
	
	
	public void setCrawlers(int numberOfCrawlers)
	{
		this.numberOfCrawlers = numberOfCrawlers;
	}
	
	public long getCrawlTime()
	{
		return duration;
	}
	
	public ArrayList<Word> getWords()
	{
		return words;
	}

}
