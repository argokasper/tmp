package com.eshop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebServlet("/SearchResultsServlet")
public class SearchResultsServlet extends HttpServlet{
	
	private static final long serialVersionUID = -5183243852995209003L;
	private final static String ACCESS_KEY = "AKIAJZSAO7I2UDWK7B7Q";
	private final static String SECRET_KEY = "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";
	
	private static List<Product> productsActive = new ArrayList<Product>();
    private static List<Product> productsPreloaded = new ArrayList<Product>();
    private static List<Product> productsBuffered = new ArrayList<Product>();
    private static final int extraItems = 3;
    private static String keyword;
    private static double requestProcessingTime;
    private static long searchResults;

    public static List<Product> getProductsActive() {
    	return productsActive;
    }
    
    public static Double getRequestProcessingTime() {
    	return requestProcessingTime;
    }
    
    public static long getSearchResults() {
    	return searchResults;
    }
    
	public void init(ServletConfig config) {
        System.out.println("Servlet has been initialized");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			parseResponse(request.getParameter("search"));
		} catch (InvalidKeyException | IllegalArgumentException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
		requestProcessingTime = 0; 
		productsActive.clear();
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void updateProductsLists() {
    	productsActive = productsPreloaded;
    }
	
	public static String formatTitle(String unformatted) {
		
		if(unformatted.length() > 100) {
			return unformatted.substring(0, 100) + "...";
		} else {
			return unformatted;
		}
		
	}
	
	 public static void parseResponse(String keywords) throws InvalidKeyException, IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException  {
		 SignedRequestsHelper helper = SignedRequestsHelper.getInstance("ecs.amazonaws.com", ACCESS_KEY, SECRET_KEY);
	      
	        for(int i = 1; i <= 3; i++) {
	        	Map<String, String> params = new HashMap<String, String>();
		        params.put("Service", "AWSECommerceService");
		        params.put("Version", "2011-08-01");
		        params.put("Operation", "ItemSearch");
		        params.put("ItemPage", String.valueOf(i));
		        params.put("VariationPage", "1");
		        params.put("SearchIndex", "All");
		        params.put("Keywords", keywords);
		        params.put("AssociateTag", "something");
		        params.put("ResponseGroup", "ItemAttributes");

		        String url = helper.sign(params);
		        try {
		            Document response = getResponse(url);
		            response.getDocumentElement().normalize();
		            //get 13 results in total and put them to productActive and productPreloaded list.
		            if(i == 2) {
		            	xmlParser(response, extraItems);
		            } else if (i == 3) {
		            	xmlParser(response, extraItems*2);
		            } else {
		            	xmlParser(response, i);
		            }
		        } catch (Exception ex) {
		            Logger.getLogger(SearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
		        }
	        }
	        System.out.println("Another search ->");
	        for(Product p:productsActive) {
	        	System.out.println(p.getTitle()+": "+p.getPrice()+"USD, link="+p.getItemLink());
	        }
	        System.out.println("Preloaded:");
	        for(Product p:productsPreloaded) {
	        	System.out.println(p.getTitle()+": "+p.getPrice()+"USD, link="+p.getItemLink());
	        }
	        
	 
	    }
	    
	    private static void xmlParser(Document response, int iterator) {
	    	NodeList operationRequestItems = response.getElementsByTagName("OperationRequest");
	    	requestProcessingTime += Double.valueOf(operationRequestItems.item(0).getLastChild().getTextContent());
	    	NodeList requestTag = response.getElementsByTagName("Request");
	    	searchResults = Long.valueOf(requestTag.item(0).getNextSibling().getTextContent());
	    	NodeList items = response.getElementsByTagName("Item");
	    	String title = "";
	    	String formattedPrice = "";
	    	String pageUrl = "";
	    	for(int j = 0; j < items.getLength(); j++) {
     		Node item = items.item(j);
     		Node attributes = item.getLastChild();
     		NodeList attributeList = attributes.getChildNodes();
     		for(int k = 0; k < attributeList.getLength(); k++) {
     			if(attributeList.item(k).getNodeName() == "Title") {
     				title = attributeList.item(k).getTextContent();
     			} else if (attributeList.item(k).getNodeName() == "ListPrice") {
     				String price = attributeList.item(k).getFirstChild().getTextContent();
     				formattedPrice = "0.00";
     				if(price.length() > 1) {
     					formattedPrice = price.substring(0, price.length()-2)+"."+price.substring(price.length()-2, price.length());
     				}
     				
     			}
     		}
     		NodeList itemProperties = item.getChildNodes();
     		for(int k = 0; k < itemProperties.getLength(); k++) {
     			if (itemProperties.item(k).getNodeName() == "DetailPageURL") {
     				pageUrl = itemProperties.item(k).getTextContent();
     			}
     		}
     		if(iterator == 1) {
     			productsActive.add(new Product(title, formattedPrice, pageUrl));
     		}
     		if(iterator == extraItems && j < extraItems) {
     			productsActive.add(new Product(title, formattedPrice, pageUrl));
     		}
     		if(iterator == extraItems && j >= extraItems) {
     			productsBuffered.add(new Product(title, formattedPrice, pageUrl));
     			if(j == extraItems) productsPreloaded = productsBuffered;
     		}
     		if (iterator == extraItems*2 && j < extraItems*2) {
     			
     			productsPreloaded.add(new Product(title, formattedPrice, pageUrl));
     		}
     		if(iterator == extraItems*2 && j >= extraItems*2) {
     			//productsBuffered.add(new Product(title, formattedPrice, pageUrl));
     		}
     		
     		
     	}
	    }

	    private static Document getResponse(String url) throws ParserConfigurationException, IOException, SAXException {
	        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc = builder.parse(url);
	        return doc;
	    }

}
