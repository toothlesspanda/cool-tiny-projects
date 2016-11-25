/**
 * Implementacao dinamica das pilhas de inteiros. 
 * Esta implementacao faz a gestao de memoria por si.
 *
 * author: Francisco Martins; respicio
 * date: April, 2007; March 2011
 * version: $Id: pilhas_dinamicas.c 12 2007-07-30 09:47:11Z fmartins $
 */

#include "pilha.h"
#include <stdlib.h>
#include <stdio.h>

/* tipos de dados */

/* no */

typedef struct no *NoPtr;

typedef struct no {
  int elem;
  NoPtr prox;
} No;

/* pilha */
typedef struct pilha {
  int n_elem;
  NoPtr inicio;
} Pilha_dinamica;

/* erro */
static int erro = PILHA_OK;

/* operacoes */

/* cria uma nova pilha (sem elementos) */
Pilha pilha_nova( void )
{
  Pilha p = (Pilha) malloc( sizeof( Pilha_dinamica ) );
  if (p != NULL) {
    p->n_elem = 0;
    p->inicio = NULL;
    return p;
  }

  erro = PILHA_MEM_ERROR;
  return NULL;
}

/* destruir uma pilha p */
void pilha_destruir( Pilha p )
{
  NoPtr no = p->inicio;
  NoPtr elim;

  while ( no != NULL ) {
    elim = no;
    no = no->prox;
    free( elim );
  }

  free( p );
}

/* acrescenta um elemento a pilha */
void pilha_sobrepor( Pilha p, int elemento )
{
  NoPtr novo = (NoPtr) malloc( sizeof ( No ) );

  if ( novo != NULL ) {
    novo->elem = elemento;
    novo->prox = p->inicio;
    p->inicio = novo;
    (p->n_elem)++;
  }
  else 
    erro = PILHA_MEM_ERROR;
}

/**
 * retira um elemento da pilha
 *  pre: !pilha_esta_vazia(p)
 */
void pilha_retirar( Pilha p )
{
  NoPtr elim = p->inicio;
  p->inicio = (p->inicio)->prox;
  p->n_elem--;
  free( elim );
}

/**
 * elemento que esta no topo da pilha
 * pre: !pilha_esta_vazia(p)
 */
int pilha_topo( Pilha p )
{
  return (p->inicio)->elem;
}

/* determina se a pilha esta vazia */
int pilha_esta_vazia( Pilha p )
{ 
  return p->inicio == NULL;
}

/* imprimir pilha no formato [a, b, c] */
void pilha_printf( Pilha p )
{  
  NoPtr percorre = p->inicio; 
  /* o 1o esta na pos f->inicio */

  printf( "dinamica[");
  if ( !pilha_esta_vazia( p ) ){
    printf( "%d", percorre->elem );
    percorre = percorre->prox;
  }
    
  while ( percorre != NULL ) {
    printf( ", %d", percorre->elem );
    percorre = percorre->prox;
  }

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
  erro = 0;
}
