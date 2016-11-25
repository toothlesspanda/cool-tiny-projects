#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 

int somaDigits( int n ); 
int somaDigitsIter( int n ); 
int somaRec( int n ); 
int somaIter( int n ); 
int getMaxIter( int a[], int size ); 
int getMaxRec( int a[], int size ); 
int getMaxRec( int a[], int size ); 
int contaZerosRec( int a[], int size ); 
int contaZeros( int a[], int size ); 
int potenciaRec( int n, int m ); 
int potenciaRecFast( int n, int m ); 
int coefBinomioRec( int n, int k ); 
int coefBinomioIter( int n, int k ); 
int coefBinomioMem( int n, int k ); 
int coefBinomioMem( int n, int k );
int numCatalanIter( int n ); 
int numCatalanRec( int n ); 
int numCatalanRecMem( int n ); 
int mdcRec( int m, int n ); 
int mdcIter( int m, int n ); 
void permutacoes( char s[] ); 
void hanoi( int source, int target, int discs ); 

/* funcoes auxiliares */
static void perm( char s[], int i, int n ); 
static void troca( char *s, char *t ); 
static int coefBinAux( int** C, int n, int k ); 
static int numCatalanAux( int *c, int n ); 
static int fact( int n ); 
int getMaxRecAux( int a[], int size, int max ); 
void escreveVector( int v[], int size );


int main( void ) {

  /* testa as varias funcoes desenvolvidas para resolver os problemas
   * do capitulo sobre recursao
   */
	 int m, n, i;
  int v[6] = {1, 2, 3, 4, 5, 6}; 
  int v2[6] = {0, 2, 0, 4, 0, 0}; 
	 char s[ 80 ]; 
  int cat; 

  
  /* soma dos primeiros n inteiros */
  printf( "soma dos primeiros 6 inteiros = %d \n", somaIter( 6 ) ); 
  printf( "soma dos primeiros 4 inteiros = %d\n", somaIter( 4 ) ); 


  /* soma dos digitos de um numero inteiro */
 	printf( "\nintroduza um numero inteiro \n" ); 
 	scanf( "%d", &n ); 
	 printf( "soma dos digitos de %d = %d (vs recursiva)\n", n, somaDigits( n ) );
	 printf( "soma dos digitos de %d = %d (vs iterativa)\n", 
           n, somaDigitsIter( n ) ); 

  /* maximo de um vector */
  escreveVector( v, 6 );
  printf( "\nmax em 6 = %d \n", getMaxRec( v, 6 ) ); 
  printf( "max em 4 = %d", getMaxRec( v, 4 ) ); 
 
  /* numero de zeros num vector */
  escreveVector( v2, 6 );
  printf( "numzeros iterativo = %d \nnum zeros recursivo = %d\n", 
           contaZeros( v2, 6 ), contaZerosRec( v2, 6 ) ); 
	
  /* coeficiente binomial */
 	printf( "\n\ncoeficiente binomial de( %d, %d )\nrecursivo = %d \n", 5, 3, 
           coefBinomioRec( 5, 3 ) ); 
	 printf( "coeficiente binomial iterativo = %d \n", coefBinomioIter( 5, 3 ) );
	 printf( "coeficiente binomial recursivo com memorização = %d\n\n", 
           coefBinomioMem( 5, 3 ) ); 

  /* permutacao dos caracteres de uma string */
	 printf( "caracteres a permutar = " ); 
	 scanf( "%s", &s );
	 permutacoes( s );

  /* maximo divisor comum pelo algoritmo de Euclides */
	 printf( "\n\nintroduza dois inteiros\n" ); 
  scanf( "%d %d", &m, &n ); 
	 printf( "mdcRec entre %d e %d = %d\n", m, n, mdcRec( m, n ) ); 
	 printf( "mdcIter entre %d e %d = %d\n", m, n, mdcIter( m, n ) ); 
	
  /* numeros de Catalan */
	 printf( "\n\nintroduza um inteiro para calcular o numero de Catalan"
          " dessa ordem\n" ); 
	 scanf( "%d", &n ); 

	 printf( "numero de Catalan rec %d = %d\n", n, numCatalanRec( n ) ); 
  printf( "numero de Catalan recursivo com memorizacao %d = %d\n", n, 
          numCatalanRecMem( n ) ); 
	 printf( "numero de Catalan iterativo %d = %d\n\n", n, numCatalanIter( n ) );

  /* torres de Hanoi */
	 printf( "\n\nTorres de Hanoi\nintroduza quantos discos quer"
           " trocar da torre 0 para a torre 2\n" ); 
	 scanf( "%d", &n ); 
  hanoi( 0, 2, n );

  printf( "\n\nintroduza um inteiro qualquer para terminar\n");
  scanf( "%d", &i );
 	return 0; 
} 


