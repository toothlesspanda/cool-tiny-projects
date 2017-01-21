#include "data.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* Estrutura que contem os dados, isto e, o valor do par 
 * (chave, valor), juntamente com o seu tamanho.
 */
struct data_t *data_create(int size) {

	struct data_t *d = (struct data_t*) malloc(sizeof(struct data_t));
	d->datasize = size;
	d->data = malloc(size);
	return d;
}

/* Funcao que cria um novo bloco de dados (isto e, que inicializa
 * a estrutura e aloca a memoria necessaria).
 */
struct data_t *data_create2(int size, void *data) {
	struct data_t *d2 = (struct data_t*) malloc(sizeof(struct data_t));
	d2->datasize = size;
	d2->data = data;

	return d2;
}

/* Funcao que destroi um bloco de dados e liberta toda a memoria.
 */
void data_destroy(struct data_t *data) {
	if (data != NULL) {
		free(data->data);
		free(data);
	}
}

/* Funcao que duplica um bloco de dados. Quando se criam duplicados
 * e necessario efetuar uma COPIA dos dados (e nao somente alocar a
 * memoria necessaria).
 */
struct data_t *data_dup(struct data_t *data) {

	struct data_t *d_copy = (struct data_t*) malloc(sizeof(struct data_t));
	d_copy->datasize = data->datasize;
	d_copy->data = malloc(data->datasize * sizeof(int));

	memcpy(d_copy->data, data->data, data->datasize);

	return d_copy;
}

