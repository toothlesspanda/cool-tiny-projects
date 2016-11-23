# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""

import socket as s, sys, pickle as serial,ssl
		

class Stub():
	
	def __init__(self):
		self.conn_sock = None

	def connect(self,host,port):
		self.conn_sock= s.socket(s.AF_INET,s.SOCK_STREAM)
		self.conn_sock.connect((host,port))
		self.sl_sock = ssl.wrap_socket(self.conn_sock, ssl_version = ssl.PROTOCOL_TLSv1, cert_reqs = ssl.CERT_REQUIRED, ca_certs ="../CA_key/ca.pem",keyfile ="client.key", certfile="client.pem")

		

	def disconnect(self):
		self.sl_sock.close()

	def create(self,mensagem):	
		return self.processaPedidos(mensagem)

	def put(self,mensagem):
		return self.processaPedidos(mensagem)

	def cas(self,mensagem):
		return self.processaPedidos(mensagem)

	def delete(self,mensagem):
		return self.processaPedidos(mensagem)
	
		return serial.loads(msg)
	def get(self,mensagem):
		return self.processaPedidos(mensagem)
	
	def list(self,mensagem):
		return self.processaPedidos(mensagem)

	def authorize(self,mensagem):
		return self.processaPedidos(mensagem)

	def recvall(self,size, sckt):
		msg= ""
		while size > 0:
			res = sckt.recv(size)
			size = size - sys.getsizeof(res)
			msg = msg + res
			if not res:
				msg = ""
		return msg

	def processaPedidos(self,mensagem):
		self.sl_sock.sendall(serial.dumps(sys.getsizeof(mensagem),-1))#envia o tamanho
		print ""
		self.sl_sock.sendall(serial.dumps(mensagem,-1)) #envia mensagem serializada

		size = serial.loads(self.sl_sock.recv(1024))
		msg = self.recvall(size,self.sl_sock)

		if msg == "" :
			msg = serial.dumps("servidor",-1)
	
		return serial.loads(msg)

