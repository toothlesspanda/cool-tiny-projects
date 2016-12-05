<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.PlanosPagamentoHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Planos de pagamento</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Planos de pagamento</h2>
<table align="center">

	<tr>
		<th>Data Inicio</th>
		<th>Data Terminal</th>
		<th>Numero de Prestacoes pagas</th>
		<th>Prestacoes todas pagas</th>
		<th>ID</th>
	</tr>
	<c:forEach var="planos" items="${helper.planos}">
	<tr>
		<td><c:out value="${planos.dataInicio}"/></td>
		<td><c:out value="${planos.dataFim}"/></td>
		<td><c:out value="${planos.numeroPagamentos}"/></td>
		<td><c:out value="${planos.prestacoesPagas}"/></td>
		<td><c:out value="${planos.ID}"/></td>
	</tr>
	</c:forEach>
</table>
<hr>

<form action="./verPrestacoes">
		<div align ="center">
			<label for="planos">Planos de pagamento:</label>
			<select name="id">
				<c:forEach var="planos" items="${helper.planos}">
					<c:choose>
						<c:when test="${helper.ID == planos.ID}">
							<option selected ="selected" value="${planos.ID}">${planos.ID}</option>
						</c:when>
						<c:otherwise>
							<option value="${planos.ID}">${planos.ID}</option>
						</c:otherwise>	
					</c:choose>
				</c:forEach>	
			</select>
					
			<input type="submit" value="Ver prestações"/>
			
		</div>
	</form>
	<br>
	<c:if test="${helper.hasMessages}">
	<p>Mensagens</p>
	<ul>
	<c:forEach var="mensagem" items="${helper.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
<br>
	<div>
		<a href ="../index.html">Voltar atrás</a>
	</div>
</body>
</html>