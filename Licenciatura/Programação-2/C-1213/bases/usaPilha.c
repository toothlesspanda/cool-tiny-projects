/**
 * Aplicacao para testar as operacoes sobre uma pilha de inteiros.
 *
 * author: respicio
 * date: March, 2011
 */

#include "pilha.h"
#include <stdio.h>
#include <stdlib.h> /* torna explicita a declaracao da funcao exit */  

/* escreve menu */
void escreverMenu( void );

int main( void )
{
  Pilha p = pilha_nova();
  int opcao = 0;
  int valor;

  if ( pilha_erro() != PILHA_OK ) {
    fprintf (stderr, "Erro ao criar a pilha!\n");
  }
  else
  do {
    escreverMenu();
    scanf( "%d%*c", &opcao );
    switch( opcao ) {
    case 1: 
      printf( "Introduza inteiro\n" );
      scanf( "%d", &valor );
      pilha_sobrepor( p, valor );
      if ( pilha_erro() == PILHA_OK ) {
        printf( "Pilha apos sobreposicao:" );
        pilha_printf( p );
        printf( "\n" );
      }
      else {
        fprintf( stderr, "Erro ao sobrepor elemento!\n" );
        pilha_destruir( p );
      }
      break;
    case 2: 
      if ( !pilha_esta_vazia( p ) )
        printf( "Topo da pilha: %d\n", pilha_topo( p ) );
      else
        printf( "pilha esta vazia!\n" );
      break;
    case 3: 
      if ( !pilha_esta_vazia( p ) ) {
        pilha_retirar( p );
        printf( "pilha apos saida:" );
        pilha_printf( p );
        printf( "\n" );
      }
      else
        printf( "pilha esta vazia!\n" );
      break;
    case 4: 
      printf( "pilha esta vazia? %s", pilha_esta_vazia( p )? "sim " : "nao " );
      pilha_printf( p );
      printf( "\n" );
      break;
    case 5:
      printf( "pilha actual:" );
      pilha_printf( p );
      printf( "\n" );
      break;
    case 0:
      printf( "Saida!" );
      break;
    default:
      printf( "Operacao nao implementada!\n" );
      break;
    }
  } while ( opcao != 0 && pilha_erro() == PILHA_OK );

  if ( pilha_erro() == PILHA_OK ) 
    pilha_destruir( p );

  exit( pilha_erro() );
}

/* escreve menu */
void escreverMenu( void ) {
  printf ("1 - Sobrepor na pilha\n"
	  "2 - Topo da pilha\n"
          "3 - Remover da pilha\n"
	  "4 - Pilha esta vazia?\n"
	  "5 - Imprimir pilha\n"
	  "0 - Sair\n"
	  "Opcao: ");
}

