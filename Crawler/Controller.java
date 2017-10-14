import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String crawlStorageFolder = "/Users/Bhavana/Desktop/Spring_Sem/IR/Assignment_2"; 
		int numberOfCrawlers = 7; 
		int MaxDepthOfCrawling = 16;
		int MaxPagesToFetch = 20000;
		int politenessDelay = 200;
		CrawlConfig config = new CrawlConfig(); 
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(MaxDepthOfCrawling);
		config.setMaxPagesToFetch(MaxPagesToFetch);
		config.setPolitenessDelay(politenessDelay);
		config.setIncludeBinaryContentInCrawling(true);
		
		PageFetcher pageFetcher = new PageFetcher(config); 
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); 
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher); 
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		//controller.addSeed("https://www.cnn.com/");
		controller.addSeed("http://www.cnn.com/");
		controller.start(MyCrawler.class, numberOfCrawlers);
		
		MyCrawler c = new MyCrawler();
		System.out.println("Total Urls:"+" "+c.totalUrls.size());
		System.out.println("Total Unique Urls:"+" "+c.totalUniqueUrls.size());
		System.out.println("Unique Urls in the domain:"+" "+c.InUrls.size());
		System.out.println("Unique Urls in the outside the domain:"+" "+c.OutUrls.size());
	

}
}