/***
 * Soma dos digitos de um número n dado: versao recursiva
 */
int somaDigits( int n ) 
{
 	if( n == 0 ) 
	 	return 0; 

	 return n % 10 + somaDigits( n / 10 ); 
}

/***
 * Soma dos digitos de um número n dado: versao iterativa
 */
int somaDigitsIter( int n ) {

  int soma = 0, num = n; 

  while( num > 0 ) {
	   soma += num % 10; 
	   num /= 10; 
  }

  return soma; 
}


/***
 * Soma dos n primeiros inteiros: versao recursiva
 * pre: n > = 0
 */
int somaRec( int n ) 
{
  if( n == 0 ) 
    return 0; 

  return n + somaRec( n - 1 ); 
}


/***
 * Soma dos n primeiros inteiros: versao iterativa
 * pre: n > = 0
 */
int somaIter( int n ) 
{
  int soma, cont; 

  for( soma = 0, cont = n; cont > 0; cont-- ) 
	   soma += cont; 

  return soma; 
}


/***
 * Maximo elemento de um vector de inteiros a com dimensao size
 * versao iterativa
 * pre: size > 0
 */
int getMaxIter( int a[], int size ) 
{
  int i, max = a[ 0 ]; 

  for( i = 1; i < size; i++ ) 
	   if( a[ i ] > max ) {
	     max = a[ i ]; 
	   }

 return max; 
}


/***
 * Maximo elemento de um vector de inteiros <a> com dimensao size
 * versao recursiva
 * pre: size > 0
 */
int getMaxRec( int a[], int size ) {
	 
 return getMaxRecAux( a, size, a[ size - 1 ] ); 
}


/***
 * Maximo elemento de um vector de inteiros <a> ate ah dimensao actual size
 * max eh o valor maximo encontrado ate ah dimensao actual size
 * funcao auxiliar a getMaxRec 
 * pre: size > 0
 */
int getMaxRecAux( int a[], int size, int max ) 
{
	 if( size == 0 ) 
		  return max; 
	 else 
		  if( a[ size - 1 ] > max ) 
			    return getMaxRecAux( a, size - 1, a[ size - 1 ] ); 
		  else 
			    return getMaxRecAux( a, size - 1, max ); 
}

/***
 * Solucao alternativa que recorre à soma de posicoes de apontadores 
 */
int getMaxRecApt( int a[], int size ) 
{
  int others; 

  if( size == 1 ) 
	   return a[ 0 ]; 

  others = getMaxRecApt( a + 1, size - 1 ); /* uma soma de apts! */

  return a[ 0 ] > others ? a[ 0 ] : others; 
}


/***
 * Numero de zeros de um vector de inteiros v com dimensao size
 * versao recursiva
 * pre: size > 0
 */
int contaZerosRec( int v[], int size ) 
{
	 if( size == 1 ) 
		  return v[ 0 ] == 0 ? 1 : 0; 
	 else 
		  return v[ size - 1 ] == 0 ? 1 + contaZerosRec( v, size - 1 ) : 
                              contaZerosRec( v, size - 1 );
}


/***
 * Numero de zeros de um vector de inteiros v com dimensao size
 * versao iterativa
 * pre: size > 0
 */
int contaZeros( int v[], int size ) 
{
 	int i, conta = 0; 

 	for( i = 0; i < size; i++ ) 
	 {
		  if( v[i] == 0 ) 
			    conta++; 
	 }
	
  return conta; 
}


/***
 * Calculo de n^m dados dois inteiros positivos n e m.
 * pre: n,m > 0
 * Versao basica
 */
int potenciaRec( int n, int m ) 
{
  if( m == 0 ) 
	   return 1; 

  return n * potenciaRec( n, m - 1 ); 
}


/***
 * Calculo de n^m dados dois inteiros positivos n e m.
 * pre: n,m > 0
 * Versao com optimizacao do calculo
 */
