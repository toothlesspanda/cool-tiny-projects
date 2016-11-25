/**
 * Aplicacao que permite testar o TDA Maquina
 * @author respicio
 * @Date April 2013
 */

#include <stdlib.h>
#include <stdio.h>
#include "maquina.h"
#include "gerador.h"
#include "fila_carros.h"

int main( int argc, char *argv[] ) {

  Maquina maq;
  FILE *dados;
  int lucro0, lucro1, lucro2, limInfChegadas, limSupChegadas, 
	  tempoSimulacao, traco;
  
  if ( argc != 2 )
  {
     printf( "Deve executar o programa indicando o nome do ficheiro \n"
             "de dados a considerar \n"
             "Exemplo: runMaquina dados.txt\n" );
     exit( 1 );
  }
 
  dados = fopen( argv[ 1 ], "r" );
  if (dados == NULL )
  {
     fprintf( stderr, "O ficheiro %s não existe \n", argv[1] );
     exit( 1 );
  }

  fscanf( dados, "%d%d%d", &lucro0, &lucro1, &lucro2 );
  fscanf( dados, "%d%d%d", &limInfChegadas, &limSupChegadas, &tempoSimulacao );
  fscanf( dados, "%d", &traco );

  /* cria maquina vazia com os parametros que definem 
   * os lucros, o cenário de chegadas e o tempo da simulacao */
  maq = maquina_criar( lucro0, lucro1, lucro2, limInfChegadas,
	  limSupChegadas, tempoSimulacao, traco );

   geraInteiro(limInfChegadas,limSupChegadas); /*gera inteiro entre os dois limites*/
   geraTipoLavagem();

  if ( maquina_erro() != MAQUINA_OK )
  {
      fprintf( stderr, "Erro ao criar maquina \n" );
      exit( maquina_erro() );
  }
  
  /* inicia o gerador - RETIRAR A INVOCACAO DE COMENTARIO APOS TESTES */
  /*inicia();*/ 

  /* processa a simulacao */
  maquina_processar( maq );
 
	
  /* escreve conteudo da maquina */
  maquina_printf( maq );

  /* liberta a memoria afecta a maquina */
  maquina_destruir( maq );

  /* fechar o ficheiro dados */
  fclose( dados );
  
  return 0;
}



