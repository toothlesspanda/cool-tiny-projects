# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""

import socket as s, pickle as serial,select as sel
import sys, ssl, time
from hkvs_skel import Skel
from servers import ServersInfo
from threading import Thread
#from configservice import ServersInfo
#from configservice import Config



def backup(resposta_cliente,sl_bk,num):							

	#envia "primario" para o backup
	msg_serial = serial.dumps("primario",-1)
	size = serial.dumps(sys.getsizeof(msg_serial),-1)

	sl_bk.sendall(size)	
	sl_bk.sendall(msg_serial)

	#envia mensagem do cliente para backup
	msg_serial = serial.dumps(resposta_cliente,-1)
	size = serial.dumps(sys.getsizeof(msg_serial),-1)

	sl_bk.sendall(size)	
	sl_bk.sendall(msg_serial)

	msg = sl_bk.recv(1024)
	size = serial.loads(msg)
	msg = recvall(size, sl_bk)
	mensagem_bk = serial.loads(msg)
	if num == 1:	
		bk_result[0]=mensagem_bk
	else:
		bk_result[1]=mensagem_bk

				
def recvall(size, sckt):
	msg= ""
	while size > 0:
		res = sckt.recv(size)
		size = size - sys.getsizeof(res)
		msg = msg + res
	return msg



try:
	if len(sys.argv) == 3:
		HOST=''
		PORT= int(sys.argv[1])		
		num = int(sys.argv[2])	
	else:
		raise EOFError
except:
	print "Erro:Faltam argumentos"
	exit()	

sock=s.socket(s.AF_INET,s.SOCK_STREAM)
sock.setsockopt(s.SOL_SOCKET,s.SO_REUSEADDR,1)

bk_result=[None]*2
threads_bk = []

sock.bind((HOST,PORT))
skell = Skel(num)
sock.listen(1)
SocketList = [sock,sys.stdin]

primario = None
conectado = False
sock_client1=s.socket(s.AF_INET,s.SOCK_STREAM)
sock_client2=s.socket(s.AF_INET,s.SOCK_STREAM)
	

lista_servers=[]

