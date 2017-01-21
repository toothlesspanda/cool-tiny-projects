#include "inet.h"
#include "message-private.h"
#include "table.h"

struct table_t *t;
/* Inicia o skeleton da tabela.
 * O main() do servidor deve chamar esta funcao antes de usar a
 * funcao invoke(). O parametro n_lists define o numero de listas a
 * serem usadas pela tabela mantida no servidor.
 * Retorna 0 (OK) ou -1 (erro, por exemplo OUT OF MEMORY)
 */
int table_skel_init(int n_lists){
	
	if((t = table_create(n_lists))==0){
		return 0;
	}
	return -1;
}

/* Libertar toda a memoria e recursos alocados pela funcao anterior.
 */
int table_skel_destroy(){

	table_destroy(t);
	return 0;
}

/* Executa uma operacao (indicada pelo opcode na msg) e retorna o
 * resultado na propria struct msg.
 * Retorna 0 (OK) ou -1 (erro, por exemplo, tabela nao incializada)
 */
int invoke(struct message_t *msg){
	

	switch (msg->opcode) {
				case OC_PUT: {
	
					msg->opcode = OC_PUT + 1;
					msg->c_type = CT_RESULT;
					msg->content.result = table_put(t, msg->content.entry->key,msg->content.entry->value);
					return 0;
					break;
				}
				case OC_COND_PUT: {
					msg->opcode = OC_COND_PUT + 1;
					msg->c_type = CT_RESULT;
					msg->content.result = table_conditional_put(t,
						msg->content.entry->key, msg->content.entry->value);
					return 0;
					break;
				}
				case OC_GET: {	

					msg->opcode = OC_GET + 1;
					msg->c_type = CT_VALUE;
					msg->content.value = table_get(t, msg->content.key);
					return 0;
					break;
				}
				case OC_DEL: {
					msg->opcode = OC_DEL + 1;
					msg->c_type = CT_RESULT;
					msg->content.result = table_del(t, msg->content.key);
					return 0;
					break;
				}
				case OC_SIZE: {
					msg->opcode = OC_SIZE + 1;
					msg->c_type = CT_RESULT;
					msg->content.result = table_size(t);
					return 0;
					break;
				}
				case OC_GET_KEYS: {
					msg->opcode = OC_GET_KEYS + 1;
					msg->c_type = CT_KEYS;
					msg->content.keys = table_get_keys(t);
					return 0 ;
					break;
				}
				default: return -1;
			}

}

char **print_tabela(){

	return table_get_keys(t);

}
