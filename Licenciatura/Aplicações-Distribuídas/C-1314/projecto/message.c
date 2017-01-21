#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <netinet/in.h>
#include "data.h"
#include "entry.h"
#include "list.h"
#include "list-private.h"
#include "message.h"
#include "message-private.h"
#include <errno.h>

long long swap_bytes_64(long long number);

int message_to_buffer(struct message_t *msg, char **msg_buf) {
	int size = -1, offset = 0, size_s;

	switch (msg->c_type) {

	case CT_ENTRY: {
		long long timestamp;
		int keysize, datasize;
		short opc;
		short ct;

		/* opcode + ct_code + timestamp + keysize + key + datasize + data */
		size = sizeof(short) + sizeof(short) + sizeof(long long) + sizeof(int)
				+ strlen(msg->content.entry->key) + sizeof(int)
				+ msg->content.entry->value->datasize;
		
		size_s = strlen(msg->content.entry->key);

		*msg_buf = (char*) malloc(size);

		/*opcode*/
		opc = htons(msg->opcode);
		memcpy(*msg_buf + offset, &(opc), sizeof(short));

		/*ct_code*/
		ct = htons(msg->c_type);
		offset += sizeof(short);
		memcpy(*msg_buf + offset, &(ct), sizeof(short));

		/*timestamp*/
		offset += sizeof(short);
		timestamp = swap_bytes_64(msg->content.entry->timestamp);
		memcpy(*msg_buf + offset, &timestamp, sizeof(long long));

		/*keysize*/
		offset += sizeof(long long);
		keysize = htonl(size_s);
		memcpy(*msg_buf + offset, &(keysize), sizeof(int));

		/*key*/
		offset += sizeof(int);
		memcpy(*msg_buf + offset, msg->content.entry->key,
				strlen(msg->content.entry->key));

		/*datasize*/
		offset += strlen(msg->content.entry->key);
		datasize = htonl(msg->content.entry->value->datasize);
		memcpy(*msg_buf + offset, &(datasize), sizeof(int));

		/*data*/
		offset += sizeof(int);
		memcpy(*msg_buf + offset, msg->content.entry->value->data,
				msg->content.entry->value->datasize);



	}
		break;
	case CT_KEY: {
		short opc, ct;
		int keysize;

		/*opcode + ct_code + keysize + key*/
		size = sizeof(short) + sizeof(short) + sizeof(int)
				+ strlen(msg->content.key);
		*msg_buf = (char*) malloc(size);
		size_s = strlen(msg->content.key);

		/*opcode*/
		opc = htons(msg->opcode);
		memcpy(*msg_buf + offset, &(opc), sizeof(short));

		/*ct_code*/
		offset += sizeof(short);
		ct = htons(msg->c_type);
		memcpy(*msg_buf + offset, &(ct), sizeof(short));

		/*keysize*/
		offset += sizeof(short);
		keysize = htonl(size_s);
		memcpy(*msg_buf + offset, &(keysize), sizeof(int));

		/*key*/
		offset += sizeof(int);
		memcpy(*msg_buf + offset, msg->content.key, size_s);

	}
		break;
	case CT_KEYS: {
		short opc;
		short ct;
		int i, nkeysize = 0,keysize,keysize2;
		for (i = 0; msg->content.keys[i] != NULL; i++) {
			nkeysize += strlen(msg->content.keys[i]);
		}

		size = sizeof(short) + sizeof(short) + sizeof(int) + i * sizeof(int)
				+ nkeysize;

		*msg_buf = (char *) malloc(size);
		/*opcode*/
		opc = htons(msg->opcode);
		memcpy(*msg_buf + offset, &(opc), sizeof(short));
		offset += sizeof(short);
		/*c_type*/
		ct = htons(msg->c_type);
		memcpy(*msg_buf + offset, &(ct), sizeof(short));
		offset += sizeof(short);
		/*keysize*/
		keysize = htonl(i); 
		memcpy(*msg_buf + offset, &(keysize), sizeof(int));
		offset += sizeof(int);
		/*alloc de n keys*/
		for (i = 0; msg->content.keys[i] != NULL; i++) {
			keysize2 = htonl(strlen(msg->content.keys[i])); 
			memcpy(*msg_buf + offset, &(keysize2), sizeof(int));
			offset += sizeof(int);

			memcpy(*msg_buf + offset, msg->content.keys[i],
					strlen(msg->content.keys[i])); 
			offset += strlen(msg->content.keys[i]);
		} 

	}
		break;

	case CT_VALUE: {
		short opc, ct;
		int datasize;

		size = 2 * sizeof(short) + sizeof(int) + msg->content.value->datasize;
		*msg_buf = (char*) malloc(size);

		/*opcode*/
		opc = htons(msg->opcode);
		memcpy(*msg_buf + offset, &(opc), sizeof(short));
		offset += sizeof(short);

		/*c_type*/
		ct = htons(msg->c_type);
		memcpy(*msg_buf + offset, &(ct), sizeof(short));
		offset += sizeof(short);

		/*datasize*/
		datasize = htonl(msg->content.value->datasize);
		memcpy(*msg_buf + offset, &(datasize), sizeof(int));
		offset += sizeof(int);

		/*data*/
		memcpy(*msg_buf + offset, msg->content.value->data,
				msg->content.value->datasize);

	}
		break;

	case CT_RESULT: {
		short opc = htons(msg->opcode);
		short ct = htons(msg->c_type);
		size = 2 * sizeof(short) + sizeof(int);
		*msg_buf = (char*) malloc(size);

		/*opcode*/
		memcpy(*msg_buf + offset, &(opc), sizeof(short));
		offset += sizeof(short);

		/*c_type*/
		memcpy(*msg_buf + offset, &(ct), sizeof(short));
		offset += sizeof(short);

		/*result*/
		int result = htonl(msg->content.result);
		memcpy(*msg_buf + offset, &(result), sizeof(int));

	}
		break;

	}

	return size;
}

