#include <pthread.h>
#include <sys/mman.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdbool.h>
#include "mem_partilhada.h"

/**
 * Funcao generica para criar recurso (create==true) 
 * ou para se ligar a recurso (create==false)
 * Argumentos:
 * 1) string com nome do recurso, 
 * 2) tamanho (em bytes) do recurso,
 * 3) variavel booleana para definir se recurso é para criar
 * ou se e' apenas para o processo se ligar a recurso ja criado
 * Retorno: ponteiro para recurso partilhado
 */
void *inicia_recurso(char *nome, int tamanho, bool create)
{
	int ret, fd;
	void *partilha;
	
	if (create == true) 
	{
		fd = shm_open (nome, O_RDWR | O_CREAT | O_EXCL, S_IRUSR | S_IWUSR);
	}
	else
	{
		fd = shm_open (nome, O_RDWR, S_IRUSR | S_IWUSR);
	}
	if (fd == -1) {
		perror ("shm_open");
		exit (1);
	}

	if (create == true) 
	{
		ret = ftruncate (fd, tamanho);
		if (ret == -1) {
			perror ("ftruncate");
			exit (2);
		}
	}

	partilha = mmap(0, tamanho, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
	if (partilha == MAP_FAILED) {
		perror ("mmap");
		exit (3);
	}
	return partilha;
}

/**
 * Funcao generica para destruir recurso partilhado
 * Argumentos:
 * 1) string com nome do recurso, 
 * 2) tamanho (em bytes) do recurso,
 * 3) ponteiro para recurso partilhado.
 */
void destroi_recurso(char *nome, int tamanho, void *partilha)
{
	int ret;
	ret = munmap (partilha, tamanho);
	if (ret == -1) {
		perror ("munmap");
		exit (1);
	}
	ret = shm_unlink (nome);
	if (ret == -1) {
		perror ("shm_unlink");
		exit (2);
	}
}
