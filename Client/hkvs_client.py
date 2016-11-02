# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""

import socket as s, pickle as serial
import sys,os,time
from hkvs_stub import Stub


def conecta(conectado,ip,porto):
	for i in range(5):
			
		try:
			if conectado == False:	
				stub.connect(ip,porto)	
				return True
			
		except s.error:
			print "Erro no servidor com porto ",porto,". Tentativa ",i+1
			time.sleep(2)
			if (i+1) == 5 and conectado == False:
				print "Nao conseguimos ligar ao servidor"
		
		pass
	return conectado	
try:
	if len(sys.argv) == 3:
		HOST = sys.argv[1]
		PORT = int(sys.argv[2])
	else:
		raise EOFError
except:
	print "Erro:Faltam argumentos"
	exit()
opc=""
msg=""
lista=""
conectado = False

stub = Stub()


conectado = conecta(conectado,HOST,PORT)

if conectado == False:
	conectado1 = conecta(conectado,HOST,9994)
	if conectado1 == False:
		conectado2 = conecta(conectado,HOST,9993)

		if conectado2 == False:
			exit()

while opc != "0" :
	print "ali"
	print "\t MENU:"
	print "1 - Create"
	print "2 - Put"
	print "3 - Cas"
	print "4 - Delete"
	print "5 - Get"
	print "6 - List"
	print "7 - Authorize"
	print "0 - para fechar"
	try:
		opc=raw_input("Opcao: ")
			
		if opc == "1" :
			print "Opcao Create:"
			path=raw_input("Path: ")
			name=raw_input("Name: ")
			msg = "10,"+path+","+name;
			msg = stub.create(msg)
		elif opc == "2" :
			print "Opcao Put: \n"
			path=raw_input("Path: ")
			name=raw_input("Name: ")
			value=raw_input("Value: ")
			msg = "20,"+path+","+name+","+value;
			msg = stub.put(msg)
		elif opc == "3" :
			print "Opcao Cas: "
			path=raw_input("Path: ")
			name=raw_input("Name: ")
			cur_val=raw_input("Cur_val: ")
			new_val=raw_input("New_val: ")
			msg = "30,"+path+","+name+","+cur_val+","+new_val;
			msg = stub.cas(msg)
		elif opc == "4" :
			print "Opcao Delete:"
			path=raw_input("Path: ")
			msg = "40,"+path;
			msg = stub.delete(msg)
		elif opc == "5" :
			print "Opcao Get:"
			path=raw_input("Path: ")
			msg = "50,"+path;
			msg = stub.get(msg)
		elif opc == "6" :
			print "Opcao List: \n"
			path=raw_input("Path: ")
			msg = "60,"+path;
			msg = stub.list(msg)
		elif opc == "7":
			print "Opcao Authorize: \n"
			cert = open("client_na.req","r")
			conteudo = cert.read();
			msg = "70,"+ serial.dumps(conteudo)
			msg = stub.authorize(msg)
		elif opc == "0":
			print "Obrigado!"
			exit()
		else :
			msg="-1"


		reply = msg.split(",",5)

		if reply[0] == "11": ## create(path,name)	 
			if reply[1] == "OK":
				print "--------[ Criado com sucesso ]--------"
			else:
				print "--------[ Nao foi criado ]--------"
		elif reply[0] == "21": ## put(path,name,value)
			if reply[1] == "OK":
				print "--------[ Inserido com sucesso ]--------"
			else:
				print "--------[ Nao foi inserido ]--------"
		elif reply[0] == "31":## cas(path,name,cur_vall,new_val)
			if reply[1] == "OK":
				print "--------[ Alterado com sucesso ]--------"
			else:
				print "--------[ Nao foi alterado ]--------"
		elif reply[0] == "41":## del(path)
			if reply[1] == "OK":
				print "--------[ Apagado com sucesso ]--------"
			else:
				print "--------[ Nao foi apagado ]--------"
		elif reply[0] == "51":## get(path)
			if reply[1] != "Erro:Eh uma directoria":		
				print "Value: "+ reply[1]
			else:
				print "--------[ Erro:Eh uma directoria ]--------"
		elif reply[0] == "61":## list(path)
			if reply[1] != "Eh uma entrada":
				lista="--------[ Lista ]--------\n"
				for i in reply[1]:
					lista= lista + i
			else :
				lista= "--------[ Erro:Eh uma entrada ]--------"
			print lista
		elif reply[0] == "71": ##authorize
			cert = open("client_na.pem","w")			
			cert.write(serial.loads(reply[1]))  #escreve para o ficheiro
			cert.flush()
			os.fsync(cert.fileno())			
			cert.close()
		elif reply[0] == "servidor":
			print "--------[ Servidor foi abaixo ]--------"
			exit()
		elif reply[0] == "backup":
			print "--------[ Servidor não disponivel. Ligue-se a outro ]--------"
			
			conectado1 = conecta(conectado,HOST,9994)
			if conectado1 == False:
				conectado2 = conecta(conectado1,HOST,9993)

				if conectado2 == False:
					exit()
		elif reply[0] =="-1":
			print "--------[ Nao eh opcao ]--------"
	except KeyboardInterrupt or s.error:
		print "\n--------[ A fechar ]--------"
		stub.disconnect()
		exit()
stub.disconnect()
