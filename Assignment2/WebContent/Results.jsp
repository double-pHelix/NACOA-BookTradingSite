<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <!--  Yeah I think you guys should install eclipse + egit lol -->
   <!--  it you want i could try and get it working for your laptops -->
   <!--  ive had to deal with stuff like this before -->
   <!--  ok -->
   <!--  ill show you now! -->
   <!--  ill push to out repo now -->
   <!--  done too ez-->
   <!--  remember to pull to the latest version before you push or it wont work -->
   <!-- I think so -->
   <!--  I think we can break it up into Databases/Business logic/Presentation? -->
   <!--  i didnt show you the xml handler code which will replace the database code ill do that now? -->
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

   	<title>Results</title>

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
    	<c:when test="${requestScope.viewBean.readMore == 'true'}">
        <!--  We display the specified content -->
        <h1>${requestScope.viewBean.readEntry.booktitle}</h1>
        
        <jsp:include page="/displayMore.jsp" />
      </c:when>  
   
    <c:otherwise>
    	<c:choose>
	    	
	      <c:when test="${sessionScope.banUser == 'true'}">
	        
	        <font color="green">${sessionScope.modifiedUser} has been banned</font>
	        
	      </c:when>
	      
	      <c:when test="${sessionScope.unbanUser == 'true'}">
	        
	        <font color="green">${sessionScope.modifiedUser} has been unbanned</font>
	        
	      </c:when>
	      
	      <c:when test="${sessionScope.makeAdmin == 'true'}">
	        
	        <font color="green">${sessionScope.modifiedUser} is now an administrator</font>
	        
	      </c:when>  
	      
	      <c:when test="${sessionScope.alreadyAdmin == 'true'}">
	        
	        <font color="green">${sessionScope.modifiedUser} is already an administrator</font>
	        
	      </c:when>   
	      
	    <c:otherwise>
	      <h1> Results </h1>
	      	<h4>Found ${requestScope.viewBean.totalResults} results.</h4>
          
           <c:choose>
    	      <c:when test="${requestScope.addedCart == 'true'}">
    	        
    	        <font color="green">"${sessionScope.itemAdded}" has been added to cart</font>
    	        
    	      </c:when>   
    	      
    	      <c:when test="${requestScope.alreadyCart == 'true'}">
    	        
    	        <font color="green">"${sessionScope.itemAdded}" is already in the cart</font>
    	        
    	      </c:when>
          </c:choose>
          
      <c:choose>
         <c:when test="${not empty requestScope.viewBean.resultBeans || not empty requestScope.viewUserBean.resultBeans}">
        
                  <c:choose>
          	        <c:when test="${not empty requestScope.viewBean.resultBeans}">
          	          <!-- We must display the results of there are any -->
          	          
          	          <div class="content">
          	          <table class="table table-bordered"> 
          	            <tr class="active">
          	              <td scope="col"><b>IMAGE</b></td>
          	              <td scope="col"><b>TITLE</b></td>
          	              <td scope="col"><b>AUTHOR</b></td>
          	              <td scope="col"><b>GENRE</b></td>
          	              <td scope="col"><b>DATE OF PUB</b></td>
          	              <td scope="col"><b>SELLER</b></td>
                            <td scope="col"><b>PRICE</b></td>
          	              <c:choose>
          	        		<c:when test="${sessionScope.logged_in == true}">
          	              		<td scope="col"><b>ACTION</b></td>
          	              	</c:when>
          	              </c:choose>
          	              <c:choose>
          	        		<c:when test="${sessionScope.userDetails.isAdmin}">
          	              		<td scope="col"><b>ADMIN ACTION</b></td>
          	              	</c:when>
          	              </c:choose>
          	            </tr>
          	            
          	            <c:forEach var="entry" items="${requestScope.viewBean.resultBeans}" varStatus="varStatus" >
          	            
          	              <form name="articles_option" action="" method="POST">
          	                <!--  set for each of these entries some way of id to add to cart later -->
          	                <input type="hidden" name="book_id" value="${entry.bookID}">
          	                <input type="hidden" name="username" value="${sessionScope.username}">
          	                <input type="hidden" name="banUser" value="false">
          	                <center>
          	                <tr class="active">
          	                  <td class="active" style="vertical-align: middle"><img class="book_image" src="${entry.picture}"></img></td>
          	                  <td class="active" style="vertical-align: middle"><a href="${pageContext.request.contextPath}/results?entryMoreView=${entry.bookID}&page=${requestScope.viewBean.curr_page_num}">${entry.booktitle}</a></td>
          	                  <td class="success" style="vertical-align: middle">${entry.author}</td>
          	                  <td class="warning" style="vertical-align: middle">${entry.genre}</td>
          	                  <td class="warning" style="vertical-align: middle">${entry.dop}</td>
                                <td class="warning" style="vertical-align: middle"><a href="${pageContext.request.contextPath}/profile?user=${entry.sellerName}">
                                                        ${entry.sellerName}
                                                    </a>
                                </td>
          	                  <td class="danger" style="vertical-align: middle">$${entry.price}</td>

          	                  <c:choose>
          		        		<c:when test="${sessionScope.logged_in == true}">
          		              		 <td class="info"  style="vertical-align: middle"><button class="btn btn-xs btn-success" type="submit" name="add_to_cart" id="addToCartButton" value="Add to Cart"><span class="glyphicon glyphicon-shopping-cart"></span></button></td>
          		              	</c:when>
          		              </c:choose>
          		              <c:choose>
          		        		<c:when test="${sessionScope.userDetails.isAdmin}">
          		              		<td class="info"  style="vertical-align: middle"><input class="btn btn-xs btn-warning" type="submit" name="remove_book" id="edit_profile_button" value="Remove Book"></td>
          		              	</c:when>
          		              </c:choose>
                            
          	                </tr>
          	                </center>
          	                <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
                        	</form>
                      	</c:forEach>
                    	</table>
                    	</div> 
            
            			<center>
	                      <div style="width:1200px;">
	                        <div style="float: left; width: 130px"> 
	                      
	                          <c:if test="${requestScope.viewBean.less =='true'}">
	                            <form name="articles_option" action="${pageContext.request.contextPath}/results" method="GET">
	                              <input type="hidden" name="page" value="${requestScope.viewBean.prev_page_num}">
	                              <div style="text-align:left">
	                                <center><button class="btn btn-xs btn-info" type="submit" id="edit_profile_button" value="Prev"><span class="glyphicon glyphicon-arrow-left"></span></center>
	                              </div>
	                            </form>
	                          </c:if>
	                            
	                        </div>
	                        <div style="float: right; width: 225px"> 
	                        <!--  um bootstrap is just the container and css yeah -->
	                        <!-- So below is an example of viewBean .. ok-->
	                        <!--  btw do you want me to show you Egit? -->
	                        <!--  its super easy bro -->
	                        <!-- eclipse is easy as well lol -->
	                          <c:if test="${requestScope.viewBean.more =='true'}">
	                            <form name="articles_option" action="${pageContext.request.contextPath}/results" method="GET">
	                              <input type="hidden" name="page" value="${requestScope.viewBean.next_page_num}">
	                              <div style="text-align:right">
	                                <center><button class="btn btn-xs btn-info" type="submit" id="edit_profile_button" value="Next"><span class="glyphicon glyphicon-arrow-right"></span></center>
	                              </div>
	                            </form>
	                          </c:if>
	                      
	                        </div>
	                      </div>
	                      <br>
	                      <b> ${requestScope.viewBean.curr_page_num} </b>
	                  </center>
          	        </c:when> 
                  	
                  	<c:when test="${not empty requestScope.viewUserBean.resultBeans}">
                       <!-- We must display the results of there are any -->
          	          <div class="content">
          		          <table class="table table-bordered"> 
          		            <tr class="active">
          		              <td scope="col"><b>USERNAME</b></td>
          		              <td scope="col"><b>FIRSTNAME</b></td>
          		              <td scope="col"><b>LASTNAME</b></td>
          		              <td scope="col"><b>NICKNAME</b></td>
          		              <!--  Permissions -->
          		              <c:choose>
          		        		<c:when test="${sessionScope.userDetails.isAdmin}">
          		              		 <td scope="col" colspan =2><b>ADMIN ACTION</b></td>
          		              	</c:when>
          		              </c:choose>
          		            </tr>
          	            
          		            <c:forEach var="entry" items="${requestScope.viewUserBean.resultBeans}" varStatus="varStatus" >
          		            
          		              <form name="articles_option" action="" method="POST">
          		                <!--  set for each of these entries some way of id to for banning purposes later -->
          		                <input type="hidden" name="user_id" value="${entry.userID}">
          		                
          		                <input type="hidden" name="username" value="${entry.username}">
          		                <center>
          		                <tr class="active">
          		                  <td class="active"  style="vertical-align: middle"><a href="${pageContext.request.contextPath}/profile?user=${entry.username}">${entry.username}</a></td>
          		                  <td class="success" style="vertical-align: middle">${entry.firstname}</td>
          		                  <td class="warning" style="vertical-align: middle">${entry.lastname}</td>
          		                  <td class="danger" style="vertical-align: middle">${entry.nickname}</td>
          		                  
          		                  <c:choose>
          			        		<c:when test="${sessionScope.userDetails.isAdmin}">
          			        			<!-- Remove the choose below -->
          			        			
          			        			<c:choose>
          			        				<c:when test="${entry.isHalted == 0}">
          			              		 		<td class="info"><input class="btn btn-xs btn-warning" type="submit" name="ban_user" id="edit_profile_button" value="Ban User"></td>
          			              		 	</c:when>
          			              		 	<c:when test="${entry.isHalted == 1}">
          			              		 		<td class="info"><input class="btn btn-xs btn-warning" type="submit" name="unban_user" id="edit_profile_button" value="Unban User"></td>
          			              		 	</c:when>
          			              		</c:choose>
          			              		
          			              		 <td class="info"><input class="btn btn-xs btn-warning" type="submit" name="make_admin" id="edit_profile_button" value="Make Admin"></td>
          			              	</c:when>
          			              </c:choose>
          		                  
          		                </tr>
          		                
          		                <input type="hidden" name="page" value="${requestScope.viewUserBean.curr_page_num}">
          		              </form>
          		            </c:forEach>
          	          		</table>
                    		</div> 
       						<center>
			                      <div style="width:1200px;">
			                        <div style="float: left; width: 130px"> 
			                      
			                          <c:if test="${requestScope.viewUserBean.less =='true'}">
			                            <form name="articles_option" action="${pageContext.request.contextPath}/results" method="GET">
			                              <input type="hidden" name="page" value="${requestScope.viewUserBean.prev_page_num}">
			                              <div style="text-align:left">
			                                <center><button class="btn btn-xs btn-info" type="submit" id="edit_profile_button" value="Prev"><span class="glyphicon glyphicon-arrow-left"></span></center>
			                              </div>
			                            </form>
			                          </c:if>
			                            
			                        </div>
			                        <div style="float: right; width: 225px"> 
			                        <!--  um bootstrap is just the container and css yeah -->
			                        <!-- So below is an example of viewBean .. ok-->
			                        <!--  btw do you want me to show you Egit? -->
			                        <!--  its super easy bro -->
			                        <!-- eclipse is easy as well lol -->
			                          <c:if test="${requestScope.viewUserBean.more =='true'}">
			                            <form name="articles_option" action="${pageContext.request.contextPath}/results" method="GET">
			                              <input type="hidden" name="page" value="${requestScope.viewUserBean.next_page_num}">
			                              <div style="text-align:right">
			                                <center><button class="btn btn-xs btn-info" type="submit" id="edit_profile_button" value="Next"><span class="glyphicon glyphicon-arrow-right"></span></center>
			                              </div>
			                            </form>
			                          </c:if>
			                      
			                        </div>
			                      </div>
			                      <br>
			                      <b> ${requestScope.viewUserBean.curr_page_num} </b>
			                      <br><br>
			                      <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
			                  </center>
                 	</c:when> 
                   </c:choose> 
        </c:when>

        <c:otherwise>
          <!-- Display no results message -->
          <h1>Sorry, no matching datasets found!</h1>
          
        </c:otherwise>

      </c:choose>
      </c:otherwise>
      </c:choose>
      </c:otherwise>
    </c:choose>
      <br>
      <br>
      <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <br>
      <br>
       
       <jsp:include page="/footer.jsp" />
      
    </div> <!-- /container -->
  </body>
</html>