int potenciaRecFast( int n, int m ) 
{
  int aux; 

  if( m == 0 ) 
	   return 1; 

  aux = potenciaRecFast( n, m / 2 ); 
  
  return aux * aux *( m % 2 == 0 ? 1 : n ); 
}


/***
 * Calculo dos coeficientes binomiais dados pela relacao de recursao
 * 
 * C(n, 0) = C(n, n) = 1, para n >= 0
 * C(n, k) = C(n-1, k-1)+C(n-1, k), para 0 < k < n
 *
 * pre: n>=0, k>=0, k<=n
 *
 * versao recursiva
 */
int coefBinomioRec( int n, int k ) 
{
  if( k == 0 || n == k )
    return 1; 

  return coefBinomioRec( n - 1, k - 1 ) + coefBinomioRec( n - 1, k );
}

/***
 * Calculo dos coeficientes binomiais dados pela formula
 * 
 * C(n, k) = n!/(k!(n-k)!)
 *
 * pre: n>=0, k>=0, k<=n
 *
 * versao formula, recorre ao calculo do factorial
 */
int coefBinomioIter( int n, int k ) 
{
  return fact( n ) / ( fact( k ) * fact( n - k ) ); 
}

static int fact( int n )
{
  int i, produto = 1; 

  for( i = n; i > 1; i-- ) {
    produto *= i;
  }

  return produto; 
}


/***
 * Calculo dos coeficientes binomiais dados pela relacao de recursao
 * 
 * C(n, 0) = C(n, n) = 1, para n >= 0
 * C(n, k) = C(n-1, k-1)+C(n, k +1), para 0 < k < n
 *
 * pre: n>=0, k>=0, k<=n
 *
 * versao recursiva com memorizacao
 */
int coefBinomioMem( int n, int k ) 
{
  int i; 
  int **C; 
  int res; 

  C = ( int** ) malloc(( n+1 ) * sizeof( int* )); 

  for( i = 0; i <= n; i++ ) 
	   C[ i ] = ( int* ) calloc(( k + 1 ), sizeof( int ));
    /* calloc inicializa cada posicaoo a zero. 
     * Como nenhum coeficiente toma o valor zero, 
     * podemos usar esta inicializacao */
 
  res = coefBinAux( C, n, k ); 
  
  free( C ); 
 
  return res; 
}

/***
 * A declaracao static aplicada a uma funcao limita o "scope" dessa funcao
 * ao ficheiro onde esta a ser compilada
 */
static int coefBinAux( int** C, int n, int k ) 
{
  if( C[ n ][ k ] != 0 ) 
   	return C[ n ][ k ]; 

  if( k == 0 || n == k ) {
   	C[ n ][ k ] = 1;
    return 1; 
  }

  C[ n ][ k ] = coefBinAux( C, n - 1, k - 1 ) + coefBinAux( C, n - 1, k );

  return C[ n ][ k ]; 
}

/***
 * Em alternativa as posicoes do vector poderiam ser inicializadas
 * com um valor que se considerasse que nao seria um valor valido 
 * para os coeficientes
/* 
#define UNKNOWN -1
int coefBinomiomem( int n, int k ) 
{
 int i, j; 
 int **C; 

 C = ( int** ) malloc(( n+1 ) *sizeof( int* ) ); 

 for( i = 0; i < = n; i++ ) {
	C[i] = ( int* ) malloc(( i+1 ) *sizeof( int ) ); 
 }

 for( i = 0; i < = n; i++ ) 
	for( j = 0; j < = i; j++ ) 
	 C[i][j] = UNKNOWN; 

 return coefBinaux( C, n, k ); 
}

static int coefBinAux( int** C, int n, int k ) 
{
 if( C[n][k]! = UNKNOWN ) 
	return C[n][k]; 

 if( k = = 0 || n = = k ) {
	C[n][k] = 1; 
	return 1; 
 }

 C[n][k] = coefBinaux( C, n-1, k-1 ) + coefbinaux( C, n-1, k ); 
 return C[n][k]; 
}


/***
 * O maximo divisor comum entre m e n, pelo metodo de Euclides
 *
 * mdc(m, n) = m, se n=0
 * mdc(m, n) = mdc(n, resto de m/n) , caso contrario
 *
 * E.g.: mdc( 150, 20 ) = mdc( 20, 10 ) = mdc( 10, 0 ) = 10
 *  (ver pag 32, ex 5)
 * Versao recursiva
 */
