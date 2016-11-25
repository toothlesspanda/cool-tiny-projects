/******************************************

Exemplo de utilização do modulo lista.h

Compilar:
  gcc -Wall -ansi -c lista.c
  gcc -Wall -ansi usarLista.c lista.o -o usarLista

*/

/* ultima alteracao respicio@21.03.2011 */

#include <stdio.h>
#include "lista.h" /*incluir o modulo das listas*/

int main( void ){

  int opt = 0, aux = 0;
  Lista l = lista_criar();
  Lista copia = lista_criar();

  while ( opt != -1 ){
    printf( "O que pretende fazer?\n" );
    printf( "[1] Insira na cabeca.\n" );
    printf( "[2] Insira no fim.\n" );
    printf( "[3] Remover cabeca.\n" );
    printf( "[4] Remover ultimo.\n" );
    printf( "[5] Comprimento da lista.\n" );
    printf( "[6] Guardar copia.\n" );
    printf( "[-1] Sair\n" );
    printf( "Insira opcao: " );
    scanf( "%d", &opt );
     
    switch ( opt ){
      case 1:
        printf( "Insira numero: " );
        scanf( "%d", &aux );
        lista_inserir_cabeca( l, aux );
        break;
      case 2:
        printf( "Insira numero: " );
        scanf( "%d", &aux);
        lista_inserir_ultimo( l, aux );
        break;
      case 3:
        if ( !lista_esta_vazia( l ) )
          lista_remover_cabeca( l );
        break;
      case 4:
        if ( !lista_esta_vazia( l ) )
          lista_remover_ultimo( l );
        break;
      case 5:
        printf( "Comprimento da lista: %d\n", lista_comprimento( l ) );
        break;
      case 6:
        copia = lista_copiar( l );
        printf( "Foi guardada uma copia\n" );
        break;
      default: 
        printf( "nao eh opcao\n" );
        break;
    }
    lista_printf( l );
    printf( "\n" );

    printf( "copia no momento em que foi feita\n" );
    lista_printf( copia );
    printf( "\n" );
  }
    
  
  if ( !lista_esta_vazia( l ) )
    printf( "Cabeca da lista = %d\n", lista_cabeca( l ) );
    
  if ( !lista_esta_vazia( l ) )
    printf( "Ultimo da lista = %d\n", lista_ultimo( l ) );
  
  lista_destruir( l );
  lista_destruir( copia );

  printf( "qualquer tecla para terminar" );
  scanf( "%d", &aux );
  
  return 0;

}