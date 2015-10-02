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
              
                <hr id="NACOABannerHorizontalLine">
                <a href="${pageContext.request.contextPath}/register" class="btn btn-lg btn-info" id="bannerRegisterButton" role="button">Register</a><br>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-lg btn-warning" id="bannerRegisterButton2" role="button">Login</a> 
                
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
        <ul class="nav nav-tabs" role="tablist" id="searchtabmenu">
          <li role="presentation" class="active"><a href="#bookSearch" aria-controls="bookSearch" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-book"></span></a></li>
          <li role="presentation"><a href="#userSearch" aria-controls="userSearch" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-user"></span></a></li>
        </ul>
        <div class="tabtastic">
          <!-- Tab panes -->
          <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="bookSearch">
        
              <ul class="nav nav-tabs" role="tablist" id="searchtabmenu">
                <li role="presentation" class="active"><a href="#basicSearch" aria-controls="basicSearch" role="tab" data-toggle="tab">Basic</a></li>
                <li role="presentation"><a href="#advancedSearch" aria-controls="advancedSearch" role="tab" data-toggle="tab">Advanced</a></li>
              </ul>
              <div class="tabtastic">
                <!-- Tab panes -->
                  <div class="tab-content">
                      <div role="tabpanel" class="tab-pane" id="advancedSearch">
                         <form action="${pageContext.request.contextPath}/results" method="POST"> 
                            <input type="hidden" name="search_type" value="book">
                            <input type="hidden" name="username" value="${sessionScope.username}">
                            <div class="input-group">
                              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Author" name="search_author">
                              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Title" name="search_title">                         
                              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Genre" name="search_genre"> 
                              <!-- Drop down -->
                              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Max Price" name="search_price">                           
                            </div><!-- /input-group -->
                            <div class="input-group">
                              
                                <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                             
                                
                            </div><!-- /input-group -->
                          </form>
                          
                        </div>
                        
                        <div role="tabpanel" class="tab-pane active" id="basicSearch">
                          <form action="${pageContext.request.contextPath}/results" method="POST"> 
                            <input type="hidden" name="search_type" value="book">
                            <input type="hidden" name="search_format" value="basic">
                            <input type="hidden" name="username" value="${sessionScope.username}">
                            <div class="input-group">
                              <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="General" name="search_general">                           
                            </div><!-- /input-group -->
                            <div class="input-group">
                              
                                <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                             
                                
                            </div><!-- /input-group -->
                          </form>
                        </div>
                      </div>
                    </div>

            </div>
            <div role="tabpanel" class="tab-pane" id="userSearch">
            
              <form action="${pageContext.request.contextPath}/results" method="POST">  
                <input type="hidden" name="search_type" value="user">
                <input type="hidden" name="username" value="${sessionScope.username}">
                <div class="input-group">
                  <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Username" name="search_username">          
                </div><!-- /input-group -->
                <div class="input-group">
                    <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                </div><!-- /input-group -->
              </form>
            
            
            </div>
          </div>
        </div>
        <!-- 
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/Results.jsp" role="button">Sign up today</a></p>
        -->
      </div>
  

      <h3>Popular Reads</h3>
          <table class="table table-borderless"> 
          <c:forEach var="random" items="${sessionScope.randomBeans}" varStatus="varStatus">
            <tr class="active">
              <input type="hidden" name="logged_in" value="${sessionScope.logged_in}">
              <input type="hidden" name="username" value="${sessionScope.username}">
	          <a href="?entryMoreView=${random.bookID}"> <img SRC="${random.picture}" height="150"> </a> &nbsp;
            </tr>
          </c:forEach>
          </table>
          
           <jsp:include page="/footer.jsp" />
          
      </div>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
