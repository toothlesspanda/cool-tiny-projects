#include <stdlib.h>
#include "heap.h"

/*
 * Uma implementaçao vectorial do TDA Amontoado
 */

#define SIZE 128  /* dimensao inicial do vector */

struct heap {
  int *myheap;  /* o vector onde se armazena os valores inteiros */
  int nElems;   /* o numero de elementos actual */
  int size;     /* a capacidade do amontoado */
};

Heap heap_criar(void) {
  Heap h = malloc(sizeof(struct heap));

  h->size = SIZE;
  h->myheap = malloc(h->size * sizeof(int));
  h->nElems = 0;
  
  return h;
}

void heap_destruir(Heap h) {
  free(h->myheap);
  free(h);
}

void heap_apagar(Heap h) {
  h->nElems = 0;
}

int heap_esta_vazia(Heap h) {
  return h->nElems == 0;
}

int heap_root(Heap h) {
  return h->myheap[0];
}

/**** funcoes auxiliares ****/

static int left(int index) {
  return 2 * index + 1;
}

static int right(int index) {
  return 2 * index + 2;
}

static int father(int index) {
  return (index-1)/2;
}

static void swap(int* v, int i, int j) {
  int tmp = v[i];
  v[i] = v[j];
  v[j] = tmp;
}

static void grow(Heap h) {
  h->size *= 2;
	h->myheap = realloc(h->myheap, h->size * sizeof(int));
}

static void moveUp(Heap h); /* ver abaixo */
static void moveDown(Heap h);

/****************************/

void heap_inserir(Heap h, int elem) {
  if (h->nElems == h->size)
    grow(h);

  h->myheap[h->nElems] = elem;
  h->nElems++;
  moveUp(h);
}

static void moveUp(Heap h) {
  int pos = h->nElems-1;
  while (pos != 0 && h->myheap[pos] > h->myheap[father(pos)]) {
    swap(h->myheap, pos, father(pos));
    pos = father(pos);
  }
}

void heap_remRoot(Heap h) {
  if (h->nElems > 1) {
    h->myheap[0] = h->myheap[h->nElems-1];
    moveDown(h);
  }
  h->nElems--;
}

static void moveDown(Heap h) {
  int maxChild, pos = 0;

  while (pos  <= father(h->nElems-1)) {  /* posição do último nó não folha */
    maxChild = left(pos);
    if (maxChild < h->nElems-1)         /* verificar qual e' o maior filho */
      if (h->myheap[maxChild] < h->myheap[maxChild+1])
        maxChild++;
    if (h->myheap[pos] > h->myheap[maxChild])
      break;                                   /* ja' esta' no local certo */
    swap(h->myheap, pos, maxChild);
    pos = maxChild;
  }
}



