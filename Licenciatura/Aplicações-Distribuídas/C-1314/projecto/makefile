all: data entry list table message client server

data: data.o test_data.o
	gcc -Wall data.o test_data.c -o data

entry: entry.o data.o test_entry.o
	gcc -Wall entry.o data.o test_entry.c -o entry

list: data.o entry.o list.o
	gcc -Wall data.o entry.o list.o test_list.c -o lista

table: data.o entry.o list.o table.o
	gcc -Wall data.o entry.o list.o table.o test_table.c -o table

message: message.o data.o entry.o list.o table.o
	gcc -Wall message.o data.o entry.o list.o table.o test_message.c -o message

client: client_stub.o table_client.o network_client.o data.o entry.o list.o table.o message.o
	gcc -Wall client_stub.o table_client.o network_client.o data.o entry.o list.o table.o message.o -o client

server: table_skel.o table_server.o network_client.o data.o entry.o list.o table.o message.o
	gcc -Wall table_skel.o table_server.o network_client.o data.o entry.o list.o table.o message.o -o server

data.o: data.c data.h
	gcc -g3 -Wall -c data.c

entry.o: entry.c entry.h
	gcc -g3 -Wall -c entry.c

list.o: list.c list.h list-private.h
	gcc -g3 -Wall -c list.c

table.o: table.c table.h table-private.h
	gcc -g3 -Wall -c table.c

message.o: message.c message.h message-private.h
	gcc -g3 -Wall -c message.c

network_client.o: inet.h network_client.h message.h
	gcc -g3 -Wall -c network_client.c

client_stub.o: inet.h network_client.h message.h client_stub-private.h data.h message-private.h network_client-private.h 
	gcc -g3 -Wall -c client_stub.c
	
table_skel.o: inet.h table-private.h table_skel.h
	gcc -g3 -Wall -c table_skel.c

table_client.o: inet.h network_client-private.h message.h client_stub-private.h
	gcc -g3 -Wall -c table_client.c

table_server.o: inet.h network_client-private.h message.h table.h table_skel.h
	gcc -g3 -Wall -c table_server.c

clean:
	rm -rf *o *~ data entry lista table message client server
