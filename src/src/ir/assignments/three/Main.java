/*
 * Justin Phan 63777127
 * 
 * Main file that contains the main method.
 */

package src.ir.assignments.three;


import java.io.IOException;
import java.util.ArrayList;

import src.ir.assignments.three.helpers.AnswerWriter;
import src.ir.assignments.three.helpers.HTMLParser;
import src.ir.assignments.three.helpers.Packet;
import src.ir.assignments.three.helpers.Word;
import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.*;
import edu.uci.ics.crawler4j.robotstxt.*;

public class Main 
{

	private static final String USERAGENT = "UCI Inf141-CS121 crawler 63777127";	
	private static final String STORAGEFOLDER = "C:\\asp\\crawler4jStorage";
	private static final int POLITENESSDELAY = 300;
	private static final String SEED = "http://www.ics.uci.edu/";
	
	public static void main(String args[])
	{
		run();		
	}
	
	public static void run()
	{
		CrawlConfig config = new CrawlConfig();
		config.setUserAgentString(USERAGENT);
		config.setCrawlStorageFolder(STORAGEFOLDER);
		config.setPolitenessDelay(POLITENESSDELAY);
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		System.out.println(config);
		
		try
		{
			Controller control = new Controller(config, pageFetcher, robotstxtServer);
			control.addSeed(SEED);
			AnswerWriter writer = new AnswerWriter();
			control.go();
			
			ArrayList<Packet> urls = Crawler.getURLS();
			long duration = control.getCrawlTime();
			HTMLParser parser = Controller.parser;
			ArrayList<Word> words = control.getWords();
			
			try
			{
				int size = urls.size();
				int count = parser.getPageCount();
				String page = parser.getPage();
				
				writer.writeAnswers(duration, size, count, page);
				writer.writeDomains(urls);
				writer.writeWords(words);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		
			writer.closeWriters();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
