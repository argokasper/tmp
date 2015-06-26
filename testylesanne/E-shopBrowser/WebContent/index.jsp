<%@page import="com.eshop.Tester"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<title>Amazon Browser</title>
</head>
<body>
	<header class="header-container">
		<h1>Now search more quickly! All in one place.</h1>
	</header>
	<div class="search-container">
		<form class="form-search">
  			<div class="input-append">
    			<input type="text" class="span5 search-query" name="search" placeholder="Search a product">
    			<button type="submit" class="btn send-button">Search</button>
  			</div>
		</form>
	</div>
	<div class="search-results-container">
		<%
			String search = request.getParameter("search");
			Tester.addList(search);
			for(String s:Tester.getSearches()) {
		%>
		<p><%= s %></p>
		<%
			}
		%>
	</div>
	
</body>
</html>