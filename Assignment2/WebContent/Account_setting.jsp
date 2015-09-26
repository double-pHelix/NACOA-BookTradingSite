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

   	<title>Account Details</title>

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
      
      <c:choose>
        <c:when test="${sessionScope.logged_in == true}">
          <h2>Change Account Details</h2> 
          <form action="${pageContext.request.contextPath}/updacc" method="POST"> 
            <input type="hidden" name="username" value="${sessionScope.username}">
            
            <div class="input-group">
              <input type="hidden" name="user_id" value="${sessionScope.user_id}">
   		  	  <label style="display:block">Password</label>
           	  <input type="password" class="form-control" aria-label="Text input with dropdown button" placeholder="Password" name="password" value="${sessionScope.password}">   
           	  <br><br> 
           	  <label style="display:block">Email</label>                   
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Email" name="email" value="${sessionScope.email}">              
			  <br><br> 
			  <label style="display:block">Nickname</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Nickname" name="nickname" value="${sessionScope.nickname}">                         
 			  <br><br> 
 			  <label style="display:block">First Name</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="First Name" name="firstname" value="${sessionScope.firstname}">              
		      <br><br> 
		      <label style="display:block">Last Name</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Last Name" name="lastname" value="${sessionScope.lastname}">             
          	  <br><br> 
          	  <label style="display:block">DOB (yyyy-mm-dd)</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="DOB (yyyy-mm-dd)" name="dob" value="${sessionScope.dob}">         
              <br><br> 
              <label style="display:block">Home Address</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Home Address" name="address" value="${sessionScope.address}">             
              <br><br> 
              <label style="display:block">Credit Card Number</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Credit Card Info (number)" name="creditinfo" value="${sessionScope.creditinfo}">
              <br><br> 
              <label style="display:block">Description</label>
              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Description" name="description" value="${sessionScope.description}">
            </div><!-- /input-group -->
          <br>
          <br>
              <div class="input-group-btn">
                <button class="btn btn-default" type="submit"><b>Save Changes</b></button>
              </div><!-- /btn-group -->
                
      	  </form>
        </c:when>    
      </c:choose>
    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
