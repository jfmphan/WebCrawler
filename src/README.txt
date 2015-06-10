The crawler max file size to crawl was default set to 1MB. Since, I do not recall instructions on setting the max file size, I left it at the deafult. 
Meaning, any link that took the crawler to a page larger than 1MB was not crawled. 
The links that were skipped were mainly PDF and slides that a professor had on their website.

The "stopWords.txt" is a text file that contained all the stop words. The program processes these words to filter them out from the commonWords.txt.
To make this work please put it outside the SRC folder. The file path I put in the code is the file path inside the project folder, outside of SRC and lin folders.