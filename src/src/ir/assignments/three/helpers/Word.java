/*
 * Justin Phan 63777127
 * 
 * Small class to encapsulate the String value and how many times it shows up during the crawl.
 */
package src.ir.assignments.three.helpers;

public class Word 
{
	private String text;
	private int frequency;
	
	public Word(String text)
	{
		this.text = text;
		frequency = 1;
	}
	
	public void increment()
	{
		frequency++;
	}
	
	public int getFrequency()
	{
		return frequency;
	}
	
	public String getText()
	{
		return text;
	}
	
}