while True:
	
	R, W, X = sel.select(SocketList, [], [])
	for sckt in R:					#servidor primari
		if sckt == sys.stdin:
			comando = sys.stdin.readline()
			if comando == "exit\n":
				exit()
		elif sckt is sock:
	
			conn_sock, addr= sock.accept()
			sl_sock = ssl.wrap_socket(conn_sock, server_side= True, ssl_version=ssl.PROTOCOL_TLSv1,ca_certs ="../CA_key/ca.pem", cert_reqs = ssl.CERT_REQUIRED, keyfile = "server.key", certfile = "server.pem")
			print "Ligado a %s", addr
			SocketList.append(sl_sock)
			
		else:
		
			msg= sckt.recv(1024)

			if msg:
				size = serial.loads(msg)
				msg = recvall(size, sckt)
			
				if msg:
					msg = serial.loads(msg)
					resposta_cliente=msg.rsplit(",",5)
					resposta = skell.processMessage(resposta_cliente,PORT)

					if resposta_cliente[0] == "90":
					
						server = resposta	
						lista_servers.append(server)	
				
						if server.getPort() == PORT and server.getServerType() != 1:
							print "secundario"
							primario = False
						elif server.getPort() == PORT and server.getServerType() == 1:
							print "primario"
							primario = True
						else:
							pass
					elif resposta_cliente[0] == "80":
						msg_serial = serial.dumps(resposta,1)
						size = serial.dumps(sys.getsizeof(msg_serial),-1)
						sckt.sendall(size)	
						sckt.sendall(msg_serial)
					else: #eh um cliente normal
						
						
						if primario == True:
							print "RESPOSTA DO CLIENTE !!!! ", resposta_cliente[0]
						
							if resposta_cliente[0] == "10" or resposta_cliente[0] == "20" or resposta_cliente[0] == "30" or resposta_cliente[0] == "40":
							
								print "PORT 10 40  30 ", PORT			
							### backup init ###
							
								    #cria ligacao com os sockets backups
								if PORT == 9995:
									sock_client1.connect(('127.0.0.1',9994))
									sock_client2.connect(('127.0.0.1',9993))
									sl_bk1 = ssl.wrap_socket(sock_client1, ssl_version = ssl.PROTOCOL_TLSv1, cert_reqs = ssl.CERT_REQUIRED, ca_certs ="../CA_key/ca.pem",keyfile ="server.key", certfile="server.pem")
									sl_bk2 = ssl.wrap_socket(sock_client2, ssl_version = ssl.PROTOCOL_TLSv1, cert_reqs = ssl.CERT_REQUIRED, ca_certs ="../CA_key/ca.pem",keyfile ="server.key", certfile="server.pem")
																											
									threads_bk.append(Thread(target = backup , args=(resposta_cliente,sl_bk1,1,) ))
									threads_bk.append(Thread(target = backup , args=(resposta_cliente,sl_bk2,2,) ))
												
									threads_bk[0].start()
									threads_bk[1].start()		
							 		
									for i in range(2):							
										threads_bk[i].join()
							
									bk1 = bk_result[0].split(",",5)
									bk2 = bk_result[1].split(",",5)

									if (bk1[1] == "OK" and bk2[1] == "OK"):
										msg_serial = serial.dumps(bk_result[0],1)
										size = serial.dumps(sys.getsizeof(msg_serial),-1)
										sckt.sendall(size)	
										sckt.sendall(msg_serial)
									sl_bk1.close()
									sl_bk2.close()
									sock_client1.close()
									sock_client2.close()
								
								if PORT == 9994:
									sock_client1.connect(('127.0.0.1',9993))
									sl_bk1 = ssl.wrap_socket(sock_client1, ssl_version = ssl.PROTOCOL_TLSv1, cert_reqs = ssl.CERT_REQUIRED, ca_certs ="../CA_key/ca.pem",keyfile ="server.key", certfile="server.pem")
																											
									threads_bk.append(Thread(target = backup , args=(resposta_cliente,sl_bk1,1,) ))									
									print "bakcup 2o primario"	
									threads_bk[0].start()
									threads_bk[0].join()
									print "bakcup 2o primario"
									bk1 = bk_result[0].split(",",5)
									print "bakcup 2o primario"
									if bk1[1] == "OK":
										print "bakcup 2o primario"
										msg_serial = serial.dumps(bk_result[0],1)
										size = serial.dumps(sys.getsizeof(msg_serial),-1)
										sckt.sendall(size)	
										sckt.sendall(msg_serial)

									sl_bk1.close()
							else:
								print "PORT 90 80 50 60", PORT
								msg_serial = serial.dumps(resposta,1)
								size = serial.dumps(sys.getsizeof(msg_serial),-1)
								sckt.sendall(size)	
								sckt.sendall(msg_serial)
						elif primario == False :
						
				
							if msg == "primario":  #verifica que o client e primario
								msg = sckt.recv(1024)
								size = serial.loads(msg)
								msg = recvall(size, sckt)
								resposta_cliente = serial.loads(msg)
							
								resposta = skell.processMessage(resposta_cliente,PORT)	
							
								msg_serial = serial.dumps(resposta,-1)
								size = serial.dumps(sys.getsizeof(msg_serial),-1)
	
								sckt.sendall(size)	
								sckt.sendall(msg_serial)
								print "backup enviou"
							
						elif primario != True:	#outros clientes
								print "secccc "
								msg_serial = serial.dumps("backup",-1)
								size = serial.dumps(sys.getsizeof(msg_serial),-1)
	
								sckt.sendall(size)
								print ""	
								sckt.sendall(msg_serial)
								sckt.close()
								SocketList.remove(sckt)		
								print "Cliente %s", addr ," foi abaixo"
					
				else:
				
					sckt.close()
					SocketList.remove(sckt)		
					print "Cliente %s", addr ," foi abaixo" 
			else:
				sckt.close()
				SocketList.remove(sckt)		
				print "Cliente %s", addr ," foi abaixo"
		

		
sock.close()
