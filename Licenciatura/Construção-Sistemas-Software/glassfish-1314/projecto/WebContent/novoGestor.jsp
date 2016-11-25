<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.BancoHelper" scope="request"/>
<jsp:useBean id="helper2" class = "ui.helpers.GestorHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Adicionar gestor</title>
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
	<form action="criarGestor" method="post">
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
	<table align="center">
	<tr>		
		<td><label for="nome"> Nome:</label></td>
		<td><input class="mandatory_field" type="text" name="nome" size="50" value="${helper2.nome}" /></td>
		<td></td>
	</tr>
	<tr>		
		<td><label for="telefone"> Telefone:</label></td>
		<td><input class="mandatory_field" type="text" name="telefone" size="50" value="${helper2.telefone}" /></td>
		<td></td>
	</tr>
	<tr>		
		<td><label for="email"> Email:</label></td>
		<td><input class="mandatory_field" type="text" name="email" size="50" value="${helper2.email}" /></td>
		<td></td>
	</tr>
		
	
	<tr>
	<td></td>
	<td></td>
	<td>	
		
	</td>
	</tr>
	</table>
	<br>
	<div class="button" align="center">
			<input type="submit" size="50" value="Adicionar gestor" />	
		</div>
	
	
</form>
	<br>
	<!--
		<div align="center">
		<button type="button" onclick="window.location.href='../EditarBancoHandler/editarBanco'">Editar Banco</button>	
	</div>
	-->
	
	
	
	<div>
		<a href ="../index.html">Voltar atrás</a>
	</div>
</body>
</html>