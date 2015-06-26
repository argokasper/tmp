package com.eshop;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;
import com.ECS.client.jax.AwsHandlerResolver;
import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemLookup;
import com.ECS.client.jax.ItemLookupRequest;
import com.ECS.client.jax.ItemLookupResponse;
import com.ECS.client.jax.Items;

public class SearchResults {
	private final static String ACCESS_KEY = "AKIAJZSAO7I2UDWK7B7Q";
	private final static String SECRET_KEY = "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";

	 public static void main(String[] args) {
		    System.out.println("API Test started");

		    AWSECommerceService service = new AWSECommerceService();
		    service.setHandlerResolver(new AwsHandlerResolver(SECRET_KEY));  // important
		    AWSECommerceServicePortType port = service.getAWSECommerceServicePort();

		    ItemLookupRequest itemLookup = new ItemLookupRequest();
		    itemLookup.setIdType("ASIN");
		    itemLookup.getItemId().add("B000RE216U");

		    ItemLookup lookup = new ItemLookup();
		    lookup.setAWSAccessKeyId(ACCESS_KEY); // important
		    lookup.getRequest().add(itemLookup);

		    ItemLookupResponse response = port.itemLookup(lookup);

		    String r = response.toString();
		    System.out.println("response: " + r);   
		    System.out.println("API Test stopped");
		  
	/*
		// Set the service:
		com.ECS.client.jax.AWSECommerceService service = new com.ECS.client.jax.AWSECommerceService();

		//Set the service port:
		com.ECS.client.jax.AWSECommerceServicePortType port = service.getAWSECommerceServicePort();

		//Get the operation object:
		com.ECS.client.jax.ItemSearchRequest itemRequest = new com.ECS.client.jax.ItemSearchRequest();

		//Fill in the request object:
		itemRequest.setSearchIndex("Books");
		itemRequest.setKeywords("dog");
		
		com.ECS.client.jax.ItemSearch ItemElement= new com.ECS.client.jax.ItemSearch();
		ItemElement.setAWSAccessKeyId(ACCESS_KEY);
		ItemElement.getRequest().add(itemRequest);

		//Call the Web service operation and store the response
		//in the response object:
		com.ECS.client.jax.ItemSearchResponse
		    response = port.itemSearch(ItemElement);
		System.out.println("response:" + response.toString());
	*/
		// Get the Title names of all the books for all the items returned in the response
	    for (Items itemList : response.getItems()) {
	        for (Item item : itemList.getItem()){
	            System.out.println("Book Name: " +
	            item.getItemAttributes().getTitle());
	        }
	    }
	}
	

	
	
}
