<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!--  Contains only the top menu  -->
<!-- yeah so i when i modify this it changes for all pages.. its easier -->

<div class="header clearfix">
  <nav>
    <ul class="nav nav-pills pull-right">    
      
        <li role="presentation">
           <div id="bannerSearchBox">
           <form action="${pageContext.request.contextPath}/results" method="POST" class="form-inline"> 
              <input type="hidden" name="search_type" value="book">
              <input type="hidden" name="search_format" value="basic">
              <div class="input-group">
                <input type="text" class="form-control" aria-label="Text input with dropdown button" placeholder="Search..." name="search_general">                           
              </div><!-- /input-group -->
              <div class="input-group">
                <button class="btn btn-default" type="submit" id="topBannerSearchButton"><span class="glyphicon glyphicon-search"></span></button>
              </div>
            </form>
            </div>
          </li>
        
      <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/"><span class="glyphicon glyphicon-home"></span></a></li>
      <c:choose>
      <c:when test="${sessionScope.logged_in == true}">
        <li role="presentation"><a href="${pageContext.request.contextPath}/upload_book"><span class="glyphicon glyphicon-upload"></span></a></li>
		<li role="presentation"><a href="${pageContext.request.contextPath}/cart?username=${sessionScope.username}"><span class="glyphicon glyphicon-shopping-cart"></span></a></li>     
      </c:when>
      </c:choose>
      <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon glyphicon-user"></span><span class="caret"></span></a>
            <ul class="dropdown-menu">
            
            <c:choose>
              <c:when test="${sessionScope.logged_in == true}">
                <li class="dropdown-header">Logged in as "${sessionScope.username}"</li>
                <li><a href="${pageContext.request.contextPath}/selling">My Books</a></li>
                <li><a href="${pageContext.request.contextPath}/profile?user=${sessionScope.username}">View Profile</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Settings</li>
                <li><a href="${pageContext.request.contextPath}/account">Change Details</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
               
              </c:when>
              <c:otherwise>
               
                <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                
              </c:otherwise> 
            </c:choose>
          
            </ul>
            </li> 
      
    </ul>
  </nav>
  <h3 class="text-muted" style="margin:7px" id="logo"><a href="${pageContext.request.contextPath}/" id="homeLink">NACOA</a><br><font size="1" color="purple">not a copy of amazon.</font></h3>
  
</div>