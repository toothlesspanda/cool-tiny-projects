<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper2" class = "ui.helpers.PrestacaoHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Prestações</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Prestações</h2>
<table align="center">

	<tr>
		<th>Data Limite</th>
		<th>Data Pagamento</th>
		<th>Valor da Prestacao</th>
		<th>Pagamento</th>
		<th>ID</th>
	</tr>
	<c:forEach var="prestacoes" items="${helper2.prestacoes}">
	<tr>
		<td><c:out value="${prestacoes.dataLimite}"/></td>
		<td><c:out value="${prestacoes.dataPagamento}"/></td>
		<td><c:out value="${prestacoes.valorPrestacao}"/></td>
		<td><c:out value="${prestacoes.pagamento}"/></td>
		<td><c:out value="${prestacoes.ID}"/></td>
	</tr>
	</c:forEach>
</table>
<hr>
<form action="./pagarPrestacao">
		<div align ="center">
			<label for="prestacoes">Prestações:</label>
			<select name="id">
				<c:forEach var="prestacoes" items="${helper2.prestacoes}">
					<c:choose>
						<c:when test="${helper2.ID == prestacoes.ID}">
							<option selected ="selected" value="${prestacoes.ID}">${prestacoes.ID}</option>
						</c:when>
						<c:otherwise>
							<option value="${prestacoes.ID}">${prestacoes.ID}</option>
						</c:otherwise>	
					</c:choose>
				</c:forEach>	
			</select>
					
			<input type="submit" value="Pagar prestação"/>
			
		</div>
	</form>
</body>
</html>