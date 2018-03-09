<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<div class="container">

</div>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/tableExport.js"></script>
<script type="text/javascript" src="js/jquery.base64.js"></script>
<script type="text/javascript" src="jspdf/libs/sprintf.js"></script>
<script type="text/javascript" src="jspdf/jspdf.js"></script>
<script type="text/javascript" src="jspdf/libs/base64.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<style>
.navbar{
margin-bottom:0;
border-radius:0;
background-color:#2E2E2E;
color:#FFF;
padding:1% 0;
font-size=1.2m;
border:0;
}
.navbar-brand{
float: left;
min-height:120px;
padding:0 30px 50px;
}
.item{
height:490px;
width:1378px;
}
</style>
<body>
<nav class = "navbar navbar-inverse">
	<div class = "container-fluid">
		<div class="navbar-header">
			<a class = "navbar-brand"><img src="https://image.ibb.co/frbj3H/smartek21_logo_440.png"></a>
		</div>
		<div class="collapse navbar-collapse" id = "myNavbar">
		<ul class="nav navbar-nav navbar-left">
		
</ul>		
</div>	
</div>	
	</nav>
<div class="container">
<div class="dropdown">
<h1>Database Code Drop Scripts Validation Report</h1>
  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Download Report
  <span class="caret"></span></button>
  <ul class="dropdown-menu">
    <li><a href="#" onClick ="$('#customers').tableExport({type:'excel',escape:'false'});">XLS</a></li>
    <li><a href="#" onClick ="$('#customers').tableExport({type:'doc',escape:'false'});">DOC</a></li>
    <li><a href="#" onClick ="$('#customers').tableExport({type:'txt',escape:'false'});">TXT</a></li>
  </ul>
</div>
</br>

<table id="customers" class="table table-bordered table-hover table-condensed" >
	<thead>			
		<tr class='warning'>
			<th>FileName</th>
			<th>Status</th>
			<th>Comments</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="listValue" items="${data}">
		<tr>
			<td>${listValue.sqlFile}</td>
			<td>${listValue.status}</td>
			<td>
			<c:forEach  var="listVar" items="${listValue.comments}">
    <c:out value="${listVar}"/><br>
</c:forEach>
			
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table> 
</div>
</body>
</html>



