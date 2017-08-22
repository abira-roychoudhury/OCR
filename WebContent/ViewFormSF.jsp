<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Image Uploaded</title>
</head>
<script type="text/javascript">
var data = <%=request.getAttribute("jsonCoord")%> 
var img = '<%=request.getAttribute("imgBase64").toString()%>'
var compressedDim = {"height" : <%=request.getAttribute("compressHeight")%>,
					 "width" : <%=request.getAttribute("compressWidth")%>} 
var fileType = '<%=request.getAttribute("fileType")%>'
var salesforcerecordID = '<%=request.getAttribute("salesforcerecordID")%>'
</script>
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<body>

	<% LinkedHashMap<String,String> displayDocument = (LinkedHashMap<String,String>)request.getAttribute("displayDocument");
		request.setAttribute("displayDocument",displayDocument); %>

	<div class="container">
	<div class="row" style="margin-top: 50px">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Document type <span class="label label-primary"><%=request.getAttribute("fileType")%></span></h3>
				</div>

		
				<div class="panel-body">
					
					<img id="image" alt=""  class="col-sm-12 col-md-12 col-xs-12"  src="" style="margin-bottom:10px">
					
					<div class="text-center" >
						<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#rawdata">Raw Data</button>
					</div>
				</div>	
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						Data
					</h3>
				</div>
				<div class="panel-body">
					<form>
						<div class="view-panel" style="">
							<c:forEach var="doc" items="${displaydocument}" varStatus="itemsRow">
								<div class="form-group">
									<c:set var = "addrKey" scope = "session" value = "Address"/>
									<label for="${doc.key}">${doc.key}</label>
									<c:choose>
         						         <c:when test = "${doc.key == addrKey}">
								            <textarea class="form-control" id="${doc.key}" name="${doc.key}" rows="4" cols="50">${doc.value}</textarea>
								         </c:when>								         		         
								         <c:otherwise>
								            <input class="form-control" type="text" value="${doc.value}" id="${doc.key}" name="${doc.key}">
								          </c:otherwise>
								      </c:choose>									
								</div>
							</c:forEach>
						</div>
						
							<button type="button" id="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						Timestamp Logs
					</h3>
				</div>
				<div class="panel-body">
					Upload Image : <%=request.getAttribute("Upload Image") %><br>
					<%-- Image Preprocessing : <%=request.getAttribute("Upload Image") %><br> --%>
					Base 64 conversion : <%=request.getAttribute("Base 64 conversion") %><br>
					Vision API Call : <%=request.getAttribute("Vision API Call") %><br>
					Templating : <%=request.getAttribute("Templating") %><br>
					
					
				</div>
			</div>
		</div>
</div>

<!-- Modal -->
  <div class="modal fade" id="rawdata" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Raw Data</h4>
        </div>
        <div class="modal-body">
          <p><%=request.getAttribute("Description") %></p>
          <p><%=request.getAttribute("document") %></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
	
  <script src='js/jquery.zoom.js'></script>
  <script src= "js/script.js?x=6" type="text/javascript"></script>
</body>
</html>