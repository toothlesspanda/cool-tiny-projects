#include "inet.h"
#include "data.h"
#include "message-private.h"
#include "network_client-private.h"
#include "client_stub-private.h"

struct rtable_t; /* A definir pelo grupo em client_stub-private.h */

/* Funcao para estabelecer uma associacao com uma tabela num servidor.
 * address_port e’ uma string no formato <hostname>:<port>.
 * retorna NULL em caso de erro
 */
struct rtable_t *rtable_bind(const char *address_port){

	struct rtable_t *rtable = (struct rtable_t*)malloc(sizeof(struct rtable_t));
	rtable->srv = (struct server_t*)malloc(sizeof(struct server_t));
	rtable->msg = (struct message_t*)malloc(sizeof(struct message_t));

	if((rtable->srv=network_connect(address_port))!=NULL){	
		return rtable;
	}
	
	return NULL;
}

/* Fecha a ligacao com o servidor, desaloca toda a memoria local.
 * Retorna 0 se tudo correr bem e -1 em caso de erro.
 */

int rtable_unbind(struct rtable_t *rtable){	
	if(network_close(rtable->srv)==0){
		free_message(rtable->msg);		
		free(rtable);
		return 0;
	}
	return -1;
}

/* Funcao para adicionar um elemento na tabela.
 * Se a key ja existe, vai substituir essa entrada pelos novos dados.
 * Devolve 0 (ok) ou -1 (problemas).
 */
int rtable_put(struct rtable_t *rtable, char *key, struct data_t *data){
	rtable->msg->opcode = OC_PUT;
	rtable->msg->c_type = CT_ENTRY;
	rtable->msg->content.entry = entry_create(strdup(key), data_dup(data));

	rtable->msg=network_send_receive(rtable->srv, rtable->msg);

	if(rtable->msg!=NULL) {
		return 0;
	}
	return -1;
}
/* Funcao para adicionar um elemento na tabela.
 * Se a key ja existe, retorna erro.
 * Devolve 0 (ok) ou -1 (problemas).
 */

int rtable_conditional_put(struct rtable_t *rtable, char *key, struct data_t
*data){
	rtable->msg->opcode = OC_COND_PUT;
	rtable->msg->c_type = CT_ENTRY;
	rtable->msg->content.entry = entry_create(key, data_dup(data));

	rtable->msg=network_send_receive(rtable->srv, rtable->msg);

	if(rtable->msg!=NULL) {
		return 0;
	}
	return -1;
}

/* Funcao para obter um elemento da tabela.
 * Em caso de erro, devolve NULL.
 */

struct data_t *rtable_get(struct rtable_t *rtable, char *key){
	rtable->msg->opcode = OC_GET;
	rtable->msg->c_type = CT_KEY;
	rtable->msg->content.key = key;

	rtable->msg=network_send_receive(rtable->srv, rtable->msg);

	if(rtable->msg!=NULL){ 
		return rtable->msg->content.value;
	}
	return NULL;
}

/* Funcao para remover um elemento da tabela. Vai desalocar
 * toda a memoria alocada na respectiva operacao rtable_put().
 * Devolve: 0 (ok), -1 (key not found ou outro problema).
 */
int rtable_del(struct rtable_t *rtable, char *key){
	rtable->msg->opcode = OC_DEL;
	rtable->msg->c_type = CT_KEY;
	rtable->msg->content.key = key;

	rtable->msg=network_send_receive(rtable->srv, rtable->msg);

	if(rtable->msg!=NULL){ 
		return rtable->msg->content.result;
	}
	return -1;
}

/* Devolve numero de elementos da tabela.
 */
int rtable_size(struct rtable_t *rtable){
	int size=-1;

	rtable->msg->opcode = OC_SIZE;
	rtable->msg->c_type = CT_RESULT;

	rtable->msg=network_send_receive(rtable->srv, rtable->msg);

	if(rtable->msg!=NULL){ 
		size=rtable->msg->content.result;
	}
	return size;
}

/* Devolve um array de char* com a copia de todas as keys da tabela,
 * e um ultimo elemento a NULL.
 */
char **rtable_get_keys(struct rtable_t *rtable){
	rtable->msg->opcode = OC_GET_KEYS;
	rtable->msg->c_type = CT_RESULT;

	rtable->msg=network_send_receive(rtable->srv, rtable->msg);

	if(rtable->msg!=NULL){ 
		return rtable->msg->content.keys;
	}
	return NULL;
}

/* Desaloca a memoria alocada por rtable_get_keys().
 */
void rtable_free_keys(char **keys){
	int i;	

  for(i=0; keys[i]!=NULL ;i++) free(keys[i]);

  free( keys );

}
