# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""
from hkvs_durable import DHKVS
from hkvs_impl import HKVS
import pickle as serial, os
from servers import ServersInfo

class Skel():

	def __init__(self,num):
		self.reply =""
		self.dhkvs = DHKVS(num)
	def processMessage(self,resposta,port):
		
		self.dhkvs.open()
		if resposta[0] == "10": ## create(path,name)
			self.reply="11,"+ self.dhkvs.create(resposta[1],resposta[2])
		elif resposta[0] == "20": ## put(path,name,value)
			self.reply="21,"+ self.dhkvs.put(resposta[1],resposta[2],resposta[3])
		elif resposta[0] == "30":## cas(path,name,cur_vall,new_val)
			self.reply="31,"+ self.dhkvs.cas(resposta[1],resposta[2],resposta[3],resposta[4])
		elif resposta[0] == "40":## del(path)
			self.reply="41,"+ self.dhkvs.delete(resposta[1])
		elif resposta[0] == "50":## get(path)
			self.reply="51,"+ self.dhkvs.get(resposta[1])
		elif resposta[0] == "60":## list(path)
			lista = "Eh uma entrada"
			contador=1
			if self.dhkvs.lista(resposta[1]) != "Erro:Eh uma entrada":
				lista = ""
				for i in self.dhkvs.lista(resposta[1]):
					lista = lista + str(contador) +"- "+i +" \n"
					contador=contador+1
			self.reply="61,"+lista
		elif resposta[0]=="70": ##authorize
			
			conteudo = self.authorize(resposta[1])
			self.reply = "71,"+ serial.dumps(conteudo)

		elif resposta[0] == "80":
			self.reply="Ok"
			print "80 OK"	
		
		elif resposta[0] == "90":
			self.reply = self.reconfigure(resposta[1])	
			print "90 OK"		
			
		elif resposta[0] == "-1":
			self.reply="-1"
		
		self.dhkvs.close()
		print "Dict: ",self.dhkvs.hkvs.tree
		return self.reply;
		

	def authorize(self,resposta):
		cert = open("client_pa.req","w")			
		cert.write(serial.loads(resposta))  #escreve para o ficheiro
		cert.flush()
		os.fsync(cert.fileno())			
		cert.close()
		bashCommand = "openssl x509 -req -in client_pa.req -CA ../CA_key/ca.pem -CAkey ../CA_key/privkey.pem -CAserial ../CA_key/file.srl -out client_a.pem"
		os.system(bashCommand)
		cert = open("client_a.pem","r")
		conteudo = cert.read()
		return conteudo

	def reconfigure(self,resposta):
		info = resposta.rsplit(":",3)
	
		server = ServersInfo(info[0],int(info[1])) 
		server.setServerType(int(info[2]))
		print "IP: ", info[0], "PORT: ", info[1], "TYPE", info[2]
		return server
