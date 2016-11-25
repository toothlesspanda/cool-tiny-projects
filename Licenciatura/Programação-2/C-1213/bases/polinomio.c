/**
 * Implementacao do interface polinomio.h utilizando 
 * um vector reservado dinamicamente.
 *
 * Author: Francisco Martins; alteracoes por csl, jpn, respicio
 * date: April, 2007; April, 2008; Marco 2009, March 2011
 */

#include "polinomio.h"
#include <stdlib.h>
#include <math.h>

/* tipos de dados */

/* polinomio */
struct pol {
  double *coefs;
  int grau;
};

/* erro */
static int erro = POL_OK;

/* operacoes */

/* criar polinomio nulo com o grau indicado */
/* pre: g >= 0 */
Polinomio polCriarNulo (int g)
{
  Polinomio p = (Polinomio) malloc (sizeof (struct pol));
  if (p != NULL) {
    p->grau = g;
    p->coefs = (double *) calloc (g + 1, sizeof( double )); /* tudo zeros */
    if ( p->coefs != NULL )
      return p;
    
    /* elimina o polinomio que ja havia reservado */
    free (p);
  }

  erro = POL_MEM_ERROR;
  return NULL;
}

/* alterar o valor do coeficiente em pos num polinomio p */
/* pre: pos <= polGrau(p) */
void polEscreverCoef (Polinomio p, int pos, double valor) 
{
  p->coefs[pos] = valor;
}

/* aceder ao coeficiente na posicao pos de um polinomio p */
/* pre: pos <= polGrau(p) */
double polLerCoef (Polinomio p, int pos) 
{
  return p->coefs[pos];
}

/* apagar o polinomio p */
Polinomio polApagar (Polinomio p) 
{
  /* liberta os coeficientes */
  free (p->coefs);

  /* liberta o polinomio */
  free (p);

  return NULL;
}

/* criar uma copia de um polinomio p */
Polinomio polCopiar (Polinomio p) 
{
  int g = polGrau (p);
  int i;

  Polinomio p1 = polCriarNulo (g);
  if (p1 != NULL) {
    for (i = 0; i <= g; i++)
      polEscreverCoef (p1, i, polLerCoef (p, i));

    return p1;
  }

  erro = POL_MEM_ERROR;
  return NULL;
}

/* grau de um polinomio p */
int polGrau (Polinomio p) 
{
  return p->grau;
}

/* verificar se um polinomio p eh nulo */
int polEhNulo (Polinomio p)
{
  int i = polGrau (p);

  for (; i >= 0; i--)
    if (polLerCoef (p, i) != 0)
      return 0;

  return 1;
}

/* soma de dois polinomios p1 e p2 */
Polinomio polSoma (Polinomio p1, Polinomio p2)
{
  int g1 = polGrau (p1);
  int g2 = polGrau (p2);
  int g = g1 < g2 ? g1 : g2;
  Polinomio p;
  int i;

  if (g1 > g2) 
    p = polCopiar (p1);
  else 
    p = polCopiar (p2);
  
  if (p == NULL)
    return NULL;

  for (i = 0; i <= g; i++)
    p->coefs[i] = p1->coefs[i] + p2->coefs[i];

  return p;
}

/* polinomio simetrico de um polinomio p */
Polinomio polSimetrico (Polinomio p)
{
  Polinomio pResult;
  int i;

  pResult = polCopiar (p);
  
  if (pResult != NULL) 
    for ( i = 0; i <= polGrau (p); i++ )
      pResult->coefs[i] = -( pResult->coefs[i]);
    
  return pResult;
}


/* polinomio que resulta de subtrair polinomios p1 e p2 */
Polinomio polSubtraccao (Polinomio p1, Polinomio p2)
{
  int g1 = polGrau (p1);
  int g2 = polGrau (p2);
  int g = g1 < g2 ? g1 : g2;
  Polinomio p;
  int i;

  if (g1 > g2) 
    p = polCopiar (p1);
  else 
    p = polCopiar (p2);
  
  if ( p == NULL )
    return NULL;

  for ( i = 0; i <= g; i++ )
    p->coefs[i] = p1->coefs[i] - p2->coefs[i];

  /* caso em que g2 > g1 */
  for ( i = g + 1; i <= g2; i++ )
    p->coefs[i] = - p->coefs[i];    

  return p;
}

/* polinomio produto de dois polinomios p1 e p2 */
Polinomio polProduto (Polinomio p1, Polinomio p2)
{
  int g1 = polGrau (p1);
  int g2 = polGrau (p2);
  int i, j;
  Polinomio p = polCriarNulo (g1 + g2);

  if ( p == NULL )
    return NULL;

  for ( i = 0; i <= g1; i++ )
    for ( j = 0; j <= g2; j++ )
      p->coefs[i + j] += p1->coefs[i] * p2->coefs[j];

  return p;
}

/* verificar se dois polinomios p1 e p2 sao iguais */
int polSaoIguais( Polinomio p1, Polinomio p2 )
{
  int i;
  int g = polGrau(p1);
  if ( g != polGrau(p2) )
    return 0;

  for ( i = 0; i <= g; i++ )
    if ( !saoIguais( p1->coefs[i], p2->coefs[i] ) )
      return 0;

  return 1;
}

/* avaliar polinomio p num ponto valor */
double polAvaliar (Polinomio p, double valor) 
{
  double resultado = 0;
  int i;
  int g = polGrau (p);

  for ( i = 0; i <= g; i++ )
    resultado += p->coefs[i] * pow(valor, i);

  return resultado;
}

/* operacoes auxiliares para controlo do erro*/

/* devolve o erro associado as operacoes sobre polinomios */
int polErro (void)
{
  return erro;
}

/* reinicia o erro associado as operacoes sobre polinomios */
void polResetErro (void)
{
  erro = POL_OK;
}

/* operacoes sobre double */
/* avalia se d1 e d2 sao iguais 
 * garante a comparacao de reais com precisao de 10^(-8)
 */
int saoIguais( double d1, double d2 ) {
  return ( d1 - d2 <= 0.00000001 && d1 - d2 > - 0.00000001 );
}