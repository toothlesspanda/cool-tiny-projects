#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <semaphore.h>
#include "SOpTattoo.h"
#include "mem_partilhada.h"

#define TEMPO_CORTE 1000

/** 
 * Funcao principal do processo cliente
 * Argumentos:
 * 1) ID do cliente
 * 2) numero de cadeiras
 * 3) numero total de clientes
 */

int main(int argc, char **argv)
{
	int cliente = atoi(argv[1]); /*ID do cliente*/
	int num_cadeiras = atoi(argv[2]); /*Numero de cadeiras*/
	int total_clientes = atoi (argv[3]); /*Numero total de clientes*/
	int *num_clientes; /*contador do numero de clientes, partilhado entre clientes*/
	sem_t *fim;
	struct estatisticas *stats;

	/**
	 * Ligacao aos varios recursos partilhados
	 * NB: o "false" indica que os processos se vao ligar a recursos ja criados
	 */
	cria_recursos_partilhados(num_cadeiras, total_clientes, false);
	stats = (struct estatisticas *) inicia_recurso("/stat",sizeof(struct estatisticas), false);
	num_clientes = (int *) inicia_recurso("/num_clientes",sizeof(int), false);
	fim = sem_open("/fim", 0);
	if (fim == NULL)
	{
		perror("sem_open");
		exit(1);
	}

	/* Cliente chega a SOp Tattoo e tenta fazer tatuagem */
    printf("Cliente %d a entrar na SOp Tattoo! Ja %d clientes.\n", cliente, *num_clientes);
	int teste = fazer_tatuagem(cliente);

    /* Se estiver cheio, sair da Sop Tatto e nao voltar mais.*/
    if (teste == CHEIO)
    {
        printf("SOp Tattoo está cheio. O cliente %d vai embora.\n", cliente);
		stats->cheio++;
		(*num_clientes)++;
		if (*num_clientes == total_clientes) 
		{ /*Era o ultimo cliente, avisar funcao main() principal*/
			sem_post(fim);
		}
		return 0;
    }
    
    /**
	 * Se a tatuadora estava ocupada, o cliente tera' esperado na sala de espera.
     * Esse cliente vai agora ser tatuado.
	 */
    else if(teste == ESPEREI)
	{
        printf("A tatuadora estava ocupada, o cliente %d esperou e agora vai ser tatuado.\n", cliente);
		stats->esperei++;
	}
    
    /* Caso contrario, nao havia ninguem na SOp Tattoo, a tatuadora deixou o descanso e fez tatuagem */
    else if (teste == DESCANSAR)
	{
        printf("A tatuadora esta' a descansar. O cliente %d pede para lhe ser feita tatuagem.\n", cliente);
		stats->descansar++;
	}
    
    /* Se o estado e' PRONTA entao o cliente nao teve de esperar e vai ja ser tatuado. */
    else
	{
        printf("O cliente %d esta a ser tatuado.\n", cliente);
		stats->pronta++;
	}
    
    /* O tempo que demora a fazer a tatuagem e' simulado agora. */
 	srand(time(NULL));
	usleep(rand()%TEMPO_CORTE);
    
    /* Tatuagem feita.  Sair da loja. */
    printf("Cliente %d foi tatuado e vai sair.\n", cliente);
    sair_SOpTattoo(cliente);
	(*num_clientes)++;
	if (*num_clientes == total_clientes) 
	{/*Era o ultimo cliente, avisar funcao main() principal*/
		sem_post(fim);
	}
	return 0;
}
