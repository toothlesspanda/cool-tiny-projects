/** 
 * Implementação do TDA fila_carros 
 * autores: fc43538 Inês Matos e fc43536 Tiago Moucho*/

#include "carro.h"
#include "fila_carros.h"
#include <stdlib.h>
#include <stdio.h>

/* tipos de dados */

/* carro */

typedef struct carroNo *CarroPtr;

typedef struct carroNo {
  Carro elem;
  CarroPtr prox;
} CarroNo; /*no com elementos do tipo carro*/

/* fila */
typedef struct filac {
  int n_elem;
  CarroPtr inicio;
  CarroPtr fim;
} Filac_espera;


/* erro */
static int erro = FILAC_OK;

/* operacoes */

/**
 * cria uma nova fila (sem elementos) 
 * @return uma fila vazia
 */
Filac filac_criar( void )
{
  Filac f = (Filac) malloc( sizeof( Filac_espera ) );
  if ( f != NULL ) {
    f->n_elem = 0;
    f->inicio = NULL;
    f->fim = NULL;
    return f;
  }

  erro = FILAC_MEM_ERROR;
  return NULL;
}

/**
 * destroi uma fila 
 * @param f a fila a destruir
 */
void filac_destruir( Filac f )
{
  CarroPtr carro = f->inicio;
  CarroPtr elim;

  while ( carro != NULL ) {
    elim = carro;
    carro = carro->prox;
    free( elim );
  }

  free( f );
}


/**
 * faz entrar o elemento valor na fila f 
 * @param f a fila a usar
 * @param valor o elemento a entrar
 */
void filac_entrar( Filac f, Carro elemento )
{
  CarroPtr novo = (CarroPtr) malloc( sizeof( CarroNo ) );

  if ( novo != NULL ) {
    novo->elem.minChegada= elemento.minChegada;
        novo->elem.ordemChegada= elemento.ordemChegada;
  novo->elem.tipoLavagem= elemento.tipoLavagem;
    novo->prox = NULL;
    if ( f->fim != NULL ) 
      f->fim->prox = novo;
    f->fim = novo;
    if ( f->inicio == NULL ) 
      f->inicio = novo;

    (f->n_elem)++;
  }
  else 
    erro = FILAC_MEM_ERROR;
}


/**
 * retira um elemento duma fila
 * @param f a fila a usar
 * @requires !fila_esta_vazia(f)
 */
void filac_sair( Filac f )
{
  CarroPtr elim = f->inicio;
  f->inicio = (f->inicio)->prox;
  if ( f->inicio == NULL )
    f->fim = NULL;
  f->n_elem--;
  free( elim );
}

/**
 * elemento que esta no inicio da fila
 * @param f a fila a usar 
 * @requires !fila_esta_vazia(f)
 */
Carro filac_primeiro( Filac f )
{
 return f->inicio->elem;
 
}

/**
 * determina se uma fila esta vazia
 * @param f a fila a avaliar
 */
int filac_esta_vazia( Filac f )
{
  return f->inicio == NULL;
}

/*
 *retira todos os elementos da fila
 * sem a destruir
 * @requires !fila_esta_vazia(f)
 */
void filac_apagar( Filac f ){


   while( !filac_esta_vazia( f ) ){
      filac_sair(f);
   }

}
/*
 *numero de elementos na fila 
 */
int filac_n_elems( Filac f ){
 return f->n_elem;
}


/**
 * imprime os elementos duma fila no formato [a, b, c] 
 * @param f a fila a imprimir
 */
void filac_imprimir( Filac f )
{  
 CarroPtr percorre = f->inicio; 

  /* o 1o esta na posf->inicio */


  printf( " %d carro(s) [",f->n_elem);
  if ( !filac_esta_vazia( f ) ){
    printf( "%dº %dmin", percorre->elem.ordemChegada, percorre->elem.minChegada );
    percorre = percorre->prox;
  }
    
  while ( percorre != NULL ) { 
    printf( ", %dº %dmin", percorre->elem.ordemChegada,percorre->elem.minChegada );
    percorre = percorre->prox;
   
   
  }

  printf( "]\n" );
}


/* retira todos os elementos cujo tempo de espera(tempoEsp)
 * eh superior ao tempo actual(tempAct);
 * @requires !fila_esta_vazia(f)
 */

void filac_desistir( Filac f, int tempEsp, int tempAct){
      CarroPtr reboque = f->inicio;
     int tempo,flag=0;

     while( reboque != NULL && !flag) {
         tempo=tempAct - (f->inicio)->elem.minChegada;

         if( tempo >= tempEsp){           
               filac_sair(f);
         }
         else  flag=1;   
   }
      
}


/* devolve o erro associado as operacoes sobre filas */
int filac_erro( void )
{
  return erro;
}

/* reinicia o erro associado as operacoes sobre filas */
void filac_reset_erro( void )
{
  erro = 0;
}
