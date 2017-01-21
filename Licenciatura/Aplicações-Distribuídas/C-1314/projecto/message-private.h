#ifndef _MESSAGE_PRIVATE_H
#define _MESSAGE_PRIVATE_H
#include "message.h"
#include "entry.h"	
#include "data.h"
#include "list.h"
#include "list-private.h"
#include "inet.h"

/* Define os possiveis opcodes da mensagem */
#define OC_PUT		10
#define OC_COND_PUT	20
#define OC_GET   	30
#define OC_DEL		40
#define OC_SIZE		50
#define OC_GET_KEYS	60
#define OC_ERR 9
/* Define codigos para os possiveis conteudos da mensagem */
#define CT_ENTRY  100
#define CT_KEY    200
#define CT_KEYS   300
#define CT_VALUE  400
#define CT_RESULT 500

/* Estrutura que representa uma mensagem generica a ser transmitida.
 * Esta mensagem pode ter varios tipos de conteudos.
 */


struct message_t {
	short opcode; /* codigo da operacao na mensagem */
	short c_type; /* tipo do content da mensagem */
	union content_u {
		struct entry_t *entry;
		char *key;
		char **keys;
		struct data_t *value;
		int result;
	} content; /* conteudo da mensagem */
};

int read_all(int sock, char *buf, int len);

int write_all(int sock, char *buf, int len);

#endif
