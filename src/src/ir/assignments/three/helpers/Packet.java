/*
 * Justin Phan 63777127
 * 
 * Small class to tie together a subDomain and how many unique links it contains.
 */
package src.ir.assignments.three.helpers;

public class Packet 
{

	private String url;
	private int links;
	
	public Packet(String url, int links)
	{
		this.url = url;
		this.links = links;
				
	}
	
	public String getUrl()
	{
		return url;	
	}
	
	public int getLinks()
	{
		return links;
	}
	
}
