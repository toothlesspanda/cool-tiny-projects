<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper2" class = "ui.helpers.GestorHelper" scope="request"/>
<jsp:useBean id="helper3" class = "ui.helpers.ContaHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus:Editar gestor</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Gestor</h2>
<table align="center">

	<tr>
		<th>Nome</th>
		<th>Telefone</th>
		<th>Email</th>
		<th>Banco</th>
		<th>ID do Gestor</th>
		<th>ID do Banco</th>
	</tr>
	<c:forEach var="gestor" items="${helper2.gestores}">
	<tr>
		<td><c:out value="${gestor.nome}"/></td>
		<td><c:out value="${gestor.telefone}"/></td>
		<td><c:out value="${gestor.email}"/></td>
		<td><c:out value="${gestor.banco}"/></td>
		<td><c:out value="${gestor.ID}"/></td>
		<td><c:out value="${gestor.IDBanco}"/></td>
	</tr>
	</c:forEach>
</table>
<hr>
	<!--
	<form action="./adicionarConta">
	
		<div align ="center">
			
				
			<input type="select" value="Adicionar Conta"/>
			
		</div>
		
	</form>
	-->
	<form action="criarContaPrazo" method="post">
	<div align ="center">
	<label for="gestor">Gestor:</label>
			<select name="id">
				<c:forEach var="gestor" items="${helper2.gestores}">
					<c:choose>
						<c:when test="${helper2.ID == gestor.ID}">
							<option selected ="selected" value="${gestor.ID}">${gestor.nome}</option>
						</c:when>
						<c:otherwise>
							<option value="${gestor.ID}">${gestor.nome}</option>
						</c:otherwise>	
					</c:choose>
				</c:forEach>	
			</select>
	</div>
	<table align="center">
	<tr>		
		<td><label for="deposito"> Deposito:</label></td>
		<td><input class="mandatory_field" type="text" name="deposito" size="50" value="${helper3.deposito}" /></td>
		<td></td>
	</tr>
	<tr>
		<td><label for="taxaJuro"> Taxa de Juro:</label></td>
		<td><input class="mandatory_field" type="text" name="taxaJuro" size="50" value="${helper3.taxaJuro}" /></td>	
		<td></td>
	</tr>
	<tr>
		<td><label for="dataLimite"> Data Limite:</label></td>
		<td></td>
	</tr>
	<tr>
		<td><label for="ano"> Ano:</label></td>
		<td><input class="mandatory_field" type="text" name="ano" size="50" value="${helper3.ano}" /></td>	
		<td></td>
	</tr>
	<tr>
		<td><label for="mes"> Mes:</label></td>
		<td><input class="mandatory_field" type="text" name="mes" size="50" value="${helper3.mes}" /></td>	
		<td></td>
	</tr>
	<tr>
		<td><label for="dia"> Dia:</label></td>
		<td><input class="mandatory_field" type="text" name="dia" size="50" value="${helper3.dia}" /></td>	
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
			<input type="submit" size="50" value="Adicionar Conta Prazo" />	
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