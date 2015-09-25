<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <!--  Remember to include these taglibs in every page... oh yeah just remembered -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<%@page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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

   	<title>Your Cart</title>

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
  
  <!-- I separated the top menu thingy into a diff jsp -->
    <jsp:include page="/topBanner.jsp" />
    
    <h1>Your Shopping Cart</h1>
    
    <c:choose>
      <c:when test="${not empty sessionScope.shoppingCart}">
        <form name="articles_option" action="" method="POST">
          <input type="hidden" name="remove_cart" value="yes">
          <input type="hidden" name="user_id" value="${sessionScope.user_id}">
          <input type="hidden" name="num_items" value="${fn:length(sessionScope.shoppingCart)}">
          <!-- We must display the results of there are any -->
          <div class="content">
          <table class="table table-bordered"> 
            <tr class="active">
              <td scope="col"><b>TITLE</b></td>
              <td scope="col"><b>AUTHOR</b></td>
              <td scope="col"><b>ISBN</b></td>
              <td scope="col"><b>PRICE</b></td>
              <td scope="col"><b>REMOVE</b></td>
            </tr>

              <c:forEach var="entry" items="${sessionScope.shoppingCart}" varStatus="varStatus" >
  
                <!--  set for each of these entries some way of id to add to cart later -->
                 
                <tr class="active">
                  <td class="active">${entry.title}</td>
                  <td class="success">${entry.authors}</td>
                  <td class="warning">${entry.isbn}</td>
                  <td class="danger">${entry.price}</td>
                  
                  <td class="info"><center> <input type="checkbox" name="entry${entry.bookID}" value="set" style="width: 20px;height: 20px;"> </center></td>
                </tr>
                
              </c:forEach>
              
              <tr class="active">
                <td></td><td></td><td></td><td></td>
                <td>
                  <center><input class="btn btn-xs btn-danger" type="submit" name="delete_article" value="Remove from Cart"></center> 
                </td>
              
              </tr>
              
              
          </table>
          </div>
          
          <a href="${pageContext.request.contextPath}/checkOut" class="btn btn-info" role="button">Purchase Books</a>
          
          <br>
        </form> 
      </c:when> 

    <c:otherwise>
     
      <!-- Display no results message -->
      <h2>Shopping Cart is Empty!</h2>
      <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      
    </c:otherwise>
    </c:choose>
    
    
    <footer class="footer">
      <p>&copy; Felix Yuen Dao Phu 2015</p>
    </footer>
    </div> <!-- /container -->
  </body>
</html>