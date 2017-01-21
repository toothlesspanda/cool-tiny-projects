#include "list.h" 
#include "list-private.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct list_t;
/*A definir pelo grupo em list-private.h*/

/* Cria uma nova lista. Em caso de erro, retorna NULL.
 */
struct list_t *list_create() {

	struct list_t *l = (struct list_t*) malloc(sizeof(struct list_t));

	if (l == NULL) /* erro na alocacao de memoria */
		return NULL;

	l->cabeca = NULL;
	l->tam = 0;

	return l;
}

/* Elimina uma lista, libertando *toda* a memoria utilizada pela
 * lista.
 * Retorna 0 (OK) ou -1 (erro)
 */
int list_destroy(struct list_t *list) {
	int ret;

	if (list->cabeca != NULL) {
		free(list);
		ret = 0;
	} else {
		ret = -1;
	}

	return ret;
}

/* Adiciona uma entry na lista. Como a lista deve ser ordenada, 
 * a nova entry deve ser colocada no local correto.
 * Retorna 0 (OK) ou -1 (erro)
 */
int list_add(struct list_t *list, struct entry_t *entry) {
	
	No novo_no = (struct no*) malloc(sizeof(struct no));

	if (novo_no == NULL)
		return -1;

	novo_no->e = entry;
	novo_no->prox = NULL;
	novo_no->ant = NULL;

	/*add cabeca*/
	if (list_size(list) == 0) {
		list->cabeca = novo_no;
		list->tam++;

		return 0;
	}
	
	No aux = list->cabeca;
	No auxprox = aux->prox;

	if (list_size(list) > 0) {

		if (strcmp(entry->key, aux->e->key) > 0 ) {

			list->cabeca = novo_no;
			novo_no->prox = aux;
			list->cabeca->ant = NULL;
			aux->ant = list->cabeca;
	
			list->tam++;
			return 0;
		}
	
	}
	/*meio: lista com mais do que 1 elemento*/
	if (list_size(list) > 1 ) {
		while (aux->prox != NULL) { /*add caso seja igual ou maior*/


			if (strcmp(entry->key, aux->e->key) > 0 ) {

				aux->prox = novo_no;
				novo_no->ant = aux;
				novo_no->prox = auxprox;
				aux->ant = novo_no;
				list->tam++;

				return 0;
			}


			aux = auxprox;
			auxprox = auxprox->prox;
		}
	}

	/*add no fim, quando menor*/
	aux->prox = novo_no;
	novo_no->ant = aux;
	list->tam++;
	
	return -1;
}

/* Elimina da lista um elemento com a chave key. 
 * Retorna 0 (OK) ou -1 (erro)
 */
int list_remove(struct list_t *list, char *key) {

	No aux = list->cabeca;
	No auxprox = aux->prox;

	if (key == NULL)
		return -1;
	if (list->tam == 0) { /*caso a lista esteja vazia*/
		list_destroy(list);
		return -1;
	}

	if (strcmp(list->cabeca->e->key, key) == 0) {

		list->cabeca = auxprox;
		entry_destroy(aux->e);
		free(aux);
		list->tam--;

		return 0;
	}

	while (auxprox != NULL) {

		if (strcmp(auxprox->e->key, key) == 0) {

			aux->prox = auxprox->prox;
			entry_destroy(auxprox->e);
			free(auxprox);
			list->tam--;
			return 0;
		}
		aux = auxprox;
		auxprox = auxprox->prox;
	}/*retorna -1 caso nao exista a key*/

	return -1;
}

/* Obtem um elemento da lista com a chave key. 
 * Retorna a referencia do elemento na lista (ou seja, uma alteração
 * implica alterar o elemento na lista).
 */
struct entry_t *list_get(struct list_t *list, char *key) {

	struct no *aux = list->cabeca;

	while (aux != NULL) {
		if (strcmp(aux->e->key, key) == 0) {
			return aux->e;
		}
		aux = aux->prox;
	}
	return NULL;

}

/* Retorna o tamanho (numero de elementos) da lista 
 * Retorna -1 em caso de erro)
 */
int list_size(struct list_t *list) {
	return list->tam;

}

/* Devolve um array de char* com a cópia de todas as keys da
 * lista, com um ultimo elemento a NULL.
 */
char **list_get_keys(struct list_t *list) {

	struct no *aux=list->cabeca;
	char **keys = (char **) malloc((list_size(list) + 1) * sizeof(char *));
	int i;

	for (i = 0; aux!= NULL; i++) {
		keys[i] = (char *) malloc(
				(strlen(aux->e->key) + 1) * sizeof(char));
		strcpy(keys[i], aux->e->key);
		aux = aux->prox;
	}

	keys[list_size(list)]=NULL;
	return keys;
}

/* Liberta a memoria alocada por list_get_keys
 */
void list_free_keys(char **keys) {
	int i;

	for (i = 0; keys[i] != NULL; i++) {
		free(keys[i]);
	}
	
	free(keys);
}


