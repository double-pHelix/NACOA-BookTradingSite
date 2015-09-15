<!--  Contains only the top menu  -->
<!-- yeah so i when i modify this it changes for all pages.. its easier -->

<div class="header clearfix">
  <nav>
    <ul class="nav nav-pills pull-right">
      <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/search">Home</a></li>
      <li role="presentation"><a href="${pageContext.request.contextPath}/cart">Cart</a></li>
      <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Account <span class="caret"></span></a>
            <ul class="dropdown-menu">
            
              <!-- TODO: If we are logged in -->
              <!-- 
                <li><a href="#">Change Details</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Settings</li>
                <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
              -->
               <!-- TODO: else we need to register -->
               
                <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                
               <!-- TODO: end if -->
            </ul>
            </li>    
      
      
    </ul>
  </nav>
  <h3 class="text-muted" id="logo">NACOA</h3>
</div>