/* Transforma uma mensagem em buffer para uma struct message_t*
 */
struct message_t *buffer_to_message(char *msg_buf, int msg_size) {

	int offset = 0;
	short opc, ct;

	struct message_t *msg = (struct message_t*) malloc(
			sizeof(struct message_t));
	
	memcpy(&(opc), msg_buf + offset, sizeof(short));
	offset += sizeof(short);

	memcpy(&(ct), msg_buf + offset, sizeof(short));
	offset += sizeof(short);

	msg->opcode = ntohs(opc);
	msg->c_type = ntohs(ct);

	switch (msg->c_type) {

	case CT_ENTRY: { /*timestamp KEYSIZE   	KEY 		   DATASIZE    	DATA*/
		int keysize, keysize2, datasize, datasize2;
		char *key;
		long long timestamp, timestamp2;
		void *data;

		/*fazer swap a timestamp*/
		memcpy(&(timestamp), msg_buf + offset, sizeof(long long));
		offset += sizeof(long long);
		timestamp2 = swap_bytes_64(timestamp);
		/*keysize*/
		memcpy(&(keysize), msg_buf + offset, sizeof(int));
		keysize2 = ntohl(keysize);
		offset += sizeof(int);
		/*key*/
		key = strdup(msg_buf + offset);
		offset += keysize2;
		/*datasize*/
		memcpy(&(datasize), msg_buf + offset, sizeof(int));
		datasize2 = ntohl(datasize);
		offset += sizeof(int);
		/*data*/
		data = msg_buf + offset;

		struct data_t *data_copy = data_create2(datasize2, data);
		/*alloc do data no entry->value e entry*/
		msg->content.entry = entry_create(key, data_dup(data_copy));
		msg->content.entry->timestamp = timestamp2;

		free(data_copy);

		return msg;
	}
		break;
	case CT_KEY: {
		int keysize, keysize2;
		/*keysize*/
		memcpy(&(keysize), msg_buf + offset, sizeof(int));
		keysize2 = ntohl(keysize);
		offset += sizeof(int);
		/*key*/
		msg->content.key = strndup(msg_buf + offset, keysize2);

		return msg;
	}
		break;
	case CT_KEYS: {
		int i, nkeys, keysize, keysize2;
		/*n keys*/
		memcpy(&keysize, msg_buf + offset, sizeof(int)); 
		nkeys = ntohl(keysize);
		offset += sizeof(int);
		/*alloc do vector de chars*/
		msg->content.keys = (char **) malloc((nkeys + 1) * sizeof(char*));
		for (i = 0; i < nkeys; i++) {
			memcpy(&keysize, msg_buf + offset, sizeof(int)); 
			keysize2 = ntohl(keysize);
			offset += sizeof(int);
			msg->content.keys[i] = malloc(keysize2 + 1);
			memcpy(msg->content.keys[i], msg_buf + offset, keysize2);
			msg->content.keys[i][keysize2] = '\0';
			offset += keysize2;
		}
		msg->content.keys[nkeys] = NULL; /*ultimo elemento a null*/

		return msg;
	}
		break;

	case CT_VALUE: {
		int datasize, datasize2;
		void *data;
		/*datasize*/
		memcpy(&(datasize), msg_buf + offset, sizeof(int));
		offset += sizeof(int);
		datasize2 = ntohl(datasize);

		/*alocacao do data e atribuicao ao value da msg*/
		data = malloc(datasize2);
		memcpy(data, msg_buf + offset, datasize2);
		msg->content.value = data_create2(datasize2, data);

		return msg;
	}
		break;

	case CT_RESULT: {
		int result;
		/*result*/
		memcpy(&(result), msg_buf + offset, sizeof(int));
		msg->content.result = ntohl(result);

		return msg;
	}
		break;
	default:
		return NULL;

	}

	return NULL;

}

/* Liberta a memoria alocada na função buffer_to_message*/
void free_message(struct message_t *message) {

	switch (message->c_type) {

	case CT_ENTRY: {entry_destroy(message->content.entry); }
		break;

	case CT_KEY:{ free(message->content.key); }
		break;

	case CT_KEYS: { list_free_keys(message->content.keys); }
		break;

	case CT_VALUE:{ data_destroy(message->content.value); }
		break;

	}

	free(message);
}

long long swap_bytes_64(long long number) {
	long long new_number;

	new_number = ((number & 0x00000000000000FF) << 56
			| (number & 0x000000000000FF00) << 40
			| (number & 0x0000000000FF0000) << 24
			| (number & 0x00000000FF000000) << 8
			| (number & 0x000000FF00000000) >> 8
			| (number & 0x0000FF0000000000) >> 24
			| (number & 0x00FF000000000000) >> 40
			| (number & 0xFF00000000000000) >> 56);

	return new_number;
}


int read_all(int sock, char *buf, int len) {
	int bufsize = len;
	while(len>0) {
		int res = read(sock, buf, len);
		if(res<0) {
			if(errno==EINTR) continue;
			perror("read failed:");
			return res;
		}
	buf += res;
	len -= res;
	}

	return bufsize;
}

int write_all(int sock, char *buf, int len) {
	int bufsize = len;
	while(len>0) {
		int res = write(sock, buf, len);
		if(res<0) {
			if(errno==EINTR) continue;
			perror("write failed:");
			return res;
		}
	buf += res;
	len -= res;
	}

	return bufsize;
}




