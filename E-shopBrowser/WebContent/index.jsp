<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
<title>Amazon Browser</title>
</head>
<body>
<div class="wrapper">
	<header class="header-container">
		<h1>Now search more quickly! All in one place.</h1>
	</header>
	<div class="search-container">
		<form class="form-search" action="${pageContext.request.contextPath}/results">
  			<div class="input-append">
    			<input type="text" class="span5 search-query" name="search" placeholder="Search a product">
    			<button type="submit" class="btn send-button">Search</button>
  			</div>
		</form>
	</div>
	
</div>
	<footer>
		<p>Created by Argo Käsper <%= Calendar.getInstance().get(Calendar.YEAR) %></p>
	</footer>

</body>
</html>