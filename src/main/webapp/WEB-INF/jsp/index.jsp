<!DOCTYPE html>
<html>
<head>
<title>PlSqlAnalyzer</title>
</head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
.btn{
font-size:18px;
color:#FFF;
padding:12px 12px;
background:#2E2E2E;
border:2px solid #FFF;
}
  body { 
  background: url("https://preview.ibb.co/hmURHc/pexels_photo.jpg") no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
.carousel-caption{

top:35%;
transform:translateY(-50%);
/*text-transform:uppercase;*/
color:#FF8800;
padding=20px;
max-width:800px;
width:100% !important;
margin:0 auto;
border-radius:2px;
bow-shadow:0px 2px 2px;


}
.panel-default {
opacity: 0.6;
color:#2E2E2E;
position: absolute;
padding=20px;
height:400px;
width:500px;
}
.form-group{
position: relative;
margin:0 auto;
align:center;
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
<div id="myCarousel" class="carousel slide" data-ride = "carousel">
	</div>
	<div class="carousel-inner" roll="listbox">
	<div class="item active">
	
	<div class = "carousel-caption">
	<h1>Database Code Drop Scripts Validation Tool</h1>
	<br>
	
<div class="container">
    
        <div class="col-md-12 col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
				<div class="panel-body">
                    <form class="form-horizontal" role="form" action="/analyze">
                    <div class="form-group">
					<h2>SELECT FILES</h2>
					<br>
                        <div class="col-md-10">
                            <input type="file" class="btn btn-primary btn-file"   name="folder_input" id="folder_input" onchange="getfolder(event)" webkitdirectory="" mozdirectory="" msdirectory="" odirectory="" directory="" multipledirectory="" multiple="" />
							<br>
                        </div>
                    </div>
                    <div class="form-group">
       
						<div class="row">
                        <div class="col-md-10">
                            <input type="submit" class="btn btn-primary" name = "submit"></input>
							<br>
                        </div>
						</div>
						</div>
						
						</div>
						
						</div>
						</div>
						</div>
						</form>
						</div>
						</div>
						</div>
						</div>
						
						</body>

</html>

<script type="text/javascript">
function getfolder(e) {
	
    var files = e.target.files;
    var path = files[0].webkitRelativePath;
    var Folder = path.split("/");
    //alert(path);
}
</script>
