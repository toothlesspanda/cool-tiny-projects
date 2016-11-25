<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="erroHelper" class = "ui.helpers.ErroHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Erro</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Lista de erros</h2>
<table align="center">

	<tr>
		<th>Erro</th>
	</tr>
	<c:forEach var="erro" items="${errohelper.messages}">
	<tr>
		<td><c:out value="${erro.message}"/></td>
	</tr>
	</c:forEach>
</table>
<hr>
	
	<br>
	<div>
		<a href ="../index.html">Voltar atrás</a>
	</div>
</body>
</html>