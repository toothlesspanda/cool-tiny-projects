/**
* Utiliza o modulo _POLINOMIO para representar um polinomio
* de um dado grau e para calcular o valor deste polinomio
* em pontos a indicar pelo utilizador.
* author: porfs de ProgII, ultima alteracao: respicio, March.2011
*/

#include "polinomio.h"
#include <stdio.h>

Polinomio lerPolinomio( void );

int main( void )
{
  int i;
  Polinomio p1 = lerPolinomio();
  Polinomio p2 = lerPolinomio();
  double x;

  if ( polErro() == POL_OK )
  {
      printf( "polinomios%s sao iguais\n", 
      polSaoIguais( p1, p2 ) ? "" : " nao");
   
    for ( i = 0; i < 3; i++ ) {
      printf( "indique o ponto onde calcular o polinomio 1:" );
      scanf( "%lf", &x );
      printf( "o valor do polinomio 1 em %f e %f\n", x,
              polAvaliar( p1, x ));
    }
  }
  else
	  printf( "Memoria insuficiente. Fim da execucao." );

  p1 = polApagar( p1 );
  p2 = polApagar( p2 );

  return 0;
}

/* le polinomio inserido pelo utilizador */
Polinomio lerPolinomio()
{
  int g, i;
  double v;
  Polinomio p;
  
  printf ("Qual o grau do polinomio a criar? ");
  scanf ("%d", &g);

  p = polCriarNulo(g);
  if ( polErro() == POL_OK )
    for ( i = 0; i <= g; i++ ) {
      printf ("Introduza o coeficiente de grau %d: ", i);
      scanf ("%lf", &v);
      polEscreverCoef (p, i, v);
    }

  return p;
}
