from __future__ import division
from mininet.topo import Topo
from mininet.net import Mininet
from mininet.link import TCLink
import sys
import math

'''
Prototcolos em Redes de Dados - PA1

Grupo 004:
Ines de Matos @ 43538
Joao Vicente  @ 44294
Ana Salvado   @ 44299

'''


'''
Atualizado a 8/03

Falta configurar os testes - talvez fazer num script a parte e fazer import
''' 

######################################################################################## ######################################################################################## 
##################################### SimpleTopo #######################################
######################################################################################## 
######################################################################################## 

'''
Parte 1
'''
class SimpleTopo(Topo):
	"Simple Data Center Topology"

	fanout = 2
	opts1 = {'bw':20,'delay':'5ms', 'loss':1, 'max_queue_size':1000,'use_htb':True}
	opts2 = {'bw':10,'delay':'5ms', 'loss':1, 'max_queue_size':1000,'use_htb':True}
	opts3 = {'bw':1,'delay':'5ms', 'loss':1, 'max_queue_size':1000,'use_htb':True}

	"linkopts - (1:core/aggreg, 2:aggregation/edge, 3: edge/host) parameters"
	"fanout - number of child switch per parent switch"
	def __init__(self,linkopts1=opts1,linkopts2=opts2,linkopts3=opts3,fanout=fanout,**opts): # Initialize topology and default options
		
		Topo.__init__(self,**opts)
		switch_core = self.addSwitch('s1_core')


		
		#counters to define IDs
		count_h = 1
		count_e = 1
		count_a = 1
	
		for aggregator in range(fanout):

			switch_aggre = self.addSwitch("s%s_aggreg" % (aggregator+count_a))
			self.addLink(switch_aggre,switch_core,**linkopts1)
			for edge in range(fanout):

				switch_edge = self.addSwitch("s%s_edge" % (edge+count_e))

				for h in range(fanout):

					host = self.addHost("h%s" % (h+count_h))
					self.addLink(host, switch_edge)

				count_h+=2
				self.addLink(switch_edge,switch_aggre)
			
			count_e+=2

######################################################################################## ######################################################################################## 
##################################### FatTopo ########################################## 
######################################################################################## 
######################################################################################## 
'''
Parte 2
'''
class FatTopo(Topo):
	"Fat Data Center Topology"

	fanout=4
	opts1 = {'bw':10,'delay':'2ms', 'loss':0, 'max_queue_size':1000}
	opts2 = {'bw':10,'delay':'2ms', 'loss':0, 'max_queue_size':1000}
	opts3 = {'bw':10,'delay':'2ms', 'loss':0, 'max_queue_size':1000}

	"linkopts - (1:core, 2:aggregation, 3: edge) parameters"
	"fanout - number of child switch per parent switch"
	def __init__(self,linkopts1=opts1,linkopts2=opts2,linkopts3=opts3,k=fanout,**opts): # Initialize topology and default options
	
		Topo.__init__(self,**opts)
				
		#counters to define IDs
		count_h = 1
		count_e = 1
		count_a = 1

		#counters to summary
		count_all_links=0

		#
	
		#arrays of each type of switch
		array_a = [None]*int(math.ceil(k/2))
		array_e = [None]*int(math.ceil(k/2))
		array_cores = [None]*int(math.pow(math.ceil(k/2),2))
		
		#create all cores
		for core in range(int((math.pow(math.ceil(k/2),2)))):

			print "Core " + str('s'+str(core+1)+'_core')+ " criado"			
			array_cores[core] = self.addSwitch('s'+str(core+1)+'_core')

		
		#for each k-POD create: 
		#1)			math.ceil(k/2) - edges and aggregs
		#2) 			math.pow(k/2,2))/2 - hosts per edge
		#3)			create and linkmath.pow(k/2,2))/2 hosts for each edge
		#4)			make link between edges and aggregs of this pod
		for pod in range(k):

			print "------------------------------------------"
			print "|               Start POD" + str(pod+1)+"              |"   
			print "------------------------------------------"
			
			for aggregator in range(int(math.ceil(k/2))): #1)

				print "     # Aggregator "+str(int(aggregator+count_a))

				array_a[aggregator] = self.addSwitch("s"+str(int(aggregator+count_a))+"_agg_p"+str(pod+1))			

			for edge in range(int(math.ceil(k/2))): #2)

				print " "
				print "     @ Edge "+str(int(edge+count_a))

				edge = self.addSwitch("s"+str(int(edge+count_a))+"_edg_p"+str(pod+1))

				print " "
				print "          POD "+str(pod+1)+" >> Start Linking Host ----> Edge..."
				print "adasd " +str(math.ceil((math.pow(math.ceil(k/2),2))/2)) 

				for h in range(int(math.ceil((math.pow(math.ceil(k/2),2))/2))): #3)

					print "                   Host "+str(h+count_h)
					host = self.addHost("h"+str(int(h+count_h))+"_p"+str(pod+1))

					print "                      "+ host +" ----> "+ edge	
					self.addLink(host, edge,**linkopts3)
					count_all_links+=1

				count_h = count_h + int(math.ceil((math.pow(math.ceil(k/2),2))/2))

				print " "
				print "          POD "+str(pod+1)+" >> Start Linking Edge ----> Aggregator..."
				
				for a in array_a: #4)
					print "                   "+edge +" ----> "+ a

					link = self.addLink(edge,a,**linkopts2)				
							
					count_all_links+=1
		
			count_e += math.ceil(k/2)	
			count_a += math.ceil(k/2)

			print " "
			print "          POD "+str(pod+1)+" >> Start Linking Aggregator ----> Cores..."
	

			#aux counter for the agg+core links
			sum_c =0

			for a in array_a: #for each AG ...
				for cc in range(int(math.ceil(k/2))): # ...and for every math.ceil(k/2) Cores create link between AG and Core
					
					print "                   "+str(a) +" ----> "+ str(array_cores[cc+sum_c])
					self.addLink(a, array_cores[cc+sum_c],**linkopts1)
					count_all_links+=1
				sum_c+= int(math.ceil(k/2))

		#summary
		print " "
		print "             ##########################"		
		print "             # JAI Data Center criado #"
		print "             ##########################"
		print " "
		print " "
		print "             ##########################"		
		print "             #         Summary        #"
		print "             ##########################"
		print " "
		print "                    @@ TOTAL"
		print "                    Switches: " + str(len(array_cores) + int(count_a-1) + int(count_e-1) )
		print "                         Cores: " + str(len(array_cores)) 
		print "                         Aggregators: " + str(int(count_a-1))
		print "                         Edges: " + str(int(count_e-1))
		print "                    Hosts: " + str(count_h-1)
		print "                    Links: " + str(count_all_links)



######################################################################################## ######################################################################################## 
##################################### Testes ########################################## 
######################################################################################## 
######################################################################################## 


def simpleTest():

	topo = FatTopo()
	net = Mininet(topo)
	net.start()
	dump
	net.pingAll()
	print "cenass"
	net.stop()

if __name__== '__main__':
	simpleTest()
		
topos = { 'oursimpletopo': ( lambda: SimpleTopo() ),'ourfattopo': ( lambda: FatTopo() ) }

