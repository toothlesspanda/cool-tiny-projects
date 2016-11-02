#!/bin/bash
IP="sudo /sbin/iptables"

#limpa regras
$IP -F
$IP -X 

#nemo ping
$IP -A INPUT -s nemo -p icmp -j ACCEPT
$IP -A OUTPUT -d nemo -p icmp -j ACCEPT

#dns ligacao udp e tcp
$IP -A INPUT -p udp --dport 53 -m state --state NEW,ESTABLISHED,RELATED -j ACCEPT
$IP -A OUTPUT -p udp --sport 53 -m state --state ESTABLISHED,RELATED -j ACCEPT
$IP -A INPUT -p tcp --dport 53 -m state --state NEW,ESTABLISHED,RELATED -j ACCEPT
$IP -A OUTPUT -p tcp --sport 53 -m state --state ESTABLISHED,RELATED -j ACCEPT

#clientes todos no porto do servidor
$IP -A INPUT -p tcp --dport 9995 -j ACCEPT 
$IP -A INPUT -p tcp --dport 9994 -j ACCEPT 
$IP -A INPUT -p tcp --dport 9993 -j ACCEPT 	

#ssh rede local
$IP -A INPUT -p tcp -s 10.101.148/22 --dport 22 -m state --state NEW,ESTABLISHED,RELATED -j ACCEPT
$IP -A OUTPUT -p tcp -d 10.101.148/22 --dport 22 -m state --state ESTABLISHED,RELATED -j ACCEPT

#regras do ienuciado
$IP -A INPUT -i lo -j ACCEPT
$IP -A OUTPUT -o lo -j ACCEPT

$IP -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
$IP -A OUTPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

#ips especificos input
$IP -A INPUT -s 10.101.253.11 -j ACCEPT 
$IP -A INPUT -s 10.101.253.12 -j ACCEPT 
$IP -A INPUT -s 10.101.253.13 -j ACCEPT 
$IP -A INPUT -s 10.101.249.63 -j ACCEPT 
$IP -A INPUT -s 10.101.85.6 -j ACCEPT 
$IP -A INPUT -s 10.101.85.138 -j ACCEPT 
$IP -A INPUT -s 10.101.85.18 -j ACCEPT 
$IP -A INPUT -s 10.101.148.1 -j ACCEPT 
$IP -A INPUT -s 10.101.85.134 -j ACCEPT

#ips especificos output 
$IP -A OUTPUT -d 10.101.253.11 -j ACCEPT 
$IP -A OUTPUT -d 10.101.253.12 -j ACCEPT 
$IP -A OUTPUT -d 10.101.253.13 -j ACCEPT 
$IP -A OUTPUT -d 10.101.249.63 -j ACCEPT 
$IP -A OUTPUT -d 10.101.85.6 -j ACCEPT 
$IP -A OUTPUT -d 10.101.85.138 -j ACCEPT 
$IP -A OUTPUT -d 10.101.85.18 -j ACCEPT 
$IP -A OUTPUT -d 10.101.148.1 -j ACCEPT 
$IP -A OUTPUT -d 10.101.85.134 -j ACCEPT 

#drop todos
$IP -A INPUT  -j REJECT
$IP -A OUTPUT -j REJECT

#lista
$IP -L
