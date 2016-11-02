# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""

class HKVS():
 
	def __init__(self):
		self.tree={}
	

	def create(self,path,name):
		""" Cria uma diretoria com o nome name no caminho path, caso não
		exista nenhum elemento com esse nome. Retorna ‘OK’ ou ‘NOK’ em
	        caso de sucesso ou fracasso da operação, respetivamente.
		"""	

		status = "NOK"
		caminho=path.split("/")
		n_barras = path.count("/")
		if n_barras > 0:
			if path=="/":
				if not self.tree.has_key(name):
					self.tree[name]={}
					status="OK"
				else:
					status= "NOK"
			else:
				x = self.percorreTree(self.tree,caminho,caminho[-1])
				if x != "NOK":
					if not x.has_key(name):
						x[name]={}
						status="OK"
					else:
						status="NOK"
		return status
		

	def put(self,path, name, value):
		"""
		Cria uma entrada com o nome name e valor value especificados, numa
		diretoria definida pelo caminho path. Caso exista outra entrada
		com o mesmo nome, o valor associado é substituído; caso exista uma
		diretoria com o mesmo nome a operação falha. Retorna ‘OK’ ou ‘NOK’
		em caso de sucesso ou fracasso da operação, respetivamente.
		"""

		status="NOK"
		caminho = path.split("/")		
		n_barras = path.count("/")
		if n_barras > 0:
			if path == "/":
				if not self.tree.has_key(name):		
					self.tree[name]=value
					status="OK"
				else:
					if type(self.tree[name]) != dict:
						self.tree[name]=value
						status="OK"
			else:
				x = self.percorreTree(self.tree,caminho,caminho[-1])	
				if x != "NOK":
					if not x.has_key(name) :
						x[name]=value
						status="OK"
					else:
						if type(x[name]) != dict:
							x[name]=value
							status= "OK"

		return status

	def cas(self,path, name, cur_val, new_val):
		"""
		Exatamente igual a put, mas apenas substitui o valor associado a
		nome se o valor antigo corresponde a cur_val.
		"""
		caminho = path.split("/")
		status= "NOK"
		n_barras = path.count("/")
		if n_barras > 0:
			if path == "/":
				if self.tree.has_key(name) and self.tree[name] == cur_val:		
					self.tree[name]= new_val
					status="OK"
			else:	
				x = self.percorreTree(self.tree,caminho,caminho[-1])
				if x != "NOK":
					if x.has_key(name) and x[name] == cur_val :
						x[name] = new_val
						status = "OK"

		return status
	


	def delete(self,path):
		"""
		Remove o último elemento do caminho path se o elemento for uma
		entrada.
		Caso este elemento seja uma diretoria, ele só é apagado se a
		diretoria estiver vazia. Retorna ‘OK’ ou ‘NOK’ em caso de sucesso
		ou fracasso da operação, respectivamente.
		"""

		caminho = path.split("/")	
		status = "NOK"
		n_barras = path.count("/")
		
		if path != "/" and n_barras > 0:
			if (caminho[-2] ==""):
				if type(self.tree[caminho[-1]]) != dict:
					del self.tree[caminho[-1]]
					status="OK"
				else:
					if len(self.tree[caminho[-1]].keys()) == 0:
						del self.tree[caminho[-1]]
						status="OK"
			else:	
				x = self.percorreTree(self.tree,caminho,caminho[-1])

				if x != "NOK":		 
					if type(x) != dict:  
							del x
							status= "OK"
					else :
						if len(x.keys()) == 0:
							del x
							status= "OK"
		return status

	def get(self,path):
		"""
		Retorna o valor associado ao ultimo elemento da path; se este
		elemento for uma diretoria, retorna uma mensagem de erro
		‘Diretoria’.
		"""
		
		caminho = path.split("/")
		try:	
			x = self.percorreTree(self.tree,caminho,caminho[-1])

			if x != "NOK":
				if type(x) != dict:  ##se for uma entrada
					return x
				else :
		    			raise EOFError
			else :
		    		raise EOFError
		except:
			return "Erro:Eh uma directoria"


	def lista(self,path):
		"""
		Retorna a lista com os elementos dentro do ultimo elemento da
		path, se este for uma diretoria. Se o elemento for uma entrada,
		retorna ‘Entrada’
		"""
		
		caminho = path.split("/")	
		n_barras = path.count("/")

		if n_barras > 0:
			if path == '/':
				return self.tree.keys();
			else:
				x = self.percorreTree(self.tree,caminho,caminho[-1])
				if x != "NOK" :
					if type(x) == dict:  #se for uma directoria
				    		return x.keys()
					
		return "Erro:Eh uma entrada"

	def percorreTree(self,x,path,ultimo):   ##metodo auxiliar para percorrer a arvore, devolve o conteudo do ultimo elemento	
					
			if x.has_key(path[1]):			
				if path[1] == ultimo and len(path) == 2:			
					return x[path[1]]
	
			else :
				return "NOK"

			return self.percorreTree(x[path[1]],path[1:len(path)],ultimo)
