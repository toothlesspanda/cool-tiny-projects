/**
 * Uma implementacao estatica da fila de inteiros, 
 * utilizando um array circular. 
 * Esta implementacao so permite armazenar ate 99 elementos.
 *
 * author: Francisco Martins; alteracoes por csl; respicio; respicio
 * date: May, 2007; April, 2008; March, 2011; March, 2013
 */

#include "fila.h"
#include <stdlib.h>
#include <stdio.h>

/* tipos de dados */

/* fila */
#define MAX_FILA 100  /* escolher MAX_FILA >= 2 */
        /* experimentar o program pondo aqui um valor pequeno *
         * por exemplo, 4
         */

typedef struct fila {
  int inicio;
  int fim;
  int elem[ MAX_FILA ];  /* tamanho maximo da fila = MAX_FILA - 1 */
} Fila_estatica;

/* erro */
static int erro = FILA_OK;


/* operacoes */

/**
 * cria uma nova fila (sem elementos) 
 * @return uma fila vazia
 */
Fila fila_criar( void )
{
  Fila f = ( Fila ) malloc( sizeof( Fila_estatica ) );
  
  if ( f != NULL ) {
    f->inicio = MAX_FILA - 1;  /* "simula"  f -> inicio = -1; */
    f->fim = 0;
    return f;
  }

  erro = FILA_MEM_ERROR;
  return NULL;
}

/**
 * destroi uma fila 
 * @param f a fila a destruir
 */
void fila_destruir( Fila f )
{
  free( f );
}

/**
 * faz entrar o elemento valor na fila f 
 * @param f a fila a usar
 * @param valor o elemento a entrar
 */
void fila_entrar( Fila f, int valor )
{
  if ( f->fim != f->inicio ) {  /* a capacidade ainda nao esta esgotada? */
    f->elem[ f->fim ] = valor;
    f->fim = ( f->fim + 1 ) % MAX_FILA;
  } 
  else 
    erro = FILA_MEM_ERROR;
}

/**
 * retira um elemento duma fila
 * @param f a fila a usar
 * @requires !fila_esta_vazia(f)
 */
void fila_sair( Fila f )
{
  f->inicio = ( f->inicio + 1 ) % MAX_FILA;
}

/**
 * elemento que esta no inicio da fila
 * @param f a fila a usar 
 * @requires !fila_esta_vazia(f)
 */
int fila_primeiro( Fila f )
{
  return f->elem[ ( f->inicio + 1 ) % MAX_FILA ];
}

/**
 * determina se uma fila esta vazia
 * @param f a fila a avaliar
 */
int fila_esta_vazia( Fila f )
{
  return ( f->inicio + 1 ) % MAX_FILA == f->fim % MAX_FILA ;
}

/**
 * imprime os elementos duma fila no formato [a, b, c] 
 * @param f a fila a imprimir
 */
void fila_imprimir( Fila f )
{
  int percorre = ( f->inicio + 1 ) % MAX_FILA; 
  /* o 1o esta na posicao (f->inicio+1)%MAX_FILA */

  printf( "[" );
  if ( !fila_esta_vazia( f ) )
  while ( percorre % MAX_FILA != f->fim ) {
    printf( " %d", f->elem[ percorre ] );
    percorre++;
    if ( percorre % MAX_FILA != f-> fim ) printf( "," );
  }

  printf( "]\n" );
}

/* devolve o erro associado as operacoes sobre fila */
int fila_erro( void )
{
  return erro;
}

/* reinicia o erro associado as operacoes sobre fila */
void fila_reset_erro( void )
{
  erro = 0;
}
