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

   	<title>${sessionScope.profile.username}'s Profile Page</title>

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
      <!--  if we are viewing as admin we can ban user and view their customer activity report -->
          <c:choose>
            <c:when test="${sessionScope.userDetails.isAdmin}">
              <c:choose>
                <c:when test="${sessionScope.profile.isUser}">
                  
                  <!-- Admin cannot view his own transaction history (doesnt have one) -->
    
                </c:when>    
                <c:otherwise>
                  <div class="admin_options">
                   <a href="${pageContext.request.contextPath}/transaction_history?user=${sessionScope.profile.username}" class="btn btn-success" role="button">View Customer Transaction Report</a> 
                  </div>
                </c:otherwise>
              </c:choose>
            </c:when>    
            <c:otherwise>
              <!--  Not an admin2 -->
            </c:otherwise>
          </c:choose>
      
      <div class="jumbotron">
      
  
          <h1>${sessionScope.profile.username}</h1>
          <h2>User Profile</h2>
          <!--  Display this user's profile -->
          
          <!--  What kind of info?? -->
          <!--  Number of books: sold, bought... -->
          
          <p>
            ${sessionScope.profile.description}
          </p>
          
       </div> <!-- /container -->
          
        <div class="profile_info">
          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="http://www.malwarwickonbooks.com/wp-content/uploads/2014/09/hand-sell-blur.jpg" height="200" width="250" style="overflow:hidden">
              <h4>Books Sold</h4>
              <span class="text-muted">${sessionScope.profile.numBooksSold}</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="http://s3-media4.fl.yelpcdn.com/bphoto/N2lcaCuzP7u-0MDER3yL2g/ls.jpg" height="200">
              <h4>Books On Sale</h4>
              <span class="text-muted">${sessionScope.profile.numBooksSale}</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="http://www.columbuspubliclibrary.info/sites/www.columbuspubliclibrary.info/files/images/events/Dogs-Reading.jpg" height="200" width="250" style="overflow:hidden">
              <h4>Books Bought</h4>
              <span class="text-muted">${sessionScope.profile.numBooksBought}</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="https://playitagaindan.files.wordpress.com/2014/12/pile-of-books.jpg" height="200" width="250" style="overflow:hidden">
              <h4>Books Wanted</h4>
              <span class="text-muted">${sessionScope.profile.numBooksInCart}</span>
            </div>
          </div>
        </div>
        
        
        <h2 class="sub-header">
          <c:choose>
            <c:when test="${sessionScope.profile.isUser}">
              My Books
            </c:when>    
            <c:otherwise>
              Seller's Books
            </c:otherwise>
          </c:choose>
        </h2>
        <div class="content">
        <table class="table table-bordered"> 
           <thead>
            <tr class="active">
              <td scope="col"><b>TITLE</b></td>
              <td scope="col"><b>AUTHOR</b></td>
              <td scope="col"><b>GENRE</b></td>
              <td scope="col"><b>PRICE</b></td>
              <td scope="col"><b>ACTIONS</b></td>
            </tr>
           </thead> 
           <tbody>
          <c:forEach var="entry" items="${sessionScope.selling_books}" varStatus="varStatus" >
          
            <form name="articles_option" action="" method="POST">
              <!--  set for each of these entries some way of id to add to cart later -->
              <input type="hidden" name="book_id" value="${entry.bookID}">
              <input type="hidden" name="username" value="${sessionScope.username}">
              <center>
                <tr class="active">
                  <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${entry.bookID}&page=${requestScope.viewBean.curr_page_num}">${entry.booktitle}</a></td>
                  <td class="success">${entry.author}</td>
                  <td class="warning">${entry.genre}</td>
                  <td class="danger">${entry.price}</td>
                  <td class="info"><input class="btn btn-xs btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart"></td>
                </tr>
              </center>
              <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
            </form>
          </c:forEach>
          </tbody>
        </table>
        </div> 

      </div>
   


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
