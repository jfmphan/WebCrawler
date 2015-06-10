/*
 * Justin Phan 63777127
 * 
 * Parses all the HTML and text of a page.
 * Places all the words in a Treemap to be returned for later use.
 */
package src.ir.assignments.three.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser 
{
	
	private Scanner scanner;
	private HashMap<String, String> stopMap;
	private TreeMap<String, Word> allWords;
	private String largestPageUrl;
	private int largestPage;
	
	
	public HTMLParser()
	{
		stopMap = new HashMap<String, String>();
		allWords = new TreeMap<String, Word>();
		largestPage = 0;

	}
	
	/*
	 * Add all the words form all subdomains into a tree map.
	 * Checks if it is a stop word by comparing it with a hash map of stop words first.
	 * If not in tree already and not a stop word, then add it to the tree as word as
	 * the value and the string as the key.
	 * If it is in the tree, then increment that word's frequency.
	 */
	public void parse(String html, String url)
	{
		scanner = new Scanner(html);
		
		int count = 0;
		
		while(scanner.hasNext())
		{
			String next = scanner.useDelimiter(("[.,:;{()}`\\[\\]_=~<>$#@^&/*%?\\d+\\-\\!\"| \t\n\r]+")).next();
			next = next.replaceAll("[^\\x20-\\x7e]", "");
			if(!stopMap.containsKey(next.toLowerCase()) && (next.trim().length() > 1) && !next.equals("'"))
			{
				Word word = allWords.get(next);
				count++;
				
				
				if(word == null)
				{
					Word newWord = new Word(next);
					allWords.put(next, newWord);
				}
				else
				{
					word.increment();
				}
					
			}
		}
		scanner.close();
		
		if(count > largestPage)
		{
			largestPage = count;
			largestPageUrl = url;
		}
			
	}

	/*
	 * Returns number of unique links in a given subDomain page 
	 */
	public int parseHtml(String html)
	{
		
		HashMap<String, String> map = new HashMap<String, String>();
			
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(html);
		while(m.find())
		{
			String url = m.group(1);
			map.put(url, url);
		}
		
		return map.size();
		
	}
	
	/*
	 * Initializes the hashmap of all the stop words.
	 */
	public void initialStopMap()
	{
		try
		{
			scanner = new Scanner(new BufferedReader(new FileReader("stopWords.txt")));
			
			while(scanner.hasNext())
			{
				String item = scanner.next();
				stopMap.put(item, item);
			}
			
			scanner.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Add words into a new tree, but with key values of frequencies and not strings.
	 * Have to iterate through the tree map.
	 * Sort the map and then return the first 500 words.
	 */
	public ArrayList<Word> sort()
	{
		
		ArrayList<Word> words = new ArrayList<Word>();
		
		for(Map.Entry<String, Word> entry : allWords.entrySet())
		{
			Word value = entry.getValue();
			words.add(value);
		}
		
		Collections.sort(words, new Comparator<Word>() {
			public int compare(Word w1, Word w2)
			{
				return w2.getFrequency() - w1.getFrequency();
			}});
			
		return  words;
		
	}
	
	public int getPageCount()
	{
		return largestPage;
	}
	
	public String getPage()
	{
		return largestPageUrl;
	}
	
}
