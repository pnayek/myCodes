import java.io.IOException;
import java.util.*;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class MyExtraction {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://journals.plos.org/plosone/article?id=10.1371%2Fjournal.pone.0151111").get();

		// Title
		String title = doc.title();
		System.out.println("PAPER TITLE: " + title);
		System.out.println();

		// Subject Areas
		Elements keywords = doc.getElementsByAttributeValue("name", "keywords");
		for (org.jsoup.nodes.Element keyword : keywords) {
			System.out.println("SUBJECT AREAS: " + keyword.attr("content"));
		}
		System.out.println();

		//Citation DOI
		Elements dois = doc.getElementsByAttributeValueContaining("name", "citation_doi");
		for (org.jsoup.nodes.Element doi : dois) {
			System.out.println("DOI: " + dois.attr("content"));
		}
		System.out.println();

		// Authors
		Elements authors = doc.getElementsByAttributeValue("class", "author-name");		
		System.out.println("AUTHORS: " + authors.text());
		System.out.println();

		// Author's Affiliation
		Elements authorAffiliation = doc.getElementsByAttributeValueContaining("id", "authAffiliations-");
		System.out.println("AUTHORS AFFILIATIONS: " + authorAffiliation.text());
		System.out.println();

		// Corresponding Author
		Elements correspondingAuthor = doc.getElementsByAttributeValueContaining("id", "authCorresponding-");
		System.out.println("CORRESPONDING AUTHORS: " + correspondingAuthor.text());
		System.out.println();

		// Abstract
		Elements elementAbstracts = doc.getElementsByAttributeValueContaining("class", "abstract toc-section");
		System.out.println("PAPER ABSTRACT: " + elementAbstracts.select("p").text());
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
			}
		}
		System.out.println();

		//System.out.println("ITEMPROP: " + itemProp.attr("content"));
		//System.out.println();

		System.out.println("JOURNAL TITLE:" + journalTitle.attr("content"));
		System.out.println();

		System.out.println("DATE OF PUBLICATION: " + date.attr("content"));
		System.out.println();

		System.out.println("CITATION FIRST PAGE: " + firstpage.attr("content"));
		System.out.println();

		System.out.println("ISSUE: " + issue.attr("content"));
		System.out.println();

		System.out.println("VOLUME: " + volume.attr("content"));
		System.out.println();

		System.out.println("ISSN: " + issn.attr("content"));
		System.out.println();

		//System.out.println("ABBREV: " + journalAbbrev.attr("content"));
		//System.out.println();

		System.out.println("PUBLISHER: " + publisher.attr("content"));
		System.out.println();

		System.out.println("PDF URL: " + pdfUrl.attr("content"));
		System.out.println();

		// Images
		Elements imageTags = doc.getElementsByAttributeValueContaining("class", "carousel-item lightbox-figure");
		for (org.jsoup.nodes.Element eachTag: imageTags) {
			Elements images = eachTag.getElementsByTag("img");
			for (org.jsoup.nodes.Element image : images) {
				// If alt is empty or null, add one to counter
				if(!image.attr("alt").equals("") || image.attr("alt") != null) {
					System.out.println("IMAGE TAG: " + image.attr("src"));
					System.out.println(" ALT: " + image.attr("alt"));
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
			}
		}


	}

}
