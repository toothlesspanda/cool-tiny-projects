var app = require('express')();
var http = require('http');
var io = require('socket.io-client');
var fs = require('fs');
console.log("Inicio");
socket = io.connect("http://192.168.43.244:3333");
socket.on("connect", function () {
	console.log("Connect...");
	setInterval(function () {
		fs.readFile("valoresbpm.txt","utf-8", function (err, data){
			if(err){
				return console.log(err);
			} else {
				
				var pontos = data.split("\n")[0];
				var hb = data.split("\n")[1];
				var obj = {"pontos":"", "heartBeat":""};
				obj["pontos"] = pontos.split(":")[1];
				obj["heartBeat"] = hb.split(":")[1];
				
				console.log("valuePontos: " + obj["pontos"]);
				console.log(" valueHB: " + obj["heartBeat"]);
				
				socket.emit("heartBeat", obj);
			}

		 });
               
    }, 2000);
	
});