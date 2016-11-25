/**
 * Interface para a memoria do tipo fila de inteiros. 
 * Uma memoria do tipo fila permite a disciplina de acesso FIFO: 
 * o primeiro a entrar e o primeiro a sair.
 *
 * author: Francisco Martins; alteracoes por csl; respicio  
 * date: May, 2007; April, 2008; March, 2011
 * version: $Id: filas.h 12 2007-07-30 09:47:11Z fmartins $
 */


#ifndef _FILA
#define _FILA

/* tipos de dados */

/* fila */
typedef struct fila *Fila;


/* operacoes */

/**
 * cria uma nova fila (sem elementos) 
 * @return uma fila vazia
 */
Fila fila_criar( void );

/**
 * destroi uma fila 
 * @param f a fila a destruir
 */
void fila_destruir( Fila f );

/**
 * faz entrar o elemento valor na fila f 
 * @param f a fila a usar
 * @param valor o elemento a entrar
 */
void fila_entrar( Fila f, int elemento );

/**
 * retira um elemento duma fila
 * @param f a fila a usar
 * @requires !fila_esta_vazia(f)
 */
void fila_sair( Fila f );

/**
 * elemento que esta no inicio da fila
 * @param f a fila a usar 
 * @requires !fila_esta_vazia(f)
 */
int fila_primeiro( Fila f );

/**
 * determina se uma fila esta vazia
 * @param f a fila a avaliar
 */
int fila_esta_vazia( Fila f );

/**
 * imprime os elementos duma fila no formato [a, b, c] 
 * @param f a fila a imprimir
 */
void fila_imprimir( Fila f );

/* devolve o erro associado as operacoes sobre filas */
int fila_erro( void );

/* reinicia o erro associado as operacoes sobre filas */
void fila_reset_erro( void );


/* constantes */

/* operacao correu sem erros */
#define FILA_OK 0

/* erro ao reservar memoria;
 * este codigo de erro corresponde, na verdade, a muitas situacoes 
 * diferentes, dependendo da implementacao particular, e ainda de
 * causas diferentes, dentro de uma mesma implementacao.
 */
#define FILA_MEM_ERROR 1

#endif
