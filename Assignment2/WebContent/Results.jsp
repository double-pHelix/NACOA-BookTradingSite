<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <!--  Yeah I think you guys should install eclipse + egit lol -->
   <!--  it you want i could try and get it working for your laptops -->
   <!--  ive had to deal with stuff like this before -->
   <!--  ok -->
   <!--  ill show you now! -->
   <!--  ill push to out repo now -->
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
        <h1>${requestScope.viewBean.readEntry.title}</h1>
        
        <jsp:include page="/displayMore.jsp" />
        
      </c:when>    
    <c:otherwise>
    
      <h1> Results </h1>
      <h4>Found ${requestScope.viewBean.totalResults} many results.</h4>
      
      <c:choose>
        <c:when test="${not empty requestScope.viewBean.resultBeans}">
          <!-- We must display the results of there are any -->
          
          <div class="content">
          <table class="table table-bordered"> 
            <tr class="active">
              <td scope="col"><b>TITLE</b></td>
              <td scope="col"><b>AUTHOR</b></td>
              <td scope="col"><b>DATE PUBLISHED</b></td>
              <td scope="col"><b>PUBTYPE</b></td>
              <td scope="col"><b>ACTIONS</b></td>
            </tr>
            
            <c:forEach var="entry" items="${requestScope.viewBean.resultBeans}" varStatus="varStatus" >
            
              <form name="articles_option" action="" method="POST">
                <!--  set for each of these entries some way of id to add to cart later -->
                <input type="hidden" name="publication_id" value="${entry.xmlID}">
                <center>
                <tr class="active">
                  <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${entry.xmlID}&page=${requestScope.viewBean.curr_page_num}">${entry.title}</a></td>
                  <td class="success">${entry.authors}</td>
                  <td class="warning">${entry.year}</td>
                  <td class="danger">  

                          <c:if test="${entry.pubType == 'article'}">           
                            Journal Article
                          </c:if>    
                          <c:if test="${entry.pubType == 'inproceedings'}">
                            Conference Paper
                          </c:if>
                          <c:if test="${entry.pubType == 'incollection'}">
                            Book/Collection
                          </c:if>
                          <c:if test="${entry.pubType == 'proceedings'}">
                            Editorship/Proceeding
                          </c:if>
                          <c:if test="${entry.pubType == 'book'}">
                            Book
                          </c:if>
                          <c:if test="${entry.pubType == 'phdthesis'}">
                            PhD Thesis
                          </c:if>
                          <c:if test="${entry.pubType == 'mastersthesis'}">
                            Master's Thesis
                          </c:if>
                          <c:if test="${entry.pubType == 'www'}">
                           www
                          </c:if>          
                                    
                  </td>
                  
                  <td class="info"><input class="btn btn-xs btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart"></td>
                </tr>
                
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
                      <center><input class="btn btn-xs btn-success" type="submit" id="edit_profile_button" value="Prev"></center>
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
                      <center><input class="btn btn-xs btn-success" type="submit" id="edit_profile_button" value="Next"></center>
                    </div>
                  </form>
                </c:if>
            
              </div>
            </div>
            <br>
            <b> ${requestScope.viewBean.curr_page_num} </b>
        </center>
          
        </c:when>    
        <c:otherwise>
          <!-- Display no results message -->
          <h1>Sorry, no matching datasets found!</h1>
          <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
          
        </c:otherwise>
      </c:choose>
       
      </c:otherwise>
    </c:choose>


      <br>
      <br>
      
      <footer class="footer">
        <p>&copy; Felix Yuen Dao Phu 2015</p>
      </footer>
      
    </div> <!-- /container -->
  </body>
</html>