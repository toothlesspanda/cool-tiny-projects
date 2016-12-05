<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.ContaHelper" scope="request"/>
<jsp:useBean id="helper2" class = "ui.helpers.PrestacaoHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Mapa de Fluxos</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Mapa de Fluxos</h2>
<table align="center">

	<tr>
		<th>Descrição</th>
		<th>Data</th>
		<th>Valor</th>
	</tr>
	<c:forEach var="conta" items="${helper.contas}">
	<tr>
		<td><c:out value="Conta a prazo"/></td>
		<td><c:out value="${helper.data}"/></td>
		<td><c:out value="${conta.valor}"/></td>
	</tr>
	</c:forEach>
	<c:forEach var="prestacoes" items="${helper2.prestacoes}">
	<tr>
		<td><c:out value="Prestação"/></td>
		<td><c:out value="${prestacoes.dataPagamento}"/></td>
		<td><c:out value="-${prestacoes.valorPrestacao}"/></td>
		
	</tr>
	</c:forEach>
</table>

<br>

<table align="center">

	<tr>
		<th>Total: </th>
		<th>${helper.total}</th>
	</tr>
</table>

<hr>

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