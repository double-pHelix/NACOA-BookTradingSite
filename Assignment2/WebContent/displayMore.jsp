<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!-- wow you have to add these taglibs at the top of each jsp, even if they're included... far out! -->
<div class="content">

  <form name="articles_option" action="" method="POST">
  		<!-- Displaying a book -->
	    <input type="hidden" name="book_id" value="${requestScope.viewBean.readEntry.bookID}">
	    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
	    <table class="table table-bordered"> 
	      <tr class="active">
	        <td scope="col"><b>TITLE</b></td>
	        <td scope="col"><b>AUTHOR</b></td>
	        <td scope="col"><b>DATE PUBLISHED</b></td>
	        <td scope="col"><b>PAGES</b></td>
	        <td scope="col"><b>PUBLISHER</b></td>
	        <td scope="col"><b>PICTURE</b></td>
	        <td scope="col"><b>ISBN</b></td>
	        <td scope="col"><b>GENRE</b></td>
	        <td scope="col"><b>PRICE</b></td>
	      </tr>
	    
	      <tr class="active">
	        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.bookID}">${requestScope.viewBean.readEntry.booktitle}</a></td>
	        <td class="success">${requestScope.viewBean.readEntry.author}</td>
	        <td class="warning">${requestScope.viewBean.readEntry.DOP}</td>
	        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
	        <td class="success">${requestScope.viewBean.readEntry.publisher}</td>
	        <td class="warning">${requestScope.viewBean.readEntry.picture}</td>
	        <td class="danger">${requestScope.viewBean.readEntry.isbn }</td>
	        <td class="success">${requestScope.viewBean.readEntry.genre}</td>
	        <td class="warning">${requestScope.viewBean.readEntry.price}</td>
	      </tr>
	    </table> 
	    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
  		<input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
  
</div>
