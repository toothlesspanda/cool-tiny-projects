/**
 * Aplicacao para testar as operacoes sobre uma arvore binaria de inteiros.
 *
 * author: respicio
 * date: March, 2011
 */

#include "arvore_binaria.h"
#include <stdio.h>
#include <stdlib.h> /* torna explicita a declaracao da funcao exit */  

/* escreve menu */
void escreverMenu( void );

int main( void )
{
  ArvBin minhaArv = arvore_criar();
  int opcao = 0;
  int valor;

  if ( arvore_erro() != ARVBIN_OK ) {
    fprintf (stderr, "Erro ao criar a arvore!\n");
  }
  else
  do {
    escreverMenu();
    scanf( "%d%*c", &opcao );
    switch( opcao ) {
    case 1: 
      printf( "Introduza inteiro na raiz, anda tudo para a dir\n" );
      scanf( "%d", &valor );
      minhaArv = arvore_agregar( valor, arvore_criar(), minhaArv  );
      if ( arvore_erro() == ARVBIN_OK ) {
        printf( "arvore apos insercao:" );
        arvore_printf( minhaArv );
        printf( "\n" );
      }
      else {
        fprintf( stderr, "Erro ao entrar elemento!\n" );
        arvore_destruir( minhaArv );
      }
      break;
    case 2: 
      if ( !arvore_esta_vazia( minhaArv ) )
        printf( "Raiz da arvore: %d\n", arvore_valor_raiz( minhaArv ) );
      else
        printf( "arvore esta vazia!\n" );
      break;
    case 3: 
      if ( !arvore_esta_vazia( minhaArv ) ) {
        printf( "arvore tamanho: %d" , arvore_tamanho( minhaArv ) );
        printf( "\n" );
      }
      else
        printf( "arvore esta vazia!\n" );
      break;
    case 4: 
      if ( !arvore_esta_vazia( minhaArv ) )
        printf( "Altura da arvore: %d\n", arvore_altura( minhaArv ) );
      else
        printf( "arvore esta vazia!\n" );
      break;
      break;
    case 5: 
      if ( !arvore_esta_vazia( minhaArv ) )
        printf( "Arvore esta vazia? Nao\n" );
      else
        printf( "Arvore esta vazia? Sim\n" );
      break;
    case 6:
      if ( !arvore_esta_vazia( minhaArv ) ) {
        printf(" Introduza valor\n");
        scanf( "%d", &valor );
        printf( "Contem %d? %s\n", valor, 
          arvore_contem( minhaArv, valor) ? "sim" : "nao" );
      }
      else
        printf( "Arvore esta vazia\n" );
      break;
    case 7:
      printf( "arvore actual:" );
      arvore_printf( minhaArv );
      printf( "\n" );
      break;
    case 0:
      printf( "Saida!" );
    default:
      printf( "Operacao nao implementada!\n" );
      break;
    }
  } while ( opcao != 0 && arvore_erro() == ARVBIN_OK );

  if ( arvore_erro() == ARVBIN_OK ) arvore_destruir( minhaArv );

  exit( arvore_erro() );
}

/* escreve menu */
void escreverMenu( void ) {
  printf ("1 - Inserir raiz na arvore\n"
	  "2 - Raiz da arvore\n"
          "3 - Tamanho da arvore\n"
	  "4 - Altura da arvore\n"
	  "5 - Arvore esta vazia?\n"
          "6 - Arvore contem valor?\n"
          "7 - Imprimir arvore\n"
	  "0 - Sair\n"
	  "Opcao: ");
}

