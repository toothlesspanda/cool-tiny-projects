<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class="application.web.datapresentation.NovoClienteHelper" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../app.css"> 
<title>V and A: adicionar cliente</title>
</head>
<body>
<h2>Adicionar Cliente</h2>
<form action="criarCliente" method="post">
    <div class="mandatory_field">
    	<label for="designacao">Designação:</label> 
    	<input type="text" name="designacao" size="50" value="${helper.designacao}"/> 
    </div>
    <div class="mandatory_field">
		<label for="npc">Número de pessoa colectiva:</label> 
		<input type="text" name="npc" value="${helper.npc}"/> <br/>
    </div>
   <div class="optional_field">
   		<label for="telefone">Telefone:</label> 
   		<input type="text" name="telefone" value="${helper.telefone}"/>
   </div>
   <div class="mandatory_field">
		<label for="desconto">Tipo de desconto:</label>
		<select name="desconto">  
			<c:forEach var="desconto" items="${helper.descontos}">
				<c:choose>
    				<c:when test="${helper.tipoDesconto == desconto.tipoDesconto}">
						<option selected = "selected" value="${desconto.tipoDesconto}">${desconto.descricao}</option>
				    </c:when>
    				<c:otherwise>
						<option value="${desconto.tipoDesconto}">${desconto.descricao}</option>
				    </c:otherwise>
					</c:choose>
			</c:forEach> 
		</select>
   </div>
   <div class="button" align="right">
   		<input type="submit" value="Criar Cliente">
   </div>
</form>
<c:if test="${helper.hasMessage}">
	<p>Mensagens</p>
	<ul>
	<c:forEach var="mensagem" items="${helper.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
</body>
</html>