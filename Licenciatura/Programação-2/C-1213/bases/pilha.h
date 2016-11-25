/**
 * Interface para a memoria do tipo pilha de inteiros. 
 * Uma memoria do tipo pilha oferece a política de acesso: 
 * o ultimo elemento a entrar e o primeiro elemento a sair (LIFO).
 *
 * author: Francisco Martins; alteracoes por csl; respicio
 * date: March, 2007; May, 2008; March, 2011
 * version: $Id: pilhas.h 12 2007-07-30 09:47:11Z fmartins $
 */


#ifndef _PILHA
#define _PILHA

/* tipos de dados */

/* pilha */
typedef struct pilha *Pilha;


/* operacoes */

/* cria uma nova pilha (sem elementos) */
Pilha pilha_nova( void );

/* destroi uma pilha */
void pilha_destruir( Pilha p );

/* acrescenta um elemento a pilha */
void pilha_sobrepor( Pilha p, int n );

/* retira um elemento da pilha
 *  pre: !pilha_esta_vazia(p)
 */
void pilha_retirar( Pilha p );

/* indica o elemento que esta no topo da pilha
 * pre: !pilha_esta_vazia(p)
 */
int pilha_topo( Pilha p );

/* determina se a pilha esta vazia */
int pilha_esta_vazia( Pilha p );

/* imprimir pilha no formato [a, b, c] */
void pilha_printf( Pilha p );

/* devolve o erro associado as operacoes sobre pilhas */
int pilha_erro( void );

/* reinicia o erro associado as operacoes sobre pilhas */
void pilha_reset_erro( void );

/* constantes */

/* operacao correu sem erros */
#define PILHA_OK 0

/* erro ao reservar memoria */
#define PILHA_MEM_ERROR 1

#endif
