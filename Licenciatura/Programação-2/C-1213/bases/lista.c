/**
 * Implementacao do TDA lista de inteiros com
 * lista simplesmente ligada com nos compactos.
 *
 * author: Francisco Martins; alteracoes e acrescentos por csl
 *         conversao para TDA por jpn
 *			      alteracoes bc & respicio
 * date: March, 2007; March, 2008; March, 2009; March 2011
 */

#include <stdio.h>    /* uso: printf */
#include <stdlib.h>   /* uso: malloc, free */
#include "lista.h"

/*************** tipos privados ********************/

typedef struct no *NoPtr;  /* apontador para no' */

struct no {           /* armazena dados de um no */
  int elem;
  NoPtr prox;
};

struct lista {      /* armazena dados de uma lista */
  NoPtr cabeca;
};

/**************** operacoes ******************/

/* Variaveis e funcoes auxiliares (privadas) */

static int erro = LISTA_OK;

/* Criar um no' */
static NoPtr criar_no( int elemento, NoPtr proximo )
{
  NoPtr novo_no = (NoPtr) malloc( sizeof(struct no) );

  if ( novo_no == NULL )   /* erro na alocacao de memoria */
	   return NULL;

  novo_no->elem = elemento;
  novo_no->prox = proximo;
  
  return novo_no;
}

/* Remover um no' */
static void remover_no( NoPtr no )
{
  free( no );
}

/* Copiar um no' (recursivamente) */
static NoPtr copiar_no( NoPtr original )
{
  NoPtr copia = NULL;

  if ( original != NULL ) {
    copia = criar_no( original->elem, NULL );

    if ( copia != NULL )   /* ok na alocacao de memoria */
      copia->prox = copiar_no( original->prox );
  }
  
  return copia;
}


/*********** funcoes publicas ****************/

/* criar uma lista vazia */
Lista lista_criar( void )
{
  /* reservar memoria para armazenar uma estrutura lista */
  Lista l = ( struct lista* ) malloc( sizeof(struct lista) );

  if ( l == NULL ) {  /* erro na alocacao de memoria */
    erro = LISTA_MEM_ERROR;
    return NULL;
  }
  
  /* inicializar atributos: */
  l->cabeca = NULL;

  return l;
}

/* destrutor: libertar toda a memoria da lista */
void lista_destruir( Lista l )
{
  lista_apagar( l );
  free( l );   /* a variável l fica indefinida */
}

/* apagar todos os elementos da lista */
void lista_apagar( Lista l )
{
  while ( !lista_esta_vazia( l ) )
    lista_remover_cabeca( l );
}

/* verifica se lista esta' vazia */
int lista_esta_vazia( Lista l )
{
  return l->cabeca == NULL;
}

/* devolve o elemento 'a cabeca */
/* pre: !lista_vazia(l) */
int lista_cabeca( Lista l )
{
  return l->cabeca->elem;
}

/* devolve o  ultimo elemento da lista */
/* pre: !lista_esta_vazia(l) */
int lista_ultimo( Lista l )
{
  NoPtr aux = l->cabeca;

  while ( aux->prox != NULL )
	  aux = aux->prox;
  return aux->elem;
}

/* inserir elemento 'a cabeca */
void lista_inserir_cabeca( Lista l, int elem )
{
  NoPtr aux = criar_no( elem, l->cabeca );

  if ( aux == NULL ) { 
    /* erro na alocacao de memoria */
	   erro = LISTA_MEM_ERROR;
	   return;
  }

  l->cabeca = aux;
}

/* inserir elemento no fim da lista */
void lista_inserir_ultimo( Lista l, int elemento )
{
  NoPtr aux;
  NoPtr novo_no = criar_no( elemento, NULL );

  if (novo_no == NULL) { 
    /* erro na alocacao de memoria */
	   erro = LISTA_MEM_ERROR;
	   return;
  }

  if ( lista_esta_vazia( l ) )
  	 l->cabeca = novo_no;
  else {

    aux = l->cabeca;
    while ( aux->prox != NULL )
      aux = aux->prox;

    aux->prox = novo_no;
  }
}

