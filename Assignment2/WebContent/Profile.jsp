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
    
      <div class="jumbotron">
      
  
          <h1>${sessionScope.profile.username}</h1>
          <h2>User Profile</h2>
          <!--  Display this user's profile -->
          
          <!--  What kind of info?? -->
          <!--  Number of books: sold, bought... -->
          
          <p>
            ${sessionScope.profile.description}
          </p>
          
          <div class="profile_info">
            <div class="row placeholders">
              <div class="col-xs-6 col-sm-3 placeholder">
                <img src="http://www.malwarwickonbooks.com/wp-content/uploads/2014/09/hand-sell-blur.jpg" class="img-responsive" alt="Generic placeholder thumbnail">
                <h4>Books Sold</h4>
                <span class="text-muted">${sessionScope.profile.numBooksSold}</span>
              </div>
              <div class="col-xs-6 col-sm-3 placeholder">
                <img src="http://s3-media4.fl.yelpcdn.com/bphoto/N2lcaCuzP7u-0MDER3yL2g/ls.jpg" class="img-responsive" alt="Generic placeholder thumbnail">
                <h4>Books On Sale</h4>
                <span class="text-muted">${sessionScope.profile.numBooksSale}</span>
              </div>
              <div class="col-xs-6 col-sm-3 placeholder">
                <img src="http://www.columbuspubliclibrary.info/sites/www.columbuspubliclibrary.info/files/images/events/Dogs-Reading.jpg" class="img-responsive" alt="Generic placeholder thumbnail">
                <h4>Books Bought</h4>
                <span class="text-muted">${sessionScope.profile.numBooksBought}</span>
              </div>
              <div class="col-xs-6 col-sm-3 placeholder">
                <img src="https://playitagaindan.files.wordpress.com/2014/12/pile-of-books.jpg" class="img-responsive" alt="Generic placeholder thumbnail">
                <h4>Books Wanted</h4>
                <span class="text-muted">${sessionScope.profile.numBooksInCart}</span>
              </div>
            </div>
          </div>
          <h2 class="sub-header">Seller's Books</h2>
          <div class="table-responsive">
          
          
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Price</th>
                  </tr>
                </thead>
                
                <tbody>
                <!--  retrieve list of user's books and display them -->
                  <tr>
                    <td>1,001</td>
                    <td>Lorem</td>
                    <td>ipsum</td>
                    <td>dolor</td>
                    <td>sit</td>
                  </tr>
                  
                </tbody>
              </table>
            </div>
   
    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
