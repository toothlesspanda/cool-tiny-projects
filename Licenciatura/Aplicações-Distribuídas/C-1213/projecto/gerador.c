/**
 * Modulo gerador de numeros pseudo-aleatorios.
 *
 * author: respicio
 * date: April, 2013
 */

#include "gerador.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h> /* para poder usar funcoes sobre o system time */
#define PERCT_0 50
#define PERCT_1 30
#define PERCT_2 20

/**
 * Inicializacao do gerador de numeros pseudo-aleatorios de forma a que
 * a sequencia de chamadas as funcoes geraInteiro e geraLavagem 
 * seja dependente do system-time. Se esta funcao nao for previamente 
 * invocada, a sequencia de valores obtidos eh sempre a mesma. O algoritmo da 
 * funcao rand() usa uma semente para gerar uma sequencia de numeros, 
 * por isso eh necessario usar o srand para fazer variar essa semente. 
 * A constante RAND_MAX esta definida na standard library (stdlib).
 */
void inicia( void ) {
  srand( (unsigned) time (NULL));
}

/**
 * Retorna um numero inteiro pseudo-aleatorio no intervalo [min, max]
 * @param min o limite inferior do intervalo
 * @param max o limite superior do intervalo
if(!filac_esta_vazia(maq->f))
      primOrd = filac_primeiro(maq->f).ordemChegada;
 */
int geraInteiro( int min, int max) {
  double v;
  int na = rand();
  v = (double) na / (double) RAND_MAX; /* Numero pseudo-aleatorio em [0,1] */
  na = (int) ( v * ( max - min + 1 ) + min );  
  /* Numero pseudoaleatorio inteiro em [min,max] */
  return na;
}

/**
 * um tipo de lavagem gerado de acordo com as percentagens definidas no
 * projecto Lava-lava
 */
int geraTipoLavagem( void ) {
  int na = geraInteiro( 0, 100 );
  if ( na <= PERCT_0 ) 
	return 0;
  else 
	  if ( na <= PERCT_0 + PERCT_1 )
		return 1;
      else 
		return 2;
}
