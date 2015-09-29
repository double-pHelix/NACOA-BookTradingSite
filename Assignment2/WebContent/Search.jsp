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

   	<title>Main Page</title>

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
            <!-- PUT WHATEVER YOU WANT HERE FOR THE LOGGED IN USER -->
          </c:when>    
          <c:otherwise>
            <div class="jumbotron" id="NACOABanner">
              <center>
              <div class ="NACOABannerText">
                <h1>Welcome to NACOA</h1>
                <p>We are an online book trading platform that nearly offers all the features our rival provides.</p>
                <br>   
                <a href="${pageContext.request.contextPath}/login" class="btn btn-lg btn-danger" id="bannerRegisterButton2" role="button">Login</a><br>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-lg btn-warning" id="bannerRegisterButton" role="button">Register</a>
             
              </div>
              </center>
            </div>
          </c:otherwise>
        </c:choose>

         
      <div class="jumbotron" id="customisedJumbotron">

         <!--  Need to place search form here -->
         <!-- Default we show basic, if we have a request to have advanced we switch -->
        
        <h2>Search</h2>

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active"><a href="#bookSearch" aria-controls="bookSearch" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-book"></span></a></li>
          <li role="presentation"><a href="#userSearch" aria-controls="userSearch" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-user"></span></a></li>
        </ul>
      
        <!-- Tab panes -->
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane active" id="bookSearch">
             <form action="${pageContext.request.contextPath}/results" method="POST"> 
                <input type="hidden" name="search_type" value="book">
                <div class="input-group">
                  <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Author" name="search_author">
                  <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Title" name="search_title">                         
                  <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Genre" name="search_genre">              
                </div><!-- /input-group -->
                <div class="input-group">
                  
                    <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                 
                    
                </div><!-- /input-group -->
              </form>

          </div>
          <div role="tabpanel" class="tab-pane" id="userSearch">
          
            <form action="${pageContext.request.contextPath}/results" method="POST">  
              <input type="hidden" name="search_type" value="user">
              <div class="input-group">
                <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Username" name="search_username">          
              </div><!-- /input-group -->
              <div class="input-group">
                  <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
              </div><!-- /input-group -->
            </form>
          
          
          </div>
        </div>
      
                
         
        <!-- 
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/Results.jsp" role="button">Sign up today</a></p>
        -->
      </div>
  
      <h3>Randomised</h3>
      <div class="content">
      <table class="table table-bordered"> 
        <tr class="active">
          <td scope="col"><b>TITLE</b></td>
          <td scope="col"><b>AUTHOR</b></td>
          <td scope="col"><b>DATE PUBLISHED</b></td>
          <td scope="col"><b>ISBN</b></td>
        </tr>
        
        <c:forEach var="entry" items="${requestScope.randomBeans}" varStatus="varStatus" >
        
          <form name="articles_option" action="" method="POST">
          <!--  set for each of these entries some way of id to add to cart later -->
          <input type="hidden" name="publication_id" value="${entry.xmlID}">
          
          <tr class="active">
            <td class="active">${entry.title}</td>
            <td class="success">${entry.authors}</td>
            <td class="warning">${entry.year}</td>
            <td class="danger"><b>${entry.isbn}</b></td>
          </tr>
          </form>
        </c:forEach>
      </table>
      </div>

      <footer class="footer">
        <p>&copy; Felix Yuen Dao Phu 2015</p>
      </footer>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
