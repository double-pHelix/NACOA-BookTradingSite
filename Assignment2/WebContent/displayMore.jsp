<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!-- wow you have to add these taglibs at the top of each jsp, even if they're included... far out! -->
<div class="content">

  <form name="articles_option" action="" method="POST">
  		<!-- Displaying a book -->
	    <input type="hidden" name="book_id" value="${requestScope.viewBean.readEntry.bookID}">
	    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
	    <input type="hidden" name="username" value="${sessionScope.username}">
	    <table class="table table-borderless">
	      <tr class="active">
	        <td><img SRC="${requestScope.viewBean.readEntry.picture}" height="300"></td>
	        <td align='left'><b>TITLE</b>
	        <br><b>AUTHOR</b>
	        <br><b>DATE PUBLISHED</b>
	        <br><b>PAGES</b>
	        <br><b>PUBLISHER</b>
	        <br><b>ISBN</b>
	        <br><b>GENRE</b>
	        <br><b>SELLER</b>
	        <br><b>PRICE</b></td>
	        <td align='left'>${requestScope.viewBean.readEntry.booktitle}
	        <br>${requestScope.viewBean.readEntry.author}
	        <br>${requestScope.viewBean.readEntry.DOP}
	        <br>${requestScope.viewBean.readEntry.pages }
	        <br>${requestScope.viewBean.readEntry.publisher}
	        <br>${requestScope.viewBean.readEntry.isbn }
	        <br>${requestScope.viewBean.readEntry.genre}
	        <br><a href="${pageContext.request.contextPath}/profile?user=${requestScope.viewBean.readEntry.sellerName}">${requestScope.viewBean.readEntry.sellerName}</a>
	        <br><font size="5" color="orange">$${requestScope.viewBean.readEntry.price}</font></td>
	      </tr>
	    </table>
          <c:choose>
            <c:when test="${not empty requestScope.returnToPage}">
                <a href="${pageContext.request.contextPath}/results?page=${requestScope.returnToPage}" class="btn btn-info" role="button">Back to Results</a>
            </c:when>
            <c:when test="${not empty requestScope.returnToCart}">
                <a href="${pageContext.request.contextPath}/cart" class="btn btn-info" role="button">Back to Cart</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
            </c:otherwise>
          </c:choose>
	    
  		<c:choose>
  			<c:when test="${sessionScope.logged_in && empty requestScope.returnToCart}">
  		  		<input class="btn btn-xx btn-warning" type="submit" name="add_to_cart_view" id="edit_profile_button" value="Add to Cart">
  			</c:when>
  		</c:choose>
  </form>
  
</div>