/* remover elemento da cabeca */
/* pre: !lista_esta_vazia(l) */
void lista_remover_cabeca( Lista l )
{
  NoPtr no = l->cabeca;
  l->cabeca = l->cabeca->prox;
  remover_no( no );
}

/* remover o ultimo elemento da lista */
/* pre: !lista_esta_vazia(l) */
void lista_remover_ultimo( Lista l )
{
  NoPtr aux;

  if ( l->cabeca->prox == NULL )    /* se tem apenas 1 elem */
 	  lista_remover_cabeca( l );    /* ...o ultimo e' a cabeca */

  else {

    aux = l->cabeca;
    while ( aux->prox->prox != NULL )  /* ir ate' ao penultimo no */
      aux = aux->prox;
    
    remover_no( aux->prox );         /* remover o ultimo no da lista */
    aux->prox = NULL;
  }
}

/* devolve o num.de elementos da lista */
int lista_comprimento( Lista l ) 
{
  int result = 0;
  NoPtr aux = l->cabeca;

  while ( aux != NULL ) {
    aux = aux->prox;
    result++;
  }

  return result;
}

/* Verifica se duas listas sao iguais */
int lista_igual( Lista l1, Lista l2 )
{
  NoPtr aux1, aux2;

  if ( lista_esta_vazia( l1 ) && lista_esta_vazia( l2 ) )
  	 return 1;    /* duas listas vazias sao iguais */

  aux1 = l1->cabeca;
  aux2 = l2->cabeca;

  while ( aux1 != NULL && aux2 != NULL ) {

    if ( aux1->elem != aux2->elem )
      return 0;   /* listas possuem elems diferentes */

    aux1 = aux1->prox;
    aux2 = aux2->prox;
  }

	 /* se ambas teem igual dimensao, os dois apts estarao no fim */
  return aux1 == NULL && aux2 == NULL;
}

/* Verifica se o elemento existe na lista */
int lista_contem( Lista l, int elemento )
{
  int achou = 0;
  NoPtr aux = l->cabeca;
  
  while ( !achou && aux != NULL )
    if ( aux->elem == elemento )
      achou = 1;
    else
      aux = aux->prox;

  return achou;
}

/* Devolve o i-esimo elemento da lista (comeca na posicao 0) */
/* pre: 0 <= index < lista_comprimento(l) */
int lista_indice( Lista l, int indice )
{
   int i;
   NoPtr aux = l->cabeca;

   for ( i = 0; i < indice; i++ )
     aux = aux->prox;

   return aux->elem;
}

/* Devolve uma copia da lista dada */
Lista lista_copiar( Lista l )
{
  Lista copia = lista_criar();

  if ( lista_esta_vazia( l ) )
    return copia;

  copia->cabeca = copiar_no( l->cabeca );
  
  if ( copia->cabeca == NULL ) {  
   /* erro na alocacao de memoria */
    erro = LISTA_MEM_ERROR;
    return NULL;
 }

  return copia;
}

/* imprimir lista no formato [a,b,c] */
void lista_printf( Lista l )
{
  NoPtr aux;

  printf( "[" );
  
  if ( !lista_esta_vazia( l ) ) {
    aux = l->cabeca;

    printf( "%d", aux->elem ); /* no primeiro elem nao se prefixa virgula */
    aux = aux->prox;

    while ( aux != NULL ) {
      printf( ",%d", aux->elem );
      aux = aux->prox;
    }
  }

  printf( "]" );
}


/***************************************************/
/* devolve o erro associado 'as operacoes */
int lista_erro

( void )
{
  return erro;
}

/* reinicia o erro associado 'as operacoes */
void lista_reset_erro( void )
{
  erro = LISTA_OK;
}


