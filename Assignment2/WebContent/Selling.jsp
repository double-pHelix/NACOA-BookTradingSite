<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<%@page import="java.util.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

   	<title>My Books</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/customBootstrap.css" rel="stylesheet">

    
    <script type="text/JavaScript" src="/js/sha512.js"></script> 
    <script type="text/JavaScript" src="/js/forms.js"></script>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.min.js"></script> 
    <script type="text/JavaScript" src="/js/popup.js"></script>

    <script src="http://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

  
  </head>

  <body>
    <div class="container">
    
    <jsp:include page="/topBanner.jsp" /> 
    <div class="jumbotron">
    <h1>My Books</h1>
      <h2>Currently Selling</h2>
      <c:choose>
      <c:when test="${not empty sessionScope.sellingList}">
        <form name="articles_option" action="" method="POST">
          <input type="hidden" name="remove_cart" value="yes">
          <input type="hidden" name="user_id" value="${sessionScope.user_id}">
          <input type="hidden" name="num_items" value="${fn:length(sessionScope.sellingList)}">
          <div class="content">
          <table class="table table-bordered"> 
            <tr class="active">
              <td scope="col"><b>TITLE</b></td>
              <td scope="col"><b>AUTHOR</b></td>
              <td scope="col"><b>ISBN</b></td>
              <td scope="col"><b>PRICE</b></td>
              <td scope="col"><b>REMOVE</b></td>
            </tr>

              <c:forEach var="entry" items="${sessionScope.sellingList}" varStatus="varStatus" >
                 
                <tr class="active">
                  <td class="active">${entry.booktitle}</td>
                  <td class="success">${entry.author}</td>
                  <td class="warning">${entry.isbn}</td>
                  <td class="danger">$${entry.price}</td>
                  
                  <td class="info"><center> <input type="checkbox" name="entry${entry.bookID}" value="set" style="width: 20px;height: 20px;"> </center></td>
                </tr>
                
              </c:forEach>
              
              <tr class="active">
                <td></td><td></td><td></td><td></td>
                <td>
                  <center><input class="btn btn-xs btn-danger" type="submit" name="delete_article" value="Stop Selling"></center> 
                </td>
              
              </tr>
              
              
          </table>
          </div>
         
        </form>
        
      
       	<% /*NOT SURE IF NEEDED!!
       	<form name="checkout_book" action="" method="POST">
      		<input type="hidden" name="user_id" value="${sessionScope.user_id}">
       		<a href="${pageContext.request.contextPath}/selling" class="btn btn-info" role="button">Remove Book</a>
       	</form> */%>
      </c:when> 

    <c:otherwise>
      <h2>No Books on Sale!</h2>
      <a href="${pageContext.request.contextPath}/upload_book" class="btn btn-info" role="button">Sell a Book</a>
      <br><br>
      <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
    </c:otherwise>
    </c:choose>
    
    <c:choose>
      <c:when test="${not empty sessionScope.pausedList}">
	   <hr/>
	   <h2>Paused Selling</h2>
        <form name="articles_option" action="" method="POST">
          <input type="hidden" name="remove_cart" value="yes">
          <input type="hidden" name="user_id" value="${sessionScope.user_id}">
          <input type="hidden" name="num_items" value="${fn:length(sessionScope.pausedList)}">
          <div class="content">
          <table class="table table-bordered"> 
            <tr class="active">
              <td scope="col"><b>TITLE</b></td>
              <td scope="col"><b>AUTHOR</b></td>
              <td scope="col"><b>ISBN</b></td>
              <td scope="col"><b>PRICE</b></td>
              <td scope="col"><b>REMOVE</b></td>
            </tr>

              <c:forEach var="paused" items="${sessionScope.pausedList}" varStatus="varStatus" >
                 
                <tr class="active">
                  <td class="active">${paused.booktitle}</td>
                  <td class="success">${paused.author}</td>
                  <td class="warning">${paused.isbn}</td>
                  <td class="danger">$${paused.price}</td>
                  
                  <td class="info"><center> <input type="checkbox" name="paused${paused.bookID}" value="set" style="width: 20px;height: 20px;"> </center></td>
                </tr>
                
              </c:forEach>
              
              <tr class="active">
                <td></td><td></td><td></td><td></td>
                <td>
       			  <input type="hidden" name="user_id" value="${sessionScope.user_id}">
                  <center><input class="btn btn-xs btn-success" type="submit" name="delete_article" value="Resume Selling"></center> 
                </td>
              
              </tr>
              
              
          </table>
          </div>
         
        </form>
        
      
       	<% /*NOT SURE IF NEEDED!!
       	<form name="checkout_book" action="" method="POST">
      		<input type="hidden" name="user_id" value="${sessionScope.user_id}">
       		<a href="${pageContext.request.contextPath}/selling" class="btn btn-info" role="button">Remove Book</a>
       	</form> */%>
      </c:when> 

    <c:otherwise>
    
    <!-- Do Nothing -->
    
    </c:otherwise>
    </c:choose>
      </div>
  
   
    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
