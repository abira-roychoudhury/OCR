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
<body>
	 
	<%=request.getAttribute("fileType")%><br><br>
	<img src="<%=request.getAttribute("imgBase64")%>" width = "500px" height = "400px"><br><br>
    <%=request.getAttribute("Description") %><br><br>
    <%=request.getAttribute("document") %>
    <br><br><br>
    <form>
    	<table>
    		<c:forEach var="doc" items="${document}" varStatus="itemsRow">
    			<tr>
       				<td>
       					 <c:out value="${doc.key}" />
        			</td>
        			<td><input type="text" name="" value="${doc.value}" /></td>
  				</tr>
			</c:forEach>
    	</table>
    </form>
    
    
</body>
</html>