int mdcRec( int m, int n ) 
{
  if( n == 0 ) 
	   return m; 

  return mdcRec( n, m % n ); 
}

int mdcIter( int a, int b ) {
  int temp, m = a, n = b; 

  while( n > 0 ) {
	   temp = n; 
	   n = m % n; 
	   m = temp; 
  }
 
  return m; 
}

/***
 * Escreve no ecra as permutacoes de uma cadeia de caracteres s
 */
void permutacoes( char s[] ) 
{
  perm( s, 0, strlen( s ) - 1 ); 
}

/***
 * Funcao auxiliar que escreve as permutacoes de s considerando
 * os caracteres entre as posicoes i e n inclusive. Esta
 * funcao esconde os parametros que controlam a escrita das
 * permutacoes. Nota-se que a segunda chamada a funcao "troca"
 * (no ciclo) tem como objectivo repor o efeito provocado pela
 * primeira troca, na qual houve uma passagem de parametros 
 * por referencia.   
 */
static void perm( char s[], int i, int n ) 
{
  int p; 

  if( i == n ) 
    printf( "%s\n", s ); 
  else 
    for( p = i; p <= n; p++ ) { 
	     troca( &s[p], &s[i] ); 
      perm( s, i + 1, n ); 
      troca( &s[p], &s[i] ); 
	   }
}

/* Troca o valor de duas variaveis (*s e *t) */
static void troca( char *s, char *t ) 
{
  char x = *s; 
  *s = *t; 
  *t = x; 
}

/***
 * imprime os movimentos necessarios para passar discs discos duma torre 
 * source para outra torre target no puzzle das torres de Hanoi. Assume-se que
 * a torre da esquerda tem indice 0, a do meio indice 1 e a da direita indice 2
 * solucao recursiva
 * uso: hanoi( 0, 2, nDiscs );
 * pre: discs>=1, source>=0, target<=2
 */
void hanoi( int source, int target, int discs ) 
{
  int temp; /* torre temporaria para armazenar os discs-1 discos superiores */

  if( discs == 1 ) {
    printf( "%d - > %d\n", source, target ); 
  } 
  else
  {
    temp = 3 - source - target; 
    hanoi( source, temp, discs - 1 ); 
    hanoi( source, target, 1 ); 
    hanoi( temp, target, discs - 1 ); 
  }
}




/***
 * Calcula o numero de Catalan de ordem n, dado pela expressao
 * C(0)=1
 * C(n+1)=C(n).2.(2n+1)/(n+2) 
 *
 * pre: n>=0 
 * versao recursiva
 */
int numCatalanRec( int n ) 
{
	 if( n == 0 ) 
		  return 1; 
	
  return numCatalanRec( n - 1 ) * ( 4 * n + 2 ) / ( n + 2 ); 
}

/***
 * Calcula o numero de Catalan de ordem n, dado pela expressao
 * C(0)=1
 * C(n+1)=C(n).2.(2n+1)/(n+2) 
 *
 * pre: n>=0 
 * versao iterativa
 */
int numCatalanIter( int n ) 
{ 
  int i, res = 1; 

  for( i = 1; i <= n; i++ ) 
	   res = res * ( 4 * i + 2 ) / ( i+2 ); 
 
  return res; 
}


/***
 * Calcula o numero de Catalan de ordem n, dado pela expressao
 * C(0)=1
 * C(n+1)=C(n).2.(2n+1)/(n+2) 
 *
 * pre: n>=0 
 * versao recursiva com memorizacao
 */
int numCatalanRecMem( int n ) 
{
  int *c = ( int* ) calloc(( n+1 ), sizeof( int ) ); 
  int numeroProcurado;

  numeroProcurado = numCatalanAux( c, n );
 
  free( c );
  return numeroProcurado; 
}

static int numCatalanAux( int *c, int n ) 
{
  if( c[ n ] != 0 )
	   return c[ n ]; 
  if( n == 0 ) {
	   c[ 0 ] = 1; 
	   return 1; 
  }
 
  c[ n ] = numCatalanAux( c, n - 1 ) * ( 4 * n + 2 ) / ( n + 2 ); 
 
  return c[ n ]; 
}

void escreveVector( int v[], int size ){
  int i;

  printf( "\n\nv = { %d", v[ 0 ] );
  for ( i = 1; i < size; i++ ) printf( ", %d", v[ i ] );
  printf( " }" );
}
