import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class FetchFiles {

	public static final String HOME_URL = "http://journals.plos.org";

	private static void download(String url, int count) throws MalformedURLException, IOException {
		String fileName = "/Users/patron/Downloads/htmls/" + count + ".html";
		BufferedReader br = new BufferedReader(new InputStreamReader(new URL(HOME_URL + url).openStream()));
		PrintWriter pw = new PrintWriter(fileName);
		String line;
		while ((line = br.readLine()) != null) {
			pw.println(line);
		}
		pw.close();
		br.close();
	}

	private static void extract(String fileName, PrintWriter writer) throws InterruptedException, IOException {
		
		//Document doc = Jsoup.connect(HOME_URL + url).get();
		File input = new File(fileName);
		Document doc = Jsoup.parse(input, "UTF-8");
		//Thread.sleep(3000);

		// Title
		String title = doc.title();
		System.out.println("PAPER TITLE: " + title);
		System.out.println();
		writer.println("PAPER TITLE: " + title);

		// Subject Areas
		Elements keywords = doc.getElementsByAttributeValue("name", "keywords");
		for (org.jsoup.nodes.Element keyword : keywords) {
			System.out.println("SUBJECT AREAS: " + keyword.attr("content"));
			writer.println("SUBJECT AREAS: " + keyword.attr("content"));
		}
		System.out.println();

		//Citation DOI
		Elements dois = doc.getElementsByAttributeValueContaining("name", "citation_doi");
		for (org.jsoup.nodes.Element doi : dois) {
			System.out.println("DOI: " + dois.attr("content"));
			writer.println("DOI: " + dois.attr("content"));
		}
		System.out.println();

		// Authors
		Elements authors = doc.getElementsByAttributeValue("class", "author-name");		
		System.out.println("AUTHORS: " + authors.text());
		writer.println("AUTHORS: " + authors.text());
		System.out.println();

		// Author's Affiliation
		Elements authorAffiliation = doc.getElementsByAttributeValueContaining("id", "authAffiliations-");
		System.out.println("AUTHORS AFFILIATIONS: " + authorAffiliation.text());
		writer.println("AUTHORS AFFILIATIONS: " + authorAffiliation.text());
		System.out.println();

		// Corresponding Author
		Elements correspondingAuthor = doc.getElementsByAttributeValueContaining("id", "authCorresponding-");
		System.out.println("CORRESPONDING AUTHORS: " + correspondingAuthor.text());
		writer.println("CORRESPONDING AUTHORS: " + correspondingAuthor.text());
		System.out.println();

		// Abstract
		Elements elementAbstracts = doc.getElementsByAttributeValueContaining("class", "abstract toc-section");
		System.out.println("PAPER ABSTRACT: " + elementAbstracts.select("p").text());
		writer.println("PAPER ABSTRACT: " + elementAbstracts.select("p").text());
		System.out.println();

		//Elements citationTitle = doc.getElementsByAttributeValueContaining("name", "citation_title");
		//System.out.println("CITATION TITLE: " + citationTitle.attr("content"));
		//System.out.println();

		// Citation details
		Elements itemProp = doc.getElementsByAttributeValueContaining("itemprop", "name");
		Elements journalTitle = doc.getElementsByAttributeValueContaining("name", "citation_journal_title");
		Elements date = doc.getElementsByAttributeValueContaining("name", "citation_date");
		Elements firstpage = doc.getElementsByAttributeValueContaining("name", "citation_firstpage");
		Elements issue = doc.getElementsByAttributeValueContaining("name", "citation_issue");
		Elements volume = doc.getElementsByAttributeValueContaining("name", "citation_volume");
		Elements issn = doc.getElementsByAttributeValueContaining("name", "citation_issn");
		Elements journalAbbrev = doc.getElementsByAttributeValueContaining("name", "citation_journal_abbrev");
		Elements publisher = doc.getElementsByAttributeValueContaining("name", "citation_publisher");
		Elements pdfUrl = doc.getElementsByAttributeValueContaining("name", "citation_pdf_url");
		for (org.jsoup.nodes.Element pdf : pdfUrl) {
			if (pdf.hasAttr("content")) {
				System.out.println("PDF: " + pdf.attr("content"));
				writer.println("PDF: " + pdf.attr("content"));
			}
		}
		System.out.println();

		//System.out.println("ITEMPROP: " + itemProp.attr("content"));
		//System.out.println();

		System.out.println("JOURNAL TITLE:" + journalTitle.attr("content"));
		writer.println("JOURNAL TITLE:" + journalTitle.attr("content"));
		System.out.println();

		System.out.println("DATE OF PUBLICATION: " + date.attr("content"));
		writer.println("DATE OF PUBLICATION: " + date.attr("content"));
		System.out.println();

		System.out.println("CITATION FIRST PAGE: " + firstpage.attr("content"));
		writer.println("CITATION FIRST PAGE: " + firstpage.attr("content"));
		System.out.println();

		System.out.println("ISSUE: " + issue.attr("content"));
		writer.println("ISSUE: " + issue.attr("content"));
		System.out.println();

		System.out.println("VOLUME: " + volume.attr("content"));
		writer.println("VOLUME: " + volume.attr("content"));
		System.out.println();

		System.out.println("ISSN: " + issn.attr("content"));
		writer.println("ISSN: " + issn.attr("content"));
		System.out.println();

		//System.out.println("ABBREV: " + journalAbbrev.attr("content"));
		//System.out.println();

		System.out.println("PUBLISHER: " + publisher.attr("content"));
		writer.println("PUBLISHER: " + publisher.attr("content"));
		System.out.println();

		System.out.println("PDF URL: " + pdfUrl.attr("content"));
		writer.println("PDF URL: " + pdfUrl.attr("content"));
		System.out.println();

		// Images
		Elements imageTags = doc.getElementsByAttributeValueContaining("class", "carousel-item lightbox-figure");
		for (org.jsoup.nodes.Element eachTag: imageTags) {
			Elements images = eachTag.getElementsByTag("img");
			for (org.jsoup.nodes.Element image : images) {
				// If alt is empty or null, add one to counter
				if(!image.attr("alt").equals("") || image.attr("alt") != null) {
					System.out.println("IMAGE TAG: " + image.attr("src"));
					writer.println("IMAGE TAG: " + image.attr("src"));
					System.out.println(" ALT: " + image.attr("alt"));
					writer.println(" ALT: " + image.attr("alt"));
				}	            
			}
		}
		System.out.println();

		// Large images
		Elements largeImages = doc.select("a[href*=/article/figure/image?size=large&amp;id=info:doi/10.1371/]");
		//Elements largeImages = doc.select("a.href");
		HashSet<String> mySet = new HashSet<String>();
		for (org.jsoup.nodes.Element largeImage : largeImages) {
			System.out.println(largeImage);
			mySet.add(largeImage.attr("href").toString());
		}
		System.out.println(mySet.toString());

		// References
		Elements references = doc.getElementsByAttributeValueContaining("name", "citation_reference");
		int i = 0;
		for (org.jsoup.nodes.Element reference : references) {
			i++;
			if (reference.hasAttr("content")) {
				System.out.println("REFERENCE" + i + ": " + reference.attr("content"));
				writer.println("REFERENCE" + i + ": " + reference.attr("content"));
			}
		}
		writer.println();
		writer.flush();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		
		/*
		
		// Download
		BufferedReader br = new BufferedReader(new FileReader("/Users/patron/Downloads/mainFileLinks500-560.txt"));
		String line;
		int i = 0;
		while ((line = br.readLine()) != null) {
			//for (int j = 1; j <= 20; j++) {
			//Document mainDoc = Jsoup.connect("http://journals.plos.org/plosone/browse/earth_sciences" + "?page=" + j).get();
			//Elements links = mainDoc.getElementsByAttributeValue("class", "article-url");
			//for (org.jsoup.nodes.Element link : links) {
			//System.out.println(link.attr("href"));
			System.out.println(line);
			try {
				download(line, i);
			}
			catch (IOException e) {
				System.out.println("Download failed for url " + i + " " + e.getMessage());
			}
			i++;
			if (i % 500 == 0) {
				Thread.sleep(10000);
			}
			//String str = link.attr("href");
			//Document doc = Jsoup.connect("http://journals.plos.org" + str).get();
		}
		*/
		
		//Extraction
		PrintWriter writer = new PrintWriter("/Users/patron/Downloads/mainFile.txt", "UTF-8");
		File folder = new File("/Users/patron/Downloads/htmls/");
		File[] listofFiles = folder.listFiles();
		for (File file : listofFiles) {
			if (file.isFile()) {
				System.out.println(file.getAbsolutePath());
				extract(file.getAbsolutePath(), writer);
			}
		}
		writer.close();
	}
}

