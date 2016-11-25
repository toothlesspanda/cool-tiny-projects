/**
 * Aplicacao para testar as operacoes sobre uma fila de inteiros.
 *
 * author: respicio
 * date: March, 2011
 */

#include "fila.h"
#include <stdio.h>
#include <stdlib.h> /* torna explicita a declaracao da funcao exit */  

/* escreve menu */
void escreverMenu( void );

int main( void )
{
  Fila f = fila_criar();
  int opcao = 0;
  int valor;

  if ( fila_erro() != FILA_OK ) {
    fprintf (stderr, "Erro ao criar a fila!\n");
  }
  else
  do {
    escreverMenu();
    scanf( "%d%*c", &opcao );
    switch( opcao ) {
    /* opcao 1 - Entrar na fila */
    case 1: 
      printf( "Introduza inteiro\n" );
      scanf( "%d", &valor );
      fila_entrar( f, valor );
      if ( fila_erro() == FILA_OK ) {
        printf( "Fila apos insercao:" );
        fila_imprimir( f );
        printf( "\n" );
      }
      else {
        fprintf( stderr, "Erro ao entrar elemento!\n" );
        fila_destruir( f );
      }
      break;
    /* opcao 2 - Primeiro da fila */
    case 2: 
      if ( !fila_esta_vazia( f ) )
        printf( "Primeiro da fila: %d\n", fila_primeiro( f ) );
      else
        printf( "Fila esta vazia!\n" );
      break;
    /* opcao 3 - Sair da fila */
    case 3: 
      if ( !fila_esta_vazia( f ) ) {
        fila_sair( f );
        printf( "Fila apos saida:" );
        fila_imprimir( f );
        printf( "\n" );
      }
      else
        printf( "Fila esta vazia!\n" );
      break;
    /* opcao 4 - Fila esta vazia? */
    case 4: 
      printf( "Fila esta vazia? %s", fila_esta_vazia( f )? "sim " : "nao " );
      fila_imprimir( f );
      printf( "\n" );
      break;
    /* opcao 5 - Imprimir fila */
    case 5:
      printf( "Fila actual:" );
      fila_imprimir( f );
      printf( "\n" );
      break;
    /* opcao 0 - Sair */
    case 0: 
      printf( "Saida!" );
      break;
    /* outras opcoes nao implementadas */
    default:
      printf( "Operacao nao implementada!\n" );
      break;
    }
  } while ( opcao != 0 && fila_erro() == FILA_OK );

  if ( fila_erro() == FILA_OK ) fila_destruir( f );

  exit( fila_erro() );
}

/* escreve menu */
void escreverMenu( void ) {
  printf ("1 - Entrar na fila\n"
	  "2 - Primeiro da fila\n"
          "3 - Sair da fila\n"
	  "4 - Fila esta vazia?\n"
	  "5 - Imprimir fila\n"
	  "0 - Sair\n"
	  "Opcao: ");
}

