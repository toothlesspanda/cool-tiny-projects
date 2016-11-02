class ServersInfo():
	
	def __init__(self,ip,porto):
		self.ip = ip	
		self.port = porto
		self.off = False  
		self.tipo = 0 #1 - primario , 2 -bk1 , 3- bk2
		self.conectado = False
		
	def getIp(self):
		return self.ip

	def getPort(self):
		return self.port
	
	def isOff(self):
		return self.off
	
	def setOff(self,boolean):
		self.off = boolean

	def isConectado(self):
		return self.conectado
	
	def setConectado(self):
		self.conectado = True
	
	def setServerType(self,tipo):
		self.tipo = tipo

	def getServerType(self):
		return self.tipo
