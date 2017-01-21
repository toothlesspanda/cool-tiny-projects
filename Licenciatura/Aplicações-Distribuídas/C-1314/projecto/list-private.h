#ifndef _LIST_PRIVATE_H
#define _LIST_PRIVATE_H
#include "list.h"

typedef struct no *No;

/*no*/
struct no {
	struct entry_t *e;
	No prox;
	No ant;
};

/*lista*/
struct list_t {
	int tam;
	No cabeca;
};

#endif
