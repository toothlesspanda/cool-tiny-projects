<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.BancoHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Bancos</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Bancos</h2>
<table align="center">

	<tr>
		<th>Designação2</th>
		<th>Sigla</th>
		<th>ID</th>
	</tr>
	<c:forEach var="banco" items="${helper.bancos}">
	<tr>
		<td><c:out value="${banco.designacao}"/></td>
		<td><c:out value="${banco.sigla}"/></td>
		<td><c:out value="${banco.ID}"/></td>
	</tr>
	</c:forEach>
</table>
<hr>
	
	<form action="./removerBanco">
		<div align ="center">
			<label for="banco">Bancos:</label>
			<select name="banco">
				<c:forEach var="banco" items="${helper.bancos}">
					<c:choose>
						<c:when test="${helper.designacao == banco.designacao}">
							<option selected ="selected" value="${banco.designacao}">${banco.designacao}</option>
						</c:when>
						<c:otherwise>
							<option value="${banco.designacao}">${banco.designacao}</option>
						</c:otherwise>	
					</c:choose>
				</c:forEach>	
			</select>
					
			<input type="submit" value="Remover Banco"/>
			
		</div>
	</form>
	<br>
		<div align="center">
		<button type="button" onclick="window.location.href='../EditarBancoHandler/editarBanco'">Editar Banco</button>	
	</div>
	<div>
		<a href ="../index.html">Voltar atrás</a>
	</div>
</body>
</html>