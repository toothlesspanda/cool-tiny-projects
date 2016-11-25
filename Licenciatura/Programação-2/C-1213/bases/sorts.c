/**
 * Algoritmos de Ordenacao.
 *
 * author: Francisco Martins; modificacoes por csl & jpn (quicksort), 
 * respicio (insertion sort, selection sort, bubble sort)
 * date: May, 2007; May, 2008; April 2009
 * version: $Id: ord_fusao.c 12 2007-07-30 09:47:11Z fmartins $
 */

 #include <stdio.h>
#include <stdlib.h>

/* Operacoes privadas */
static void fundir (int vec [], int inicio, int meio, int fim);

static void trocar (int v [], int i, int j)
{
  int t = v [i];
  v [i] = v [j];
  v [j] = t;
}

/*********************************************************/
/* Ordenacao via insertion sort */
void insertionSort( int v[], int n )
{
  int i, j, key;
 
  for ( i = 1; i < n; i++ ) 
  {
    key = v[ j ];
    j = i - 1;
    while ( j >= 0 && v[ j ] > key )
    {
      v[ j + 1 ] = v[ j ];
      j--;
    }         
    v[ j + 1 ] = key;
  }
}

/**
 * Ordenacao via selection Sort do vector v dadas as 
 * posicoes inicio e fim
 */
void selectionSort( int v[], int inicio, int fim )
{
  int i, j, ind_minimo;
  
  for ( i = inicio; i < fim; i++ ) {
    ind_minimo = i;
    for ( j = i + 1; j <= fim; j++ )
      if ( v[ j ] < v[ ind_minimo ] )
        ind_minimo = j;
 
    if ( ind_minimo != i )
      trocar( v, i, ind_minimo );
  }
}

/*****************************************************/
/* Ordenacao via quicksort */
void ord_quick (int vec [], int inicio, int fim)
{
    int  lower  = inicio,
         higher = fim,
         pivot;

    if (fim>inicio) {
                 /* Aqui, o pivot, arbitrariamente, e' o do meio */
      pivot = vec[inicio + (fim-inicio)/2];    /* evita overflow */

      while (lower<=higher) {
        while (lower<fim && vec[lower]<pivot)
          lower++;

        while (higher>inicio && vec[higher] > pivot)
          higher--;

              /* se os indices ainda nao se cruzaram... */
        if(lower <= higher)  /* ...trocar os elementos  */
          trocar(vec, lower++, higher--);
      }

      if(inicio<higher)
        ord_quick(vec, inicio, higher);

      if(lower<fim)
        ord_quick(vec, lower, fim);
    }
}

/*****************************************************/
/* Ordenacao via mergesort */
void mergeSort( int vec[], int inicio, int fim )
{
  if ( inicio < fim )
  {
    int meio = inicio + ( fim - inicio ) / 2;
      /* (inicio+fim)/2 produz overflow qd perto do valor max do int */
    mergeSort( vec, inicio, meio );
    mergeSort( vec, meio + 1, fim );
    fundir( vec, inicio, meio, fim );
  }
}

/* Fundir duas metades ordenadas de um vector */
static void fundir (int vec [], int inicio, int meio, int fim)
{
  int *aux = malloc ((fim - inicio + 1) * sizeof (int));
  int part1 = inicio,
      part2 = meio + 1,
      destino = 0;
  
  while (part1 <= meio && part2 <= fim) 
    aux [destino++] =
      vec [part1] < vec [part2] ? vec [part1++] : vec [part2++];
  
  if (part1 <= meio)
    while (part1 <= meio)
      aux [destino++] = vec [part1++];
  else
    while (part2 <= fim)
      aux [destino++] = vec [part2++];

  for (part1 = inicio, destino = 0; part1 <= fim; part1++, destino++)
    vec [part1] = aux [destino];

  free(aux);
}

/**************************************************/
/* Ordenacao via countsort, onde se assume uma variacao
 * maxima 'm' entre os elementos a ordenar. Assim, e'
 * possivel ordenar em tempo O(n+m)
 *
 * Adaptado de http://en.wikipedia.org/wiki/Counting_sort
 */
void ord_count (int vec [], int inicio, int fim)
{
  int i, j, k, min, max;
  int *ocorrencias;

  min = max = vec[inicio];            /* descobrir variacao */
  for(i=inicio+1; i<=fim; i++)
    if (vec[i] < min)
      min = vec[i];
    else if (vec[i] > max)
      max = vec[i];

  ocorrencias = calloc(max - min + 1, sizeof(int));

  for(i=inicio; i<=fim; i++)
    ocorrencias[vec[i] - min]++;   /* guardar as ocorrencias de vec[] */

  for(i=min, j=0; i <= max; i++)  /* reinserir as ocorrencias em vec[] */
    for(k=0; k<ocorrencias[i - min]; k++)
      vec[j++] = i;

  free(ocorrencias);
}
/**
 * Ordenacao via Bubble Sort, do vector v com n elementos.
 * Tempo de execução O(n^2)
 */
void bubbleSort(int v[], int n)
{
  int i;
  int continua;
 
  do
  {
    n--;
    continua = 0;
 
    for ( i = 0; i < n; i++ )
      if( v[ i ] > v[ i + 1 ] )
      {
        trocar( v, i, i +1 );
        continua = 1; 
      }

  /* soh deixa de percorrer o vector quando nao conseguiu nenhuma troca
   * nesse caso continua = 0
   * ou seja, qd todos os elems jah estao por ordem "certa"
   */
  } while ( continua );
}


/*****************************************************/
/* escrever o vector no ecra */
void escrever_vector (int vec [], int inicio, int fim)
{
  int i;

  for (i = inicio; i <= fim; i++)
    printf ("%d ", vec [i]);

  printf ("\n");
}

