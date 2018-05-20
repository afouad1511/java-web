<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id= "wrapper">
	<div id="header">
	<h2>Hellwan University</h2>
	</div>
</div>

<div id= "container">
	<div id= "content">
	<input type="button" value="Add Student"
			onclick="window.location.href='add_student_form.jsp'; return false"
			class="add-student-button"/>
	<table>
		<tr>
			<th>FirstName</th>
			<th>LastName</th>
			<th>Email</th>	
			<th>Action</th>		
		</tr>
		<c:forEach var="s" items="${STUDENTS_LIST}">
				<c:url var="tempLink" value="StudentControllerServlet">
					<c:param name="command" value="LOAD"></c:param>
					<c:param name="studentId" value="${s.id}"></c:param>
				</c:url>
				
				
				<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${s.id}" />
					</c:url>
				
				
				<tr>
				<td> ${s.firstname}</td>
				<td> ${s.lastname}</td>
				<td> ${s.email}</td>
				<td> <a href="${tempLink}">Update</a> | <a href="${deleteLink}"
					onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">
					Delete</a>	
				
				</td>		
			</tr>
			
		</c:forEach>
	</table>
	
	</div>
</div>













</body>
</html>