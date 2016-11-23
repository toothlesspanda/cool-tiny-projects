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
		pass

	def connect(self,host,port,conn_sock):
		conn_sock.connect((host,port))
		sl_sock = ssl.wrap_socket(conn_sock, ssl_version = ssl.PROTOCOL_TLSv1, cert_reqs = ssl.CERT_REQUIRED, ca_certs ="../CA_key/ca.pem",keyfile ="server.key", certfile="server.pem")
		return sl_sock
		

	def disconnect(self,sl_sock):
		sl_sock.close()

	def recvall(self,size, sckt):
		msg= ""
		while size > 0:
			res = sckt.recv(size)
			size = size - sys.getsizeof(res)
			msg = msg + res
			if not res:
				msg = ""
		return msg

	def processaPedidos(self,mensagem,sl_sock):
		sl_sock.sendall(serial.dumps(sys.getsizeof(mensagem),-1))#envia o tamanho
		print ""
		sl_sock.sendall(serial.dumps(mensagem,-1)) #envia mensagem serializada

		size = serial.loads(sl_sock.recv(1024))
		msg = self.recvall(size,sl_sock)

		if msg == "" :
			msg = serial.dumps("servidor",-1)
	
		return serial.loads(msg)

