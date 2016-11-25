/**
 * Implementacao estatica da pilha de inteiros, utilizando um array.
 * Esta implementacao so permite armazenar 100 elementos.
 *
 * author: Francisco Martins; alteracoes por csl; respicio,respicio,
 * date: March, 2007; April, 2008; March, 2011, April, 2013
 * version: $Id: pilhas_estaticas.c 12 2007-07-30 09:47:11Z fmartins $
 */

#include "pilha.h"
#include <stdlib.h>
#include <stdio.h>

/* tipos de dados */

/* pilha */
#define MAX_PILHA 100

typedef struct pilha {
  int n_elem;
  int elem[ MAX_PILHA ];
} Pilha_estatica;

/* erro */
static int erro = PILHA_OK;


/* operacoes */

/* cria uma nova pilha (sem elementos) */
Pilha pilha_nova( void )
{
  Pilha p = (Pilha) malloc( sizeof( Pilha_estatica ) );
  if (p != NULL) {
    p -> n_elem = 0;
    return p;
  }

  erro = PILHA_MEM_ERROR;
  return NULL;
}

/* destroi uma pilha */
void pilha_destruir( Pilha p )
{
  free( p );
}

/* acrescenta um elemento a pilha */
void pilha_sobrepor( Pilha p, int n )
{
  if ( p->n_elem != MAX_PILHA ) {
    p->elem[ p->n_elem ] = n;
    p->n_elem++;
  } 
  else 
    erro = PILHA_MEM_ERROR;
}

/* retira o elemento que esta no topo da pilha
 * pre: !pilha_esta_vazia(p)
 */
void pilha_retirar( Pilha p )
{
  p->n_elem--;
}

/* indica o elemento que esta no topo da pilha
 * pre: !pilha_esta_vazia(p)
 */
int pilha_topo( Pilha p )
{
  return p->elem[ p ->n_elem - 1 ];
}

/* determina se a pilha esta vazia */
int pilha_esta_vazia( Pilha p )
{
  return p->n_elem == 0;
}

/* imprimir pilha no formato [a, b, c] */
void pilha_printf( Pilha p )
{
  int i;
  
  printf( "[" );
  for ( i = p->n_elem - 1; i > 0; i-- )
    printf( "%d, ", p->elem[ i ] );
  if ( p->n_elem > 0) printf( " %d", p->elem[ 0 ] ); /* base */ 
  printf( "]" );
}

/* devolve o erro associado as operacoes sobre pilhas */
int pilha_erro( void )
{
  return erro;
}

/* reinicia o erro associado as operacoes sobre pilhas */
void pilha_reset_erro( void )
{
  erro = PILHA_OK;
}
