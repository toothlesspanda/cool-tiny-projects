#include "inet.h"
#include "network_client.h"
#include "message.h"

struct server_t *srv;

/* Esta funcao deve
 * - estabelecer a ligacao com o servidor;
 * - address_port e’ uma string no formato <hostname>:<port>
 *   (exemplo: 10.10.10.10:10000)
 * - retornar toda a informacao necessaria (e.g., descritor do socket)
 *   na estrutura server_t
 */


struct server_t *network_connect(const char *address_port) {
	char *host, *port;


	srv = (struct server_t*) malloc(sizeof(struct server_t));
	
	//srv->address_p=address_port;
printf("ola2\n");
	// Cria socket TCP
	if ((srv->socketNum = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
		perror("Erro ao criar socket TCP");
		return NULL;
	}

	host = strtok((char*) address_port, ":");
	port = strtok(NULL, "\0");

	printf("host %s | port %s\n", host, port);

	// Preenche estrutura server para estabelecer conexão
	srv->server.sin_family = AF_INET;
	srv->server.sin_port = htons(atoi(port));
	printf("ola3\n");
	if (inet_pton(AF_INET, host, &srv->server.sin_addr) < 1) {
		printf("Erro ao converter IP\n");
		close(srv->socketNum);
		return NULL;
	}
printf("ola4\n");
	// Estabelece conexão com o servidor definido em server
	if (connect(srv->socketNum, (struct sockaddr *) &srv->server,
			sizeof(srv->server)) < 0) {
		perror("Erro ao conectar-se ao servidor");
		close(srv->socketNum);
		return NULL;
	}
printf("ola5\n");
	return srv;
}


/*network_send_receive falha, a função deve dormir por RETRY_TIME
segundos (constante a definir em network_client-private.h)
e tentar uma única vez restabelecer a ligação, reenviar o pedido e esperar pela
resposta. Se falhar novamente em algum destes passos, deve retornar NULL para indicar o erro*/

/* Esta funcao deve
 * - Obter o descritor da ligacao (socket) da estrutura server_t;
 * - enviar a mensagem msg ao servidor;
 * - receber uma resposta do servidor;
 * - retornar a mensagem obtida como resposta ou NULL em caso de erro.
 */
struct message_t *network_send_receive(struct server_t *server,
		struct message_t *msg) {

	int bytes;
	char **msg_buf, *msg_buf2=NULL;
	uint32_t tamanho;
	int bufferSize=0, bufferSize2=0;
	uint32_t sizeR;
	int i=0;

	struct message_t *message = (struct message_t *) malloc(
			sizeof(struct message_t));

	msg_buf = (char**) malloc(sizeof(char*));

	//converte mensagem para buffer
	bufferSize = message_to_buffer(msg,msg_buf);
	//printf("buffersize %d\n", bufferSize);

	tamanho = htonl(bufferSize);
	
	// Envia tamanho da mensagem
	if ((bytes = write(server->socketNum, &tamanho, sizeof(uint32_t)))
			!= sizeof(uint32_t)) {
		perror("Erro ao enviar tamanho da mensagem ao servidor");
		//sleep(RETRY_TIME);	
		/*while(i<1){
			if((srv=network_connect(srv->address_p))!=NULL){
				printf("Conneccao feita\n");
				continue;
			}
		}*/
		close(server->socketNum);
		return NULL;
	}

	

	//envia a mensagem		
	if ((bytes = write_all(server->socketNum, *msg_buf, bufferSize))
			!= bufferSize) {
		perror("Erro ao enviar mensagem ao servidor");
		close(server->socketNum);
		return NULL;
	}
	free(msg_buf);


	printf("'A espera de resposta do servidor ...\n");

	//recebe tamanho da mensagem
	if ((bytes = read(server->socketNum, &sizeR, sizeof(int))) != sizeof(int)) {
		perror("Erro ao receber tamanho do servidor");
		close(server->socketNum);
		return NULL;
	}

	bufferSize2 = ntohl(sizeR);
	//printf("le tamanho 2x ");
	msg_buf2 = malloc(bufferSize2);

	
	// Recebe mensagem
	if ((bytes = read_all(server->socketNum, msg_buf2, bufferSize2)) != bufferSize2) {
		perror("Erro ao receber mensagem do servidor");
		close(server->socketNum);
		return NULL;
	}
	//printf("le mensagem 2x\n");
	//converte buffer	para mensagem
	message = buffer_to_message(msg_buf2, bufferSize2);

	free(msg_buf2);
	
	
	return message;
}

/* A funcao network_close() deve fechar a ligacao estabelecida por
 * network_connect(). Se network_connect() reservou memoria, a funcao
 * deve libertar essa memoria.
 */
int network_close(struct server_t *server) {

	close(server->socketNum);
	free(server);

	return 0;

}
