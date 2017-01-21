#include "entry.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* Funcao que cria um novo par chave-valor (isto e, que inicializa
 * a estrutura e aloca a memoria necessaria).
 */
struct entry_t *entry_create(char *key, struct data_t *data) {

	struct entry_t *entrada = (struct entry_t*) malloc(sizeof(struct entry_t));
	entrada->key = key;	
	entrada->value = data;
	entrada->timestamp = 0;

	return entrada;

}

/* Funcao que destroi um par chave-valor e liberta toda a memoria.
 */
void entry_destroy(struct entry_t *entry) {
	if (entry != NULL) {
		data_destroy(entry->value);
		free(entry->key);
		free(entry);
	}

}

/* Funcao que duplica um par chave-valor. */
struct entry_t *entry_dup(struct entry_t *entry) {

	struct entry_t *entry_copy = (struct entry_t*) malloc(
			sizeof(struct entry_t));
	entry_copy->key = strdup(entry->key);
	entry_copy->timestamp = entry->timestamp;
	entry_copy->value = data_dup(entry->value);
	
	return entry_copy;
}
