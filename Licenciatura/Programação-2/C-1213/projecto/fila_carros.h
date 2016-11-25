 
/** Interface para a memoria do tipo fila de inteiros. 
 * Uma memoria do tipo fila permite a disciplina de acesso FIFO: 
 * o primeiro a entrar e o primeiro a sair.
 *
 * author: Francisco Martins; alteracoes por csl; respicio  
 * date: May, 2007; April, 2008; March, 2011
 * version: $Id: filas.h 12 2007-07-30 09:47:11Z fmartins $
 *
 * re-edition:
 * autores: fc43538 Inês Matos e fc43536 Tiago Moucho
 */


#ifndef _FILA_CARROS
#define _FILA_CARROS
#include "carro.h"

/* tipos de dados */

/* fila */
typedef struct filac *Filac;


/* operacoes */

/**
 * cria uma nova fila (sem elementos) 
 * @return uma fila vazia
 */
Filac filac_criar( void );

/**
 * destroi uma fila 
 * @param f a fila a destruir
 */
void filac_destruir( Filac f );

/**
 * faz entrar o elemento valor na fila f 
 * @param f a fila a usar
 * @param valor o elemento a entrar
 */
void filac_entrar( Filac f, Carro elemento );

/**
 * retira um elemento duma fila
 * @param f a fila a usar
 * @requires !fila_esta_vazia(f)
 */
void filac_sair( Filac f );

/*
 *retira todos os elementos da fila
 * sem a destruir
 * @requires !fila_esta_vazia(f)
 */
void filac_apagar(Filac f);

/**
 * elemento que esta no inicio da fila
 * @param f a fila a usar 
 * @requires !fila_esta_vazia(f)
 */
Carro filac_primeiro( Filac f );

/**
 * determina se uma fila esta vazia
 * @param f a fila a avaliar
 */
int filac_esta_vazia( Filac f );

/*
 *numero de elementos na fila 
 */
int filac_n_elems( Filac f );

/**
 * imprime os elementos duma fila no formato [a, b, c] 
 * @param f a fila a imprimir
 */
void filac_imprimir( Filac f );

/* devolve o erro associado as operacoes sobre filas */
int filac_erro( void );

/* reinicia o erro associado as operacoes sobre filas */
void filac_reset_erro( void );

/* retira todos os elementos cujo tempo de espera(tempoEsp)
 * eh superior ao tempo actual(tempAct);
 * @requires !fila_esta_vazia(f)
 */
void filac_desistir( Filac f, int tempEsp, int tempAct);

/* constantes */

/* operacao correu sem erros */
#define FILAC_OK 0

/* erro ao reservar memoria;
 * este codigo de erro corresponde, na verdade, a muitas situacoes 
 * diferentes, dependendo da implementacao particular, e ainda de
 * causas diferentes, dentro de uma mesma implementacao.
 */
#define FILAC_MEM_ERROR 1

#endif
