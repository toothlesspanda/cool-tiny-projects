#include "data.h"
#include "entry.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


/* problema no destroy e no dup, o resto passa*/
int testCreate() {
	char *key = strdup("123abc");

	struct data_t *value = data_create2(strlen("1234567890abc")+1,strdup("1234567890abc"));

	struct entry_t *entry = entry_create(key,value);

	int result = (entry->key == key) && 
                     (entry->value == value);

	entry_destroy(entry);


	printf("Modulo entry -> teste entry_create: %s\n",result?"passou":"nao passou");
	return result;
}

int testDup() {
	char *key = strdup("123abc");
	struct data_t *value = data_create2(strlen("1234567890abc")+1,strdup("1234567890abc"));
	
	struct entry_t *entry = entry_create(key,value);

	struct entry_t *entry2 = entry_dup(entry);

	int result = (entry->key != entry2->key) &&
		     (strcmp(entry->key,entry2->key) == 0) && 
                     (entry->value != entry2->value) && /*condicao que nao resulta*/
		     (entry->value->datasize == entry2->value->datasize) &&
                     (memcmp(entry->value->data, entry2->value->data, entry->value->datasize) == 0);

	entry_destroy(entry);
	entry_destroy(entry2);

	printf("Modulo entry -> teste entry_dup: %s\n",result?"passou":"não passou");
	return result;
}

int testCreate_2() {
	char *key = strdup("123abc");

	struct data_t *value = data_create2(strlen("18x3r83ny q8pyf38fy p93y4fz349f \n sss09duqw \t oqjd j3209xu239 x2309xu ` ' /)/(&%&/$%#")+1,strdup("18x3r83ny q8pyf38fy p93y4fz349f \n sss09duqw \t oqjd j3209xu239 x2309xu ` ' /)/(&%&/$%#"));

	struct entry_t *entry = entry_create(key,value);

	int result = (entry->key == key) && 
                     (entry->value == value);

	entry_destroy(entry);

	printf("Modulo entry -> teste entry_create_2: %s\n",result?"passou":"nao passou");
	return result;
}

int testDup_2() {
	char *key = strdup("123abc");
	struct data_t *value = data_create2(strlen("18x3r83ny q8pyf38fy p93y4fz349f \n sss09duqw \t oqjd j3209xu239 x2309xu ` ' /)/(&%&/$%#")+1,strdup("18x3r83ny q8pyf38fy p93y4fz349f \n sss09duqw \t oqjd j3209xu239 x2309xu ` ' /)/(&%&/$%#"));

	struct entry_t *entry = entry_create(key,value);

	struct entry_t *entry2 = entry_dup(entry);

	int result = (entry->key != entry2->key) &&
		     (strcmp(entry->key,entry2->key) == 0) && 
                     (entry->value != entry2->value) &&
		     (entry->value->datasize == entry2->value->datasize) &&
                     (memcmp(entry->value->data, entry2->value->data, entry->value->datasize) == 0);

	entry_destroy(entry);
	entry_destroy(entry2);

	printf("Modulo entry -> teste entry_dup_2: %s\n",result?"passou":"não passou");
	return result;
}


int main() {
	int score = 0;

	printf("Iniciando o teste do modulo entry\n");
	
	score += testCreate();
	
	score += testDup();
	
	score += testCreate_2();

	score += testDup_2();

	printf("Resultados do teste do modulo entry: %d/4\n",score);

	return score;
}
