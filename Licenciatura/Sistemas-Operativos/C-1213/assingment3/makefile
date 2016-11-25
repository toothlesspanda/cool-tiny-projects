OBJ1 = main.o SOpTattoo.o mem_partilhada.o
OBJ2 = cliente.o SOpTattoo.o mem_partilhada.o

all: tattoo cliente

tattoo: $(OBJ1)
	gcc -Wall -g $(OBJ1) -o tattoo -lrt -lpthread

cliente: $(OBJ2)
	gcc -Wall -g $(OBJ2) -o cliente -lrt -lpthread

main.o: main.c
	gcc -c -g -Wall main.c

SOpTattoo.o: SOpTattoo.c 
	gcc -c -g -Wall SOpTattoo.c 

cliente.o: cliente.c
	gcc -c -g -Wall cliente.c

mem_partilhada.o: mem_partilhada.c
	gcc -c -g \-Wall mem_partilhada.c

clean:
	rm -f *.o *~ tattoo cliente
	rm -f /dev/shm/sem.fim /dev/shm/sinc /dev/shm/tatuadora /dev/shm/cadeira /dev/shm/stat /dev/shm/num_clientes /dev/shm/cliente /dev/shm/recurso2 /dev/shm/sinc2 /dev/shm/partilhada 
