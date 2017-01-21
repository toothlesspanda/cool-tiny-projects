# -*- coding: utf-8 -*-
"""
Trabalho realizado por:
Jorge Ferreira nº 43104
Ines de Matos nº 43538
Grupo 5
"""
import time,datetime, os, pickle as s, sys
from hkvs_impl import HKVS

class DHKVS():

	def __init__(self,num):
		self.num=num	
		self.hkvs = HKVS()			
		
		if os.path.exists("dhkvs-log.txt"):
			self.f=open("dhkvs-log.txt","r")
			linhas = self.f.readlines()
			
			if len(linhas) < self.num: 			#enquanto estiver dentro do NUM de linhas
				self.recupera()
					
			else: 						##cria checkpoint
				self.checkpoint()
		
		else: 							#caso log nao exista
			self.f =open("dhkvs-log.txt","w")
			if not os.path.exists("dhkvs-ckpt.p"):
				self.hkvs = HKVS()
			else:
				self.f_chkpt = open("dhkvs-ckpt.p","r")
				conteudo = self.f_chkpt.read()
				hkvs_chkpt = s.loads(conteudo)
				os.fsync(self.f_chkpt.fileno())
				self.hkvs = hkvs_chkpt
			
		self.f=open("dhkvs-log.txt","a")
		print "Dict: ", self.hkvs.tree
			
	def open(self):  						#dentro do sistema(apos init)	
		
		if os.path.exists("dhkvs-log.txt"):
			self.f=open("dhkvs-log.txt","r")
			linhas = self.f.readlines()
			
			if len(linhas) < self.num:
				self.f=open("dhkvs-log.txt","a")
					
			else: 						##cria checkpoint
				self.checkpoint()
						
			self.f=open("dhkvs-log.txt","a")				

	def create(self,path,name):
		msg=self.hkvs.create(path,name)
		 
		if msg == "OK":
			mensagem = "10,"+path+","+name
			self.f.write(mensagem)
			self.f.write("\n")
		self.f.flush
		return msg

	def put(self,path,name,value):
		
		msg=self.hkvs.put(path,name,value)
		 
		if msg == "OK":
			mensagem = "20,"+path+","+name+","+value
			self.f.write(mensagem)
			self.f.write("\n")
		self.f.flush
	
		return msg

	def cas(self,path,name,cur_val,new_val):
		msg=self.hkvs.cas(path,name,cur_val,new_val)
		 
		if msg == "OK":
			mensagem = "30,"+path+","+name+","+cur_val+","+new_val
			self.f.write(mensagem)
			self.f.write("\n")
		self.f.flush	
		return msg	

	def delete(self,path):
		msg=self.hkvs.delete(path)
		 
		if msg == "OK":
			mensagem = "40,"+path
			self.f.write(mensagem)
			self.f.write("\n")
		self.f.flush
		return msg

	def get(self,path):
		return self.hkvs.get(path)

	def lista(self,path):
		return self.hkvs.lista(path)
	
	def close(self):
		os.fsync(self.f.fileno())
		self.f.close()

	def recupera(self):
		if os.path.exists("dhkvs-ckpt-tmp.p"):   		#caso tmp exista
			self.f=open("dhkvs-log.txt","a")			
			self.f_chkpt = open("dhkvs-ckpt-tmp.p","r")
			conteudo = self.f_chkpt.read()
			hkvs_chkpt = s.loads(conteudo)
			self.f.truncate()
			self.f_chkpt.flush()
			os.fsync(self.f_chkpt.fileno())
			os.rename("dhkvs-ckpt-tmp.p", "dhkvs-ckpt.p")
			self.hkvs = hkvs_chkpt
			
		elif os.path.exists("dhkvs-ckpt.p"):			#caso chkpt.p exista

			self.f_chkpt = open("dhkvs-ckpt.p","r")
			conteudo = self.f_chkpt.read()
			hkvs_chkpt = s.loads(conteudo)
			os.fsync(self.f_chkpt.fileno())
			self.hkvs = hkvs_chkpt

		else:
			self.hkvs = HKVS()
			
		if os.path.getsize("dhkvs-log.txt") > 0:  		#caso log exista e tenha alguma coisa la dentro
			self.f = open("dhkvs-log.txt", "r")
			logs = self.f.readlines()
			for i in logs:
				comando = i.split(",")
				
				if comando[0]== "10":
					self.hkvs.create(comando[1],comando[2].replace("\n",""))
				if comando[0]== "20":
					self.hkvs.put(comando[1],comando[2],comando[3].replace("\n",""))	
				if comando[0]== "30":
					self.hkvs.cas(comando[1],comando[2],comando[3],comando[4].replace("\n",""))	
				if comando[0]== "40":
					self.hkvs.delete(comando[1].replace("\n",""))

	def checkpoint(self):						#cria checkpoint
		self.f=open("dhkvs-log.txt","a")
		chkpt_hkvs = s.dumps(self.hkvs,-1)
		self.f_chkpt = open("dhkvs-ckpt-tmp.p","w")
		self.f_chkpt.write(chkpt_hkvs)
		self.f.truncate(0)
		self.f_chkpt.flush()
		os.fsync(self.f_chkpt.fileno())
		os.rename("dhkvs-ckpt-tmp.p", "dhkvs-ckpt.p")

