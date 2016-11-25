#include <stdio.h>
#include <stdlib.h>
#include "arvore_binaria.h"

/* Nesta implementacao uma arvore vazia corresponde a NULL */

typedef struct noArvBin {       /* armazena dados de um no da arvore */
  int elem;
  ArvBin esquerda;
  ArvBin direita;
} NoArvBin;

static int erro = ARVBIN_OK;

/*********************************************/

/* construtor: criar uma arvore vazia */
ArvBin arvore_criar (void)
{
  return NULL;
}

/* construtor: criar uma arvore a partir de um inteiro e de
 * outras duas arvores.
 */
ArvBin arvore_agregar( int elemento, ArvBin esquerda,
                      ArvBin direita )
{
  ArvBin a = (ArvBin)malloc( sizeof(struct noArvBin) );

  if ( a != NULL )
  {                
    /* preencher atributos */
    a->elem = elemento;
    a->esquerda = esquerda;
    a->direita = direita;
  }
  else /* erro na alocacao de memoria */
    erro = ARVBIN_MEM_ERROR;

  return a;     
}

/* destrutor: libertar toda a memoria da arvore (recursivamente)
 * De notar que a arvore final nao fica vazia, pois esta funcao
 * nao consegue colocar o apontador do cliente a NULL tendo
 * de devolver NULL para ser aplicado a esse apontador. Isto e'
 * um defeito desta estrutura de dados.
 */
ArvBin arvore_destruir( ArvBin a )
{
  if ( !arvore_esta_vazia( arvore_esquerda( a ) ) )
    a->esquerda = arvore_destruir( arvore_esquerda( a ) );

  if ( !arvore_esta_vazia( arvore_direita( a ) ) )
    a->direita = arvore_destruir( arvore_direita( a ) );

  free( a );
  return NULL;
}

/* verifica se arvore esta' vazia */
int arvore_esta_vazia( ArvBin a )
{
  return a == NULL;
}

/* devolve o elemento na raiz */
/* pre: !arvore_esta_vazia(a) */
int arvore_valor_raiz( ArvBin a )
{
  return a->elem;
}

/* devolve o filho esquerdo na raiz */
/* pre: !arvore_esta_vazia(a) */
ArvBin arvore_esquerda( ArvBin a )
{
  return a->esquerda;
}

/* devolve o filho direito na raiz */
/* pre: !arvore_esta_vazia(a) */
ArvBin arvore_direita( ArvBin a )
{
  return a->direita;
}

/* devolve o número de elementos da arvore */
int arvore_tamanho( ArvBin a )
{
  if ( arvore_esta_vazia( a ) )
    return 0;

  return 1 + arvore_tamanho( arvore_esquerda( a ) ) +
             arvore_tamanho( arvore_direita( a ) );
}

/* devolve a altura da arvore (arvore vazia tem altura zero) */
int arvore_altura( ArvBin a )
{
  int altura_esq;
  int altura_dir;

  if ( arvore_esta_vazia( a ) )
    return 0;
  else {
    altura_esq = arvore_altura( arvore_esquerda( a ) );
    altura_dir = arvore_altura( arvore_direita( a ) );

    return 1 + (altura_esq > altura_dir ? altura_esq : altura_dir);
  }
}

/* verifica se elemento pertence 'a arvore */
int arvore_contem( ArvBin a, int elemento )
{
  if ( arvore_esta_vazia( a ) )
    return 0;

  if ( arvore_valor_raiz( a ) == elemento )
    return 1;

  return arvore_contem( arvore_esquerda( a ), elemento ) ||
         arvore_contem( arvore_direita( a ),  elemento );
}

/***************************************************/
/* funcao auxiliar para impressao da arvore (recursiva) */
static void imprimir( ArvBin a, int level )
{
  int i;
  if ( !arvore_esta_vazia( a ) )
  {
    for( i = 0; i < level; i++ )
      printf("     ");
    printf( "%i\n", arvore_valor_raiz( a ) );

    imprimir( arvore_esquerda( a ), level + 1 );
    imprimir( arvore_direita( a ), level + 1 );
  }
}

/* descritor: imprime o conteudo da arvore */
void arvore_printf( ArvBin a )
{
  printf( "{\n" );
  imprimir( a, 0 );
  printf( "}\n" );
}

/***************************************************/
/* devolve o erro associado 'as operacoes */
int arvore_erro( void )
{
  return erro;
}

/* reinicia o erro associado 'as operacoes */
void arvore_reset_erro( void )
{
  erro = ARVBIN_OK;
}

