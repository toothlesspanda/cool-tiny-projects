#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>
#include <semaphore.h>
#include "SOpTattoo.h"
#include "mem_partilhada.h"

/** Funcao principal do SOp Tatoo 
 * Argumentos:
 * 1) Numero de clientes
 * 2) Numero de cadeiras
 */
int main(int argc, char **argv)
{
	char cliente_str[6]; /*Num clientes (maximo 5 digitos)*/
	char num_cad_str[6]; /*Num cadeiras (maximo 5 digitos)*/
	char num_clientes_str[6]; /*Num clientes (maximo 5 digitos)*/
	int cliente = 1, num_clientes, num_cad;
	pid_t pid;
	sem_t *fim;
	struct estatisticas *stats; /*estrutura que mantem estatisticas*/
	int soma;

	/* Ler argumentos da linha de comandos */
	num_clientes = atoi(argv[1]); /*1º argumento e' o numero de clientes*/
	num_cad = atoi(argv[2]); /*2º argumento e' o numero de cadeiras*/

    /* Inicializacoes */
    SOpTattoo_init(num_clientes, num_cad); /*criar a SOp Tattoo*/
	fim = sem_open("/fim", 0); /*semaforo que controla o fim do programa*/
	
	/* criar estrutura partilhada para estatisticas */
	stats = (struct estatisticas *) inicia_recurso("/stat",sizeof(struct estatisticas), true); 
        
    /* Simular a chegada de clientes 'a Sop Tattoo */
    while(cliente <= num_clientes)
    {
        pid = fork();
		if (pid==0)
		{
			sprintf(cliente_str,"%d", cliente);
			sprintf(num_cad_str,"%d", num_cad);
			sprintf(num_clientes_str,"%d", num_clientes);
			/*Lancar um novo processo "cliente da SOp Tattoo", com os varios argumentos*/
			execlp("./cliente","cliente", cliente_str, num_cad_str, num_clientes_str, NULL);
		}
		else
		{
			cliente++;
		}
    }

	sem_wait(fim); /*espera que todos clientes terminem*/

	/* Calculo e apresentacao das estatisticas finais */
	soma = stats->cheio + stats->esperei + stats->descansar + stats->pronta;
	printf("=====ESTATISTICAS=====\n* Tatoo SOp cheio = %d\n* Esperou na fila = %d\n* Tatuadora estava a descansar = %d\n* Tatuadora estava pronta = %d\n* Total = %d\n", 
		stats->cheio, stats->esperei, stats->descansar, stats->pronta, soma);
	
	/* Destruição de todos os recursos partilhados */
	destroi_recurso("/stat", sizeof(struct estatisticas), stats);
	destroi_recursos_SopTatoo();
	return 0;
}
