import java.awt.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.opencsv.CSVWriter;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|dln|cln|hln|ktvi|tv|cnn|nasa" +
"|rm|smil|wmv|swf|wma|zip|rar|gz|php|iso|xml|txt|map))$");
	
	//private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?|ico))$");
	static int count=0;
	static int pagesfetched =0;
	public static Set<String> totalUniqueUrls = new HashSet<String>();
	public static Set<String> InUrls = new HashSet<String>();;
	public static Set<String> OutUrls = new HashSet<String>();
	public static ArrayList<String> totalUrls = new ArrayList<String>();
	CSVWriter writer = null;
	//CSVWriter writer2 = null;
	//CSVWriter writer3 = null;
	String rec = "";
	String [] record;
	/*@Override
    public synchronized void onStart() {
        // Do nothing by default
        // Sub-classed can override this to add their custom functionality
		String csv1 = "urls_CNN.csv";
		String csv2 = "fetch_CNN.csv";
		String csv3 = "visit_CNN.csv";
		try {
	 writer1 = new CSVWriter(new FileWriter(csv1,true));
	 writer2 = new CSVWriter(new FileWriter(csv2,true));
	 writer3 = new CSVWriter(new FileWriter(csv3,true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	@Override
	public synchronized void onBeforeExit() {
        // Do nothing by default
        // Sub-classed can override this to add their custom functionality
		try {
			
			writer2.close();
			writer3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }*/
	@Override
	public synchronized boolean shouldVisit(Page referringPage, WebURL url) {
		
		String href = url.getURL().toLowerCase(); 
		String csv = "urls_CNN.csv";
		totalUrls.add(href);
		totalUniqueUrls.add(href);
		if(href.startsWith("http://www.cnn.com"))
		{				
			InUrls.add(href);
				try {
					String rec = href+","+"OK";
					record = rec.split(",");
					 writer = new CSVWriter(new FileWriter(csv,true));
					 writer.writeNext(record);
					 writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
				
			      //String [] record = rec.split(",");
			      //Write the record to file
			      
					
		}
		else{
			
			OutUrls.add(href);
			
			try {
				String rec = href+","+"N_OK";
				record = rec.split(",");
				 writer = new CSVWriter(new FileWriter(csv,true));
				 writer.writeNext(record);
				 writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			return false;
		}
		
	
		
		/*if ( && (href.startsWith("http://www.cnn.com") || href.startsWith("https://www.cnn.com")))
		{
			return true;
		}*/
		return !FILTERS.matcher(href).matches() && (href.startsWith("http://www.cnn.com"));
	}
	
	@Override 
	public synchronized void visit(Page page) { 
		String url = page.getWebURL().getURL(); 
		url = url.replace(',', '-');
		//count++;
		//System.out.println("URL: " + url); 
		if (page.getParseData() instanceof HtmlParseData) 
		{ 
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); 
			String text = htmlParseData.getText(); 
			String html = htmlParseData.getHtml(); 
			Set<WebURL> links = htmlParseData.getOutgoingUrls(); 
			int fileSize = html.length();
			String fs = Integer.toString(fileSize);
			int outlinks = links.size();
			String Ol = Integer.toString(outlinks);
			String contentType = page.getContentType();
			//String csv = "visit_CNN.csv";				
				//CSVWriter writer = new CSVWriter(new FileWriter(csv,true));
				
			      String csv = "visit_CNN.csv";
			      try {
			    	  	String rec1 = url+","+fs+","+Ol+","+contentType;
			    	  	record = rec1.split(",");
						 writer = new CSVWriter(new FileWriter(csv,true));
						 writer.writeNext(record);
						 writer.close();
							} 
			      catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			      //Write the record to file
			      

			//System.out.println("Text length: " + text.length()); 
			//System.out.println("Html length: " + html.length()); 
			//System.out.println("Number of outgoing links: " + links.size());
			
			//System.out.println("num :" + count);
		}
		else{
			
			 String rec1 = url+","+Integer.toString(page.getContentData().length)+","+Integer.toString(0)+","+page.getContentType();
			record = rec1.split(",");
			String csv = "visit_CNN.csv";
			try {
				 writer = new CSVWriter(new FileWriter(csv,true));
				 writer.writeNext(record);
				 writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		
		
	}
	
	@Override
	protected synchronized void handlePageStatusCode(WebURL webUrl, int statusCode, 
			   String statusDescription) { 
			  	System.out.println(webUrl.getURL() + " " +statusCode);
			  	pagesfetched++;
			  	System.out.println("PagesFetched" + " " + pagesfetched);
			  	String url = webUrl.getURL().toString();
			  	String csv = "fetch_CNN.csv";
			  	try {
			  		 String rec2 = url+","+Integer.toString(statusCode);
				      record = rec2.split(",");
				      //Write the record to file
				      //write(record,writer);
					writer = new CSVWriter(new FileWriter(csv,true));
					writer.writeNext(record);
					 writer.close();
				} 
			  	catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  	
			 }
	
	/*public synchronized void write(String[] records, CSVWriter writer){
			
		writer.writeNext(records);
	
}*/
}
