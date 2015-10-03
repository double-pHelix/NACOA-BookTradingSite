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

   	<title>${sessionScope.profile.username}'s Transaction History</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/customBootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/profilePage.css" rel="stylesheet">

    
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
            <c:when test="${sessionScope.userDetails.isAdmin}">
              <c:choose>
                <c:when test="${sessionScope.profile.isUser}">
                    <h1>You do not have a transaction history because you are not an Admin</h1>
                </c:when>    
                <c:otherwise>
                    	<h1>${sessionScope.profile.username}'s Transaction History</h1>  
                    <!--  Display this user's profile -->
                    <div class="content">
                    <c:choose>
                    <c:when test="${not empty sessionScope.transaction_history}">
                    
                    <h2 class="sub-header"> History </h2>
                    <table class="table table-bordered"> 
                       <thead>
                        <tr class="active">
                          <td scope="col"><b>TITLE</b></td>
                          <td scope="col"><b>ACTION</b></td>
                          <td scope="col"><b>TIME STAMP</b></td>
                        </tr>
                       </thead> 
                       <tbody>
                      <c:forEach var="entry" items="${sessionScope.transaction_history}" varStatus="varStatus" >
                      
                        <form name="articles_option" action="" method="POST">
                          <!--  set for each of these entries some way of id to add to cart later -->
                          <input type="hidden" name="book_id" value="${entry.book_id}">
                          <input type="hidden" name="username" value="${sessionScope.profile.username}">
                          <center>
                            <tr class="active">
                              <td class="active"><a href="${pageContext.request.contextPath}/transaction_history?entryMoreView=${entry.book_id}&user=${sessionScope.profile.username}">${entry.bookTitle}</a></td>
                              <td class="success">${entry.action}</td>
                              <td class="warning">${entry.timeStamp}</td>
                            </tr>
                          </center>
                        </form>
                      </c:forEach>
                      </tbody>
                    </table>
                    </c:when>
                    <c:otherwise>
                         <h4><br>${sessionScope.profile.username}'s Transaction History is empty</h4>
                    </c:otherwise>
                    </c:choose>
                    </div>
                </c:otherwise>
              </c:choose>
            </c:when>    
            <c:otherwise>
              	<h1>You cannot view ${sessionScope.profile.username}'s Transaction History</h1> 
            </c:otherwise>
          </c:choose>
      </div>


      <jsp:include page="/footer.jsp" />
   
     </div>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
