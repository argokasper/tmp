<%@page import="com.eshop.SearchResultsServlet"%>
<%@page import="com.eshop.Product"%>
<%@page import="NET.webserviceX.www.Currency"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
<title>Amazon Browser - Results</title>
</head>
<body>
<div class="wrapper">
	<header class="search-page-header">
		<a href="${pageContext.request.contextPath}" title="Search Amazon">
			<h1>Search Amazon products</h1>
		</a>
		
	</header>
	<div class="search-page-search-container">
		<select class="span2 change-currency">
			<%
				for(String key : Currency.getTable().keySet()) {
					if(key == "USD") {
					%>
						<option value="<%= key%>" selected><%= key%></option>
					<%
					} else {
					%>
						<option value="<%= key%>"><%= key%></option>
			<%		
					}
				}
			%>
		</select>
		<form class="form-search" action="${pageContext.request.contextPath}/results">
  			<div class="input-append">
    			<input type="text" class="span5 search-query" name="search" value="<%= !request.getParameter("search").isEmpty() ? request.getParameter("search") : "" %>" placeholder="Search a product">
    			<button type="submit" class="btn send-button">Search</button>
  			</div>
		</form>
	</div>
	<div class="search-results-container">
		<div>
			<span>Showing results <%= "1 - " + SearchResultsServlet.getProductsActive().size() %></span>
			<span>Search took <%= SearchResultsServlet.getRequestProcessingTime() %> seconds to find <%= SearchResultsServlet.getSearchResults() %> hits</span>
		</div>
	<% 
		for(Product p : SearchResultsServlet.getProductsActive()) {
	%>
		<a href="<%= p.getItemLink() %>" title="<%= p.getTitle() %>">
			<div class="item">
				<span class="item-title"><%= SearchResultsServlet.formatTitle(p.getTitle())%></span>
				<span class="item-price"><%= (p.getPrice() != "0.00") ? p.getPrice()+"USD" : "Free"%></span>
			</div>
		</a>
	<%
		}
	%>
	</div>
	<div class="pagination-container">
		<a href="${pageContext.request.contextPath}/results=pg_" title="Previous">Previous</a>
		<a href="${pageContext.request.contextPath}/results=pg_" title="Next">Next</a>
	</div>
</div>	
	<footer>
		<p>Created by Argo Käsper <%= Calendar.getInstance().get(Calendar.YEAR) %></p>
	</footer>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
	
	
</body>
</html>