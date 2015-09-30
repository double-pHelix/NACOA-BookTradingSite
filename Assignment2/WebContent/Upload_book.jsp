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
   
    <!-- - This is default -->
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

   	<title>Upload Book</title>

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
      
      <c:choose>
        <c:when test="${sessionScope.upload_success == true}">
          <h2>Your book has been uploaded successfully</h2>
          <a href="${pageContext.request.contextPath}/upload_book" class="btn btn-default" role="button">Upload Another</a>
          <br> <br>
          <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Main</a>
        </c:when>
        <c:otherwise>
         
          <h1>Selling a Book</h1>
          <h4> Enter in the details of the book into the form to sell on NACOA</h4>
		  <font color="red">${sessionScope.upload_message}</font>
          <form action="${pageContext.request.contextPath}/upload_book" method="POST"> 
            <input type="hidden" name="uploading" value="true">
            
            <div class="input-group">
              <input type="hidden" name="user_id" value="${sessionScope.user_id}">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Title" name="title">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Author" name="author">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Picture (URL)" name="picture">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Publisher" name="publisher">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Date of Publication (YYYY-MM-DD)" name="dateofpublication">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Pages (number)" name="pages">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="ISBN" name="isbn">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Genre" name="genre">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Price ($)" name="price">
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Description" name="description">
            </div><!-- /input-group -->
          <br>
              <div class="input-group-btn">
                <button class="btn btn-default" type="submit"><b>Sell</b></button>
              </div><!-- /btn-group -->
                
            </div><!-- /input-group -->
          </form>
        
        </c:otherwise> 
      </c:choose>
      
        
             

            
      </div>
  
   
    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>