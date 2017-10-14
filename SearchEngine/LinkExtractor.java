import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class LinkExtractor {

	public static void main(String[] args) throws Exception{
		
		
		BufferedReader br1 = new BufferedReader(new FileReader("/Users/Bhavana/Desktop/Spring_Sem/IR/Assignment_4/CNNData/mapCNNDataFile.csv"));
		BufferedReader br2 = new BufferedReader(new FileReader("/Users/Bhavana/Desktop/Spring_Sem/IR/Assignment_4/CNNData/mapCNNDataFile.csv"));
	    String line1 =  null;
	    String line2 =  null;
	    HashMap<String,String> fileUrlMap = new HashMap<String, String>();
	    HashMap<String,String> UrlFileMap = new HashMap<String, String>();
	    Set<String> edges = new HashSet<String>();
	    FileWriter writer = new FileWriter("edgeList.txt",true);
	    BufferedWriter bufferedWriter = new BufferedWriter(writer);
	    while((line1=br1.readLine())!=null){
	        String str[] = line1.split(",");
	        for(int i=0;i<str.length;i++){
	            fileUrlMap.put(str[0], str[1]);
	            UrlFileMap.put(str[1], str[0]);
	        }
	    }
	    while((line2=br2.readLine())!=null){
	        String str1[] = line2.split(",");
	        for(int i=0;i<str1.length;i++){
	            //fileUrlMap.put(str[0], str[1]);
	            UrlFileMap.put(str1[1], str1[0]);
	        }
	    }
	    //System.out.println(fileUrlMap.get("08b335e3-1e03-4f13-9076-e169c5a7229d.html"));
	   // System.out.println(UrlFileMap);
		
		File dir = new File("/Users/Bhavana/Desktop/Spring_Sem/IR/Assignment_4/CNNData/CNNDownloadData/");
		for(File file : dir.listFiles()){
		Document doc = Jsoup.parse(file,"UTF-8",fileUrlMap.get(file.getName()));
		Elements links = doc.select("a[href]");
		
		for(Element link : links){
			
			String url = link.attr("abs:href").trim();
			if(UrlFileMap.containsKey(url)){
				
				edges.add(file.getName() + " " + UrlFileMap.get(url));
				
			}
			
		}
	}
		
		for(String s : edges){
			
			bufferedWriter.write(s);
			bufferedWriter.newLine();
			
		}
		
		
		bufferedWriter.close();
		
	}
}
