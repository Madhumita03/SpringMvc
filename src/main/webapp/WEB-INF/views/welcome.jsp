<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/css/bootstrap.min.css">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2 id="article_header" class="text-warning" align="center">Todo
			Application</h2>
		<div>&nbsp;</div>

		<!-- Div to add a new user to the mongo database -->
		<div id="add_new_user">
			<c:url var="addUrl" value="/todo/add" />
			<a id="add" href="${addUrl}" class="btn btn-success">Add Todo</a>
		</div>
		<div>&nbsp;</div>

		<!-- Table to display the user list from the mongo database -->
		<table id="users_table" class="table">
			<thead>
				<tr align="center">
					<th>label</th>
					<th>Description</th>
					<!--  <th>Due Date</th> -->
					<th>Status</th>
					<th colspan="2"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr align="center">
						<td><c:out value="${todo.label}" /></td>
						<td><c:out value="${todo.description}" /></td>

						<td><c:out value="${todo.status}" /></td>
						<td><c:url var="editUrl" value="/todo/edit?id=${todo.id}" /><a
							id="update" href="${editUrl}" class="btn btn-warning">Update</a></td>
						<td><c:url var="deleteUrl" value="/todo/delete?id=${todo.id}" /><a
							id="delete" href="${deleteUrl}" class="btn btn-danger">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>