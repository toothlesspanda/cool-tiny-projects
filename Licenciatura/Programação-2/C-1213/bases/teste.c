/*
 * teste.c
 *
 *  Created on: 10 de Mai de 2011
 *      Author: respicio
 */
/* o qsort esta definido na stdlib */
#include<stdlib.h>

#include<stdio.h>
/* qsort int comparison function */
int int_cmp(const void *a, const void *b)
{
  const int *ia = (const int *)a; // casting pointer types
  const int *ib = (const int *)b;
  return *ia - *ib;
  /* integer comparison: returns negative if b > a
     and positive if a > b */
}

int main( void )
{
	  int numbers[] = { 7, 3, 4, 1, -1, 23, 12, 43, 2, -4, 5 };
	  int i;
	  size_t numbers_len = sizeof(numbers)/sizeof(int);
	  for ( i = 0; i < numbers_len; i++ ) printf( "%d\n", numbers[i]);


	  /* sort array using qsort functions */
	  qsort(numbers, numbers_len, sizeof(int), int_cmp);

	  printf ("\ndepois de ordenado\n");
	  for ( i = 0; i < numbers_len; i++ ) printf( "%d\n", numbers[i]);
	  return 0;

}

