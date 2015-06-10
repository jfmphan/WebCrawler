/*
 * Justin Phan 63777127
 * 
 * This class is created to manage all the writing answers into the text files.
 * Avoids copy pasting from console output into the give text files.
 */

package src.ir.assignments.three.helpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AnswerWriter 
{
	private BufferedWriter writer1;
	private BufferedWriter writer2;
	private BufferedWriter writer3;
	
	/*
	 * Initializes all the three writers that will write into separate text files.
	 */
	public AnswerWriter()
	{
		try
		{	
			writer1 = new BufferedWriter(new PrintWriter("answers.txt"));
			writer2 = new BufferedWriter(new PrintWriter("CommonWords.txt"));
			writer3 = new BufferedWriter(new PrintWriter("Subdomains.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Formats and writes answers to question 1, 2, 4 from the assignment documentation.
	 * 1) Print it took to crawl.
	 * 2) Unique pages in the domain.
	 * 4) The longest pages in text length along with it's URL.
	 */
	public void writeAnswers(long duration, int urls, int size, String url) 
	throws IOException
	{
		
		long second = (duration / 1000) % 60;
		long minute = (duration / (1000 * 60)) % 60;
		long hour = (duration / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		
		String s = "Time it took to crawl: " + time + "\n";
		s += "\nNumber of unique pages: " + urls + "\n"; 
		s += "\nLongest page is: " + url + "\n";
		s += "\nWith " + size + " words.\n";
		writer1.write(s);
	}
	
	/*
	 * Writes the subDomains into the file SubDomains
	 */
	public void writeDomains(ArrayList<Packet> urls)
	throws IOException
	{
		for(Packet p : urls)
		{
			String s = p.getUrl() + ", " + p.getLinks() + "\n";
			writer3.write(s);
		}
		
	}
	
	
	/*
	 * Writes the first 500 most frequent words into the file.
	 */
	public void writeWords(ArrayList<Word> words)
	throws IOException
	{	
		int size = words.size();
		int count = 0;
		
		if(size < 500)
		{
			count = size;
		}
		else
		{
			count = 500;
		}
		
		for(int i = 0; i< count; i++)
		{
			String s = words.get(i).getText() + ", " + words.get(i).getFrequency() +"\n";
			writer2.write(s);
		}
		
	}

	public void closeWriters()
	{
		try
		{
			writer1.close();
			writer2.close();
			writer3.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}
