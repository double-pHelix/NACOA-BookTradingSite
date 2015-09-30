<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!-- wow you have to add these taglibs at the top of each jsp, even if they're included... far out! -->
<div class="content">

  <form name="articles_option" action="" method="POST">
  		<!-- Displaying a book -->
	    <input type="hidden" name="book_id" value="${requestScope.viewBean.readEntry.bookID}">
	    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
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
	        <td align='left'><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.bookID}">${requestScope.viewBean.readEntry.booktitle}</a>
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
	    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
  		<input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
  
</div>
