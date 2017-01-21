#include "inet.h"
#include <errno.h>
#include <signal.h>
#include "network_client-private.h"
#include "message.h"
#include "client_stub-private.h"


void removerChar(char *s1, char eliminar);

int main(int argc, char *argv[]) {

	char *par[MAX_MSG];
	printf("Bem-vindo Cliente!\n");

	signal(SIGPIPE,SIG_IGN);
	signal(SIGSEGV,SIG_IGN);
	struct rtable_t *rt=(struct rtable_t*)malloc(sizeof(struct rtable_t));;

	rt = rtable_bind(argv[1]);

	par[0] = "";

	while (strcmp(par[0], "quit") != 0 ){ 

		char str[MAX_MSG], *parsing =NULL, *par[MAX_MSG], *key;
		int count_esp = 0,i;
		char *datadef=malloc(MAX_MSG);

		printf("Insira um comando:\n");
		fgets(str, MAX_MSG, stdin);

		for (i = 0; str[i] != '\0'; i++) {
			if (str[i] == ' ') {
				count_esp++;
			}
		}

		parsing = strtok(str, " ");
		par[0] = parsing;

		//guardar data
		for (i = 1; parsing != NULL; i++) {
			parsing = strtok(NULL, " ");
			par[i] = parsing;
		}
		//guardar key
			key = malloc(sizeof(char));
			key = par[count_esp];
			removerChar(key, '\n');

		if (count_esp > 1) {
			i = 1;
			while (i < count_esp) {
				strcat(datadef, par[i]);
				strcat(datadef, " ");
				i++;
				//printf("datadef dentro%s \n",(char*)datadef);
			}
			removerChar(datadef, '\n');
			
		}
			//printf("fora %s \n",(char*)datadef);
		
				
		struct message_t *msg2 = (struct message_t*)malloc(sizeof(struct message_t));
		if (count_esp > 1) {
			if (strcmp(par[0], "put") == 0) {
				char *datadef2=malloc(MAX_MSG);
				strncpy(datadef2, datadef, strlen(datadef) - 1);

				struct data_t *d = data_create2(strlen(datadef2),datadef2);
					
				printf("%s \n",(char*)d->data);
				rtable_put(rt, key, d);	
		                //data_destroy(d);
			} else if (strcmp(par[0], "cput") == 0) {
				char *datadef2=malloc(MAX_MSG);
				strncpy(datadef2, datadef, strlen(datadef) - 1);
		
				void *data = datadef2;
				struct data_t *d = data_create(strlen(data));
				d = data_create2((strlen(data)), data);
				rtable_conditional_put(rt, key, d);

			}
		} else if (count_esp == 1) {
			if (strcmp(par[0], "get") == 0) {
				rtable_get(rt, key);
	
			} else if (strcmp(par[0], "del") == 0) {
				rtable_del(rt, key);
	
			}
		} else if (count_esp == 0) {
			if (strcmp(par[0], "getkeys") == 0) {

				rtable_get_keys(rt);	

			} else if (strcmp(par[0], "size") == 0) {
				//printf("ola1\n");
				rtable_size(rt);	

			} else {

				rtable_unbind(rt);
				exit(0);
			}
		 
			
		} 			
			
		msg2=rt->msg;

		if ((msg2->opcode == (OC_PUT + 1)) && msg2->c_type == CT_RESULT) {
				//printf("ola\n");
			if (msg2->content.result == 0) {
				printf("Put Success!!\n");
			} else
				printf("Put Not Successful!!\n");
		}
		else if ((msg2->opcode == (OC_COND_PUT + 1)) && msg2->c_type == CT_RESULT) {
			if (msg2->content.result == 0) {
				printf("Cond. Put Success!!\n");
			} else
				printf("Cond. Put Not Successful!!\n");
		}
		else if ((msg2->opcode == (OC_GET + 1)) && msg2->c_type == CT_VALUE) {
			printf("Data: %s\n", (char*) msg2->content.value->data);

		}
		else if ((msg2->opcode == (OC_DEL + 1)) && msg2->c_type == CT_RESULT) {
			if (msg2->content.result == 0) {
				printf("Del Success!!\n");
			} else
				printf("Del Not Successful!!\n");
		}
		else if ((msg2->opcode == (OC_SIZE + 1)) && msg2->c_type == CT_RESULT) {
			printf("Size: %d\n", msg2->content.result);
		}
		else if ((msg2->opcode == (OC_GET_KEYS + 1)) && msg2->c_type == CT_KEYS) {
			for (i = 0; msg2->content.keys[i] != NULL; i++) {
				printf("Key[%d]: %s\n", i, msg2->content.keys[i]);
			}
			
		}
			free_message(msg2);
			
	
	}//fecha while
		
	free_message(rt->msg);
	rtable_unbind(rt);
	exit(0);

}

void removerChar(char *s1, char eliminar) {

	char *inicio, *fim;
	for (inicio = fim = s1; *inicio != '\0'; inicio++) {
		*fim = *inicio;
		if (*fim != eliminar)
			fim++;
	}
	*fim = '\0';
}
