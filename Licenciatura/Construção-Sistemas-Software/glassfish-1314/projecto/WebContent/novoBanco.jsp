<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class = "ui.helpers.BancoHelper" scope="request"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../styleapp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activus: Adicionar Banco</title>
</head>
<body>
<h1 align="center">Activus</h1>
<h2 align="center">Adicionar Banco</h2>

<form action="criarBanco" method="post">
	<table align="center">
	<tr>		
		<td><label for="designacao"> Designação:</label></td>
		<td><input class="mandatory_field" type="text" name="designacao" size="50" value="${helper.designacao}" required /></td>
		<td></td>
	</tr>
	<tr>
		<td><label for="sigla"> Sigla:</label></td>
		<td><input class="mandatory_field" type="text" name="sigla" size="50" value="${helper.sigla}" required /></td>	
		<td></td>
	</tr>
	<tr>
	<td></td>
	<td></td>
	<td>	
		<div class="button" align="right">
			<input type="submit" size="50" value="Criar Banco" />	
		</div>
	</td>
	</tr>
	</table>
</form>
	<div>
		<a href ="../index.html">Voltar atrás</a>
	</div>
	<div align="center">
<button type="button" onclick="window.location.href='../VerBancosHandler/verBancos'">Ver Bancos</button>	
	</div>

</body>
</html>
