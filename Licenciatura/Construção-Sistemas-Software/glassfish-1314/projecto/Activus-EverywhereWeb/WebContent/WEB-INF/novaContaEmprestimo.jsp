<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.BancoHelper" scope="request"/>
<jsp:useBean id="helper2" class = "ui.helpers.ContaHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Adicionar conta a prazo</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Bancos</h2>
<table align="center">

	<tr>
		<th>Designação</th>
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
	<!--
	<form action="./adicionarConta">
	-->
		<div align ="center">
			
			<!--	
			<input type="select" value="Adicionar Conta"/>
			-->
		</div>
	<!--	
	</form>
	-->
	<!--	<form action="criarContaEmprestimo" method="post">-->
	<form action="emprestimo" method="post">
	<div align ="center">
	<label for="banco">Banco:</label>
			<select name="id">
				<c:forEach var="banco" items="${helper.bancos}">
					<c:choose>
						<c:when test="${helper.ID == banco.ID}">
							<option selected ="selected" value="${banco.ID}">${banco.designacao}</option>
						</c:when>
						<c:otherwise>
							<option value="${banco.ID}">${banco.designacao}</option>
						</c:otherwise>	
					</c:choose>
				</c:forEach>	
			</select>
	</div>
	
	<br>
	<div class="button" align="center">
			<input type="submit" size="50" value="Escolher banco" />	
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