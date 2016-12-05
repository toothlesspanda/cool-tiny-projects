<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.ContaHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Prestações</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Contas a Emprestimo</h2>
<table align="center">

	<tr>
		<th>Valor Emprestimo</th>
		<th>Taxa</th>
		<th>Calculo do fluxo</th>
		<th>ID</th>
	</tr>
	<c:forEach var="contas" items="${helper.contasEmprestimo}">
	<tr>
		<td><c:out value="${contas.valor}"/></td>
		<td><c:out value="${contas.taxa}"/></td>
		<td><c:out value="${contas.calculaFluxo}"/></td> 
		<td><c:out value="${contas.ID}"/></td>
	</tr>
	</c:forEach>
</table>
<hr>
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
