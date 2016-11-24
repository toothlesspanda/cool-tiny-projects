#ifndef _MAQUINA
#define _MAQUINA
#include "carro.h"



typedef struct maquina *Maquina;
 
/*cria um máquina nova com :uma fila vazia, a informação dos valores de lucro por cada tipo de 
lavagem, a informação relativa ao cenário de chegadas (limite inferior e limite superior do intervalo que define 
o número de minutos entre chegadas sucessivas), o tempo total da simulação, a informação relativamente ao 
traço da simulação estar ou não activo, o minuto da primeira chegada que deve estar inicializado (próxima 
chegada) e todos os indicadores a zero. */

Maquina maquina_criar(int lucro0,int lucro1,int lucro2,int limInfChegadas,int
	  limSupChegadas,int tempoSimulacao,int traco);

/*destrói a máquina. */
void maquina_destruir(Maquina maq);

/*processa uma chegada de acordo com as regras.*/ 
void maquina_chegar(Maquina maq);


/* processa o início duma lavagem. */
void maquina_iniciar_lavagem(Maquina maq);

/*processa o fim de uma lavagem. */
void maquina_sair(Maquina maq); 

 /*processa as desistências dos carros que estão na fila da máquina.*/ 
void maquina_desistir(Maquina maq);


/*processa a simulação recorrendo às outras operações do TDA.*/
void maquina_processar(Maquina maq);


/*escreve o conteúdo da máquina apresentando o conteúdo da fila, quantos carros entraram 
no sistema, quantos carros desistiram, o tempo médio de espera por carro atendido, o tempo médio no 
sistema por carro atendido (inclui o tempo de espera e o tempo de atendimento) e o lucro diário da máquina 
(lucro total das lavagens efectuadas ‐ custo fixo de operação), conforme se exemplifica no ficheiro de output.*/  
void maquina_printf(Maquina maq); 

/*o valor do erro do máquina. */
int maquina_erro( void );

 /*reinicia o erroo máquina. */
void maquina_reset_erro( void );

/* operacao correu sem erros */
#define MAQUINA_OK 0

/* erro ao reservar memoria;
 * este codigo de erro corresponde, na verdade, a muitas situacoes 
 * diferentes, dependendo da implementacao particular, e ainda de
 * causas diferentes, dentro de uma mesma implementacao.
 */
#define MAQUINA_MEM_ERROR 1

#endif
