#ifndef _TABLE_PRIVATE_H
#define _TABLE_PRIVATE_H
#include "table.h"
#include "list.h"


/*tabela*/
struct table_t{
	int linhas;
	int elem;
	struct list_t **l;
};

#endif
