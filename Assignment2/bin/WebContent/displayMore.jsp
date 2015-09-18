<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!-- wow you have to add these taglibs at the top of each jsp, even if they're included... far out! -->
<div class="content">
<c:if test="${requestScope.viewBean.readEntry.pubType == 'article'}">

  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PAGES</b></td>
        <td scope="col"><b>VOLUME</b></td>
        <td scope="col"><b>JOURNAL</b></td>
        <td scope="col"><b>NUMBER</b></td>
        <td scope="col"><b>URL</b></td>
        <td scope="col"><b>EE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
        <td class="success">${requestScope.viewBean.readEntry.volume}</td>
        <td class="warning">${requestScope.viewBean.readEntry.journal}</td>
        <td class="danger">${requestScope.viewBean.readEntry.number }</td>
        <td class="success">${requestScope.viewBean.readEntry.url}</td>
        <td class="warning">${requestScope.viewBean.readEntry.ee}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
  
</c:if>
  

<c:if test="${requestScope.viewBean.readEntry.pubType == 'inproceedings'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PAGES</b></td>
        <td scope="col"><b>CROSSREF</b></td>
        <td scope="col"><b>BOOKTITLE</b></td>
        <td scope="col"><b>URL</b></td>
        <td scope="col"><b>EE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
        <td class="success">${requestScope.viewBean.readEntry.crossref}</td>
        <td class="warning">${requestScope.viewBean.readEntry.booktitle}</td>
        <td class="success">${requestScope.viewBean.readEntry.url}</td>
        <td class="warning">${requestScope.viewBean.readEntry.ee}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>

<c:if test="${requestScope.viewBean.readEntry.pubType == 'incollection'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PAGES</b></td>
        <td scope="col"><b>CROSSREF</b></td>
        <td scope="col"><b>BOOKTITLE</b></td>
        <td scope="col"><b>URL</b></td>
        <td scope="col"><b>EE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
        <td class="success">${requestScope.viewBean.readEntry.crossref}</td>
        <td class="warning">${requestScope.viewBean.readEntry.booktitle}</td>
        <td class="success">${requestScope.viewBean.readEntry.url}</td>
        <td class="warning">${requestScope.viewBean.readEntry.ee}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>

<c:if test="${requestScope.viewBean.readEntry.pubType == 'proceedings'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>EDITORS</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PUBLISHER</b></td>
        <td scope="col"><b>BOOKTITLE</b></td>
        <td scope="col"><b>VOLUME</b></td>
        <td scope="col"><b>SERIES</b></td>
        <td scope="col"><b>ISBN</b></td>
        <td scope="col"><b>URL</b></td>
        <td scope="col"><b>EE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.publisher }</td>
        <td class="success">${requestScope.viewBean.readEntry.booktitle}</td>
        <td class="warning">${requestScope.viewBean.readEntry.volume}</td>
        <td class="success">${requestScope.viewBean.readEntry.series}</td>
        <td class="warning">${requestScope.viewBean.readEntry.isbn}</td>
        <td class="warning">${requestScope.viewBean.readEntry.url}</td>
        <td class="warning">${requestScope.viewBean.readEntry.ee}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>

<c:if test="${requestScope.viewBean.readEntry.pubType == 'book'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PAGES</b></td>
        <td scope="col"><b>PUBLISHER</b></td>
        <td scope="col"><b>VOLUME</b></td>
        <td scope="col"><b>SERIES</b></td>
        <td scope="col"><b>SCHOOL</b></td>
        <td scope="col"><b>ISBN</b></td>
        <td scope="col"><b>EE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
        <td class="success">${requestScope.viewBean.readEntry.publisher}</td>
        <td class="warning">${requestScope.viewBean.readEntry.volume}</td>
        <td class="success">${requestScope.viewBean.readEntry.series}</td>
        <td class="warning">${requestScope.viewBean.readEntry.school}</td>
        <td class="warning">${requestScope.viewBean.readEntry.isbn}</td>
        <td class="warning">${requestScope.viewBean.readEntry.ee}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>

<c:if test="${requestScope.viewBean.readEntry.pubType == 'phdthesis'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PAGES</b></td>
        <td scope="col"><b>SCHOOL</b></td>
        <td scope="col"><b>ISBN</b></td>
        <td scope="col"><b>NOTE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
        <td class="warning">${requestScope.viewBean.readEntry.school}</td>
        <td class="warning">${requestScope.viewBean.readEntry.isbn}</td>
        <td class="warning">${requestScope.viewBean.readEntry.note}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>

<c:if test="${requestScope.viewBean.readEntry.pubType == 'mastersthesis'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
        <td scope="col"><b>DATE PUBLISHED</b></td>
        <td scope="col"><b>PAGES</b></td>
        <td scope="col"><b>SCHOOL</b></td>
        <td scope="col"><b>ISBN</b></td>
        <td scope="col"><b>NOTE</b></td>
        <td scope="col"><b>URL</b></td>
        <td scope="col"><b>EE</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
        <td class="warning">${requestScope.viewBean.readEntry.year}</td>
        <td class="danger">${requestScope.viewBean.readEntry.pages }</td>
        <td class="warning">${requestScope.viewBean.readEntry.school}</td>
        <td class="warning">${requestScope.viewBean.readEntry.isbn}</td>
        <td class="warning">${requestScope.viewBean.readEntry.note}</td>
        <td class="success">${requestScope.viewBean.readEntry.url}</td>
        <td class="success">${requestScope.viewBean.readEntry.ee}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>

<c:if test="${requestScope.viewBean.readEntry.pubType == 'www'}">
  <form name="articles_option" action="" method="POST">
    <input type="hidden" name="publication_id" value="${requestScope.viewBean.readEntry.xmlID}">
    <input type="hidden" name="page" value="${requestScope.viewBean.curr_page_num}">
    <table class="table table-bordered"> 
      <tr class="active">
        <td scope="col"><b>TITLE</b></td>
        <td scope="col"><b>AUTHOR</b></td>
      </tr>
    
      <tr class="active">
        <td class="active"><a href="${pageContext.request.contextPath}/results?entryMoreView=${requestScope.viewBean.readEntry.xmlID}">${requestScope.viewBean.readEntry.title}</a></td>
        <td class="success">${requestScope.viewBean.readEntry.authors}</td>
      </tr>
    </table> 
    <a href="${pageContext.request.contextPath}/search" class="btn btn-info" role="button">Back to Search</a>
      <input class="btn btn-xx btn-warning" type="submit" name="add_to_cart" id="edit_profile_button" value="Add to Cart">
  </form>
</c:if>
</div>
