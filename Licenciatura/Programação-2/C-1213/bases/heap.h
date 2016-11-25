#ifndef _HEAP
#define _HEAP

/* TDA amontoado */

typedef struct heap *Heap;

/* construtor */
Heap heap_criar(void);

/* destrutor */
void heap_destruir(Heap h);

/* apaga todos os elementos da heap */
void heap_apagar(Heap h);

/* verifica se o amontoado esta' vazio */
int heap_esta_vazia(Heap h);

/* devolve o valor da raiz, ie, o maior elemento */
int heap_root(Heap h);

/* insere novo elemento no amontoado, O(log n) */
void heap_inserir(Heap h, int elem);

/* remove a raiz do amontoado, O(log n) */
void heap_remRoot(Heap h);

#endif
