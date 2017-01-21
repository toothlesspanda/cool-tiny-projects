#include "list-private.h"
#include "list.h"
#include "table-private.h"
#include "table.h"
#include "entry.h"
#include "data.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


int hash(char *s, int n);

struct table_t;
struct index;

/* Função para criar/inicializar uma nova tabela hash, com n
* linhas(n = módulo da função hash)
*/
struct table_t *table_create(int n){
	int i;

	struct table_t *t = (struct table_t*)malloc(sizeof(struct table_t));
	t->l= (struct list_t**)malloc(n*sizeof(struct list_t*));

	t->linhas=n;
	t->elem=0;	

	for(i=0;i<t->linhas;i++){
		t->l[i]=list_create();
	}

	return t;
}

/* Eliminar/desalocar toda a memoria
*/
void table_destroy(struct table_t *table){
	int j;
	if(table->elem>0){
		for(j=0;j<table->linhas;j++){	
			list_destroy(table->l[j]);
	
		}
	}	

	free(table);

}


/* Funcao para adicionar um elemento na tabela.
* A função vai copiar a key (string) e os dados num
* espaco de memoria alocado por malloc().
* Se a key ja existe, vai substituir essa entrada
* pelos novos dados.
* Devolve 0 (ok) ou -1 (out of memory)
*/
int table_put(struct table_t *table, char *key, struct data_t
*data){

	int size;

	
	struct entry_t *entry=entry_create(strdup(key),data_dup(data));
	struct entry_t *copy=entry_dup(entry);
	if(entry == NULL)	
		return -1;

	size=hash(key,table->linhas);

	if(list_get(table->l[size],key)!= NULL){
			data_destroy((list_get(table->l[size],key))->value);
			list_get(table->l[size],key)->value=data_dup(data);		
	return 0;
	} else {
			table->elem++;
			return list_add(table->l[size],copy);
		}
			
		

	return -1;
}


/* Funcao para adicionar um elemento na tabela caso a chave
* associada ao elemento nao exista na tabela. Caso a chave
* exista, retorna erro.
* A função vai copiar a key (string) e os dados num
* espaco de memoria alocado por malloc().
* Devolve 0 (ok) ou -1 (chave existe ou out of memory)
*/
int table_conditional_put(struct table_t *table, char *key, struct
data_t *data){


	int size;

	struct data_t *data_copy=data_dup(data);
	struct entry_t *copy=entry_create(strdup(key),data_copy);
	
	//printf("mxt d j,fv\n");

	if(copy == NULL)	
		return -1;
	
	//printf("inescoco\n");

	size=hash(key,table->linhas);

	if(list_get(table->l[size],key)== NULL){
			table->elem++;
			return list_add(table->l[size],copy);
		}
			
		//printf("roscafo\n");

	return -1;

}


/* Funcao para obter um elemento da tabela.
* O argumento key indica a chave da entrada da tabela.
* A função aloca memoria para armazenar uma *COPIA*
* dos dados da tabela e retorna este endereco.
* O programa a usar essa funcao deve
* desalocar (utilizando free()) esta memoria.
* Em caso de erro, devolve NULL
*/
struct data_t *table_get(struct table_t *table, char *key){
	int size;
	
	struct entry_t *entrada;
	struct data_t *copy;
	size=hash(key,table->linhas);

	if(list_get(table->l[size], key)!=NULL){
			entrada=entry_dup(list_get(table->l[size],key));	
			return copy=data_dup(entrada->value);
		}	
	
	return NULL;


}
/* Funcao para remover um elemento da tabela. Vai desalocar
* toda a memoria alocada na respetiva operação table_put().
* Devolve: 0 (ok), -1 (key not found)
*/
int table_del(struct table_t *table, char *key){

	int size=hash(key,table->linhas);

	if(list_get(table->l[size],key)!= NULL){
		table->elem--;
		list_remove( table->l[size], key );
			return 0;		
	}
		
	
	return -1;
}


/* Devolve o numero de elementos da tabela.
*/
int table_size(struct table_t *table){

	return table->elem;
}


/* Devolve um array de char * com a copia de todas as
* keys da tabela, e um ultimo elemento a NULL.
*/
char **table_get_keys(struct table_t *table){

	char **keys = (char **)malloc((table_size(table)+1)* (sizeof(char *)));
	char **lkeys; 
	int i,j,count=0;
 
	for(i=0;i< table->linhas;i++ ){
		lkeys = list_get_keys(table->l[i]);
		if(lkeys != NULL){
			for(j=0; j<list_size(table->l[i]);j++){			
			keys[count] = strdup(lkeys[j]);
			count++;
			}
		list_free_keys(lkeys);
		lkeys = NULL;
		}
	}
	
	keys[count] = NULL;
	
	return keys;
}


/* Desaloca a memoria alocada por table_get_keys()
*/
void table_free_keys(char **keys){

  int i; 
  
  for(i=0; keys[i]!=NULL ;i++) free(keys[i]);

  free( keys );
}

int hash(char *s, int n){
	int size, soma=0,i;

	size=strlen(s);
	if(size<=5){
		for(i=0; i<size;i++){
			soma = soma + s[i];
		}
	}
	if(size>5){
		for(i=0;i<=2;i++){
			soma+=s[i];
		}
		for(i=size-1;i<=size;i++){
			soma+=s[i];
		}
	
	}
	
return soma%n;
}


