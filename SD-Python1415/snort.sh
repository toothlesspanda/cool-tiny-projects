#!/bin/bash 
var IP 127.0.0.1
alert tcp !$IP any -> $IP 1:1024 (flags:S;msg:"Varrimento de porta";threshold:type both, track by_src, count 4, seconds 60;sid:1;)
alert tcp !$IP any -> $IP 9993:9995 (flags:S;msg:"ATAQUE AS PASSWORDS!!!";threshold:type threshold, track by_src, count 3, seconds 20;sid:2;)



