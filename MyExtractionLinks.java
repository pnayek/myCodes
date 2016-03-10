import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class MyExtractionLinks {

	public static void main(String[] args) throws IOException, InterruptedException {
		PrintWriter writer = new PrintWriter("/Users/patron/Downloads/mainFileLinks200-250.txt", "UTF-8");
		for (int j = 200; j <= 250; j++) {
			
			Document mainDoc = Jsoup.connect("http://journals.plos.org/plosone/browse/earth_sciences" + "?page=" + j).get();
			Elements links = mainDoc.getElementsByAttributeValue("class", "article-url");
			for (org.jsoup.nodes.Element link : links) {
				System.out.println(link.attr("href"));
				String str = link.attr("href");
				writer.println(str);	
			}
			writer.flush();
			Thread.sleep(50000);
		}
		writer.close();
		
	}

}
