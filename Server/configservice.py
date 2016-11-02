# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""

import socket as s, pickle as serial
import sys,os,ssl,time
from hkvs_stub import Stub
from servers import ServersInfo
from threading import Thread

				############## Funcoes auxiliares
def is_alive(server,sl_sock):
	r = stub.processaPedidos("80",sl_sock)

	return r 

def reconfigure(server,lista_servers):

	if server.getPort() == lista_servers[0].getPort() and lista_servers[0].isOff() == False: 
		server.setServerType(1)
		lista_servers[0] = server
		lista_servers[3] = True
	elif server.getPort() == lista_servers[1].getPort() and lista_servers[0].isOff() == False:
		server.setServerType(2)	
		lista_servers[1] = server
	elif server.getPort() == lista_servers[2].getPort() and lista_servers[0].isOff() == False:
		server.setServerType(3)
		lista_servers[2] = server
	elif lista_servers[0].isOff() == True and (lista_servers[1].getServerType()!=1 and lista_servers[2].getServerType() != 2) : #se o primario esta morto e ainda nao esta atribuido nenhum primario
		print "Primario morreu"
		if lista_servers[1].isOff() == False:
			lista_servers[1].setServerType(1)
			lista_servers[2].setServerType(2)
		else:
			lista_servers[2].setServerType(1)
		lista_servers[3] = True

	return lista_servers

				############## Main
try:
	if len(sys.argv) == 3:
		n_opera = int(sys.argv[1])
		timeout = int(sys.argv[2])
	else:
		raise EOFError
except:
	print "Erro:Faltam argumentos"
	print "configservice.py <ip> <porto> <numero Operacoes> <timeout>"
	exit()


stub = Stub()

server1 = ServersInfo('127.0.0.1',9995)
server2 = ServersInfo('127.0.0.1',9994)
server3 = ServersInfo('127.0.0.1',9993)

conn_sock1= s.socket(s.AF_INET,s.SOCK_STREAM) #primario
conn_sock2= s.socket(s.AF_INET,s.SOCK_STREAM) #bk1
conn_sock3= s.socket(s.AF_INET,s.SOCK_STREAM) #bk2

lista_servers = [server1,server2,server3,False]
lista_ssl = [None]*3

while(True):

	for server in lista_servers[:3]:
		print "Server - Porto:",server.getPort(), " Tipo: ", server.getServerType(), " Off ?",server.isOff()
		print "Primario vivo?", lista_servers[3]	

		if server.isConectado() == False and server.isOff() == False:
			
			if server.getPort() == lista_servers[0].getPort() :	#server1 socket1
				sl_sock = stub.connect(server.getIp(),server.getPort(),conn_sock1)
				lista_ssl[0] = sl_sock
			elif server.getPort() == lista_servers[1].getPort():	#server2 socket2
				sl_sock = stub.connect(server.getIp(),server.getPort(),conn_sock2)
				lista_ssl[1] = sl_sock
			elif server.getPort() == lista_servers[2].getPort():	#server2 socket2
				sl_sock = stub.connect(server.getIp(),server.getPort(),conn_sock3)
				lista_ssl[2] = sl_sock
			server.setConectado()
			
		
		if server.getServerType() == 0 or lista_servers[3] == False:	
			print "Reconfigure Server porto", server.getPort()
			lista_servers = reconfigure(server,lista_servers)
	
			if server.getPort() == 9995 and server.isOff() == False: 
				for i in range(len(lista_servers)-1):
					mensagem= "90,"+lista_servers[i].getIp() + ":"+ str(lista_servers[i].getPort()) + ":"+str(lista_servers[i].getServerType())
					print "Server 1 tipo reconfigure", str(lista_servers[i].getServerType())
					lista_ssl[0].sendall(serial.dumps(sys.getsizeof(mensagem),-1)) 
#envia o tamanho	
					lista_ssl[0].sendall(serial.dumps(mensagem)) #envia mensagem serializada
					primario_existe = True
			if server.getPort() == 9994 and server.isOff() == False:
				
				for i in range(len(lista_servers)-1):
					mensagem= "90,"+lista_servers[i].getIp() + ":"+ str(lista_servers[i].getPort()) + ":"+str(lista_servers[i].getServerType())
					print "Server 2 tipo reconfigure", str(lista_servers[i].getServerType())
					lista_ssl[1].sendall(serial.dumps(sys.getsizeof(mensagem),-1))#envia o tamanho
					lista_ssl[1].sendall(serial.dumps(mensagem)) #envia mensagem serializada
			if server.getPort() == 9993 and server.isOff() == False: 
				for i in range(len(lista_servers)-1):
					mensagem= "90,"+lista_servers[i].getIp() + ":"+ str(lista_servers[i].getPort()) + ":"+str(lista_servers[i].getServerType())
					print "Server 3 tipo reconfigure", str(lista_servers[i].getServerType())
					lista_ssl[2].sendall(serial.dumps(sys.getsizeof(mensagem),-1))#envia o tamanho
					lista_ssl[2].sendall(serial.dumps(mensagem)) #envia mensagem serializada	
		else:
			
			alive = False
			if server.getPort() == 9995 and server.isOff() == False:
				for i in range(n_opera):
					try:	
						if is_alive(server,lista_ssl[0]):
							print server.getPort(),"OK"
							alive = True
							pass							
					
					except :
						print "Tentativa ", i+1
						i+=1
						if i == n_opera and alive == False:
							server.setServerType(-1)
							server.setOff(True)
							lista_servers[0] = server
							lista_servers[3] = False
							stub.disconnect(lista_ssl[0])
				pass
			
			if server.getPort() == 9994 and server.isOff() == False:
				for i in range(n_opera):
					try:	
						if is_alive(server,lista_ssl[1]):
							print server.getPort(),"OK"
							alive = True
							pass							
					
					except :
						print "Tentativa ", i+1
						i+=1
						if i == n_opera and alive == False:
							server.setServerType(-1)
							server.setOff(True)
							lista_servers[1] = server
							stub.disconnect(lista_ssl[1])
					pass
			if server.getPort() == 9993 and server.isOff() == False:
				for i in range(n_opera):
					try:	
						if is_alive(server,lista_ssl[2]):
							print server.getPort(),"OK"
							alive = True
							pass							
					
					except :
						print "Tentativa ", i+1
						i+=1
						if i == n_opera and alive == False:
							server.setServerType(-1)
							server.setOff(True)
							lista_servers[2] = server
							stub.disconnect(lista_ssl[2])
				pass
		time.sleep(timeout)
