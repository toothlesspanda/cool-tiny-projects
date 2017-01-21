#include "inet.h"
#include "network_client-private.h"
#include "message.h"
#include "table.h"
#include <signal.h>
#include "table_skel.h"
#include <poll.h>

/*setsockopt
 (..., SO_REUSEADDR, ...)*/

int actualizar_backup(int size, int sockfd,struct message_t *msg){
	int nbytes;
	char *buffer=malloc(size);
	message_to_buffer(msg,&buffer);
	

	if ((nbytes = write(sockfd, &size, sizeof(size)))
							!= sizeof(size)) {
		perror("Erro ao enviar tamanho da mensagem ao servidor secundario");
		close(sockfd);
		return -1;
	}

	if ((nbytes = write_all(sockfd, &buffer, size)
							!= size) {
		perror("Erro ao enviar mensagem ao servidor secundario");
		close(sockfd);
		return -1;
	}

	return nbytes;
}

int ligar_backup(char *ip, char *port){
	int socket, porto;

	struct sockaddr_in servers;
	
	port=atoi(port);
		

	// Cria socket TCP
	if ((socket = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
		perror("Erro ao criar socket");
		return -1;
	}

	// Preenche estrutura server para bind
	server.sin_family = AF_INET;
	server.sin_port = htons(port);
	server.sin_addr = (struct hostent*)gethostbyname(ip);  //estudar caso
	
	if(connect(socket,(struct sockaddr *)&servers, sizeof(servers))<0){
		perror("Erro ao conectar-se ao servidor");
		return -1:
	}


	return socket;
}

int main(int argc, char **argv) {

	int sockfd, connsockfd,n_listas, port, sockSec;

	struct sockaddr_in server, client;
	int nbytes, size, i, j;
	uint32_t sizeR;
	socklen_t size_client;
	int optval = 1;
	char ip[INET_ADDRSTRLEN];

	struct pollfd my_pfds[5];

	//ligar BACKUP SECUNDARIO
	if(argc==5){
		sockSec=ligar_backup(argv[3],argv[4]);
	
	}

	// Verifica se foi passado algum argumento
	if (argc != 3) {
		printf("Uso: ./server <n_listas> <porto_servidor>\n");
		printf("Exemplo de uso: ./server 4 12345\n");
		return -1;
	}

	signal(SIGPIPE, SIG_IGN);

	n_listas = atoi(argv[1]);
	port = atoi(argv[2]);

	// Cria socket TCP
	if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
		perror("Erro ao criar socket");
		return -1;
	}

	// Preenche estrutura server para bind
	server.sin_family = AF_INET;
	server.sin_port = htons(port);
	server.sin_addr.s_addr = htonl(INADDR_ANY);
	setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &optval, sizeof optval);

	// Faz bind
	if (bind(sockfd, (struct sockaddr *) &server, sizeof(server)) < 0) {
		perror("Erro ao fazer bind");
		close(sockfd);
		return -1;
	};

	// Faz listen
	if (listen(sockfd, 0) < 0) {
		perror("Erro ao executar listen");
		close(sockfd);
		return -1;
	};

	printf("Servidor 'a espera de dados\n");

	//struct table_t *t = table_create(n_listas);
	table_skel_init(n_listas);

	my_pfds[1].fd = fileno(stdin); //receber input
	my_pfds[1].events = POLLIN;
	my_pfds[0].fd = sockfd; // preenchimento do socket 
	my_pfds[0].events = POLLIN;

	for (j = 2; j < 5; j++) { //preenche a estrutura dos possiveis sockets (5)
		my_pfds[j].fd = -1;
		my_pfds[j].events = 0;
	}

	char polltest[150];
	char *keys = print_table();

	memset(&client, 0, sizeof(client));
	size_client = sizeof(struct sockaddr);

	while (1) {
		if (poll(my_pfds, 5, -1) > 0) {
			/*if(my_pfds[1].revents & POLLIN){
			 scanf("%s", polltest);
			 if(strncmp(polltest,"print",5)){
			 //printf("Servidor: %d",1);
			 for (i = 0; keys[i] != NULL; i++) {
			 printf("Key: %s \n",keys[i]);
			 }
			 //arranjar uma maneira de distinguir o servidor 1 do 2 = pelo porto
			 }
			 }*/
			if (my_pfds[0].revents & POLLIN) {
				connsockfd = accept(sockfd, (struct sockaddr *) &client,
						&size_client);

				if (my_pfds[0].fd != -1) {

					if (inet_ntop(AF_INET, &(client.sin_addr), ip,
							INET_ADDRSTRLEN) == NULL) {
						printf("Erro ao converter IP\n");
						close(my_pfds[1].fd);
						return -1;
					}
					printf("---> Cliente (%s,%d) ligou-se. \n", ip,
							ntohs(client.sin_port));
				}

				for (i = 2; i < 5; i++) {
					if (my_pfds[i].fd == -1) { //preencher um socket que esteja vazio
						my_pfds[i].fd = connsockfd;
						my_pfds[i].events = POLLIN;
						break;
					}
				}

			}

			for (i = 2; i < 5; i++) {

				if (my_pfds[i].revents & POLLIN) {
					printf("ola i %d \n", i);
					struct message_t *msg = (struct message_t*) malloc(
							sizeof(struct message_t));
					printf("ola2\n");
					char *msg_bufR;

					printf("ola struct\n");

					//Le tamanho do msg_buf enviado pelo cliente do socket referente 'a conexao		
					if ((nbytes = read(my_pfds[i].fd, &sizeR, sizeof(uint32_t)))
							< 0) {
						perror("Erro ao receber tamanho do cliente");
						close(my_pfds[i].fd);
						continue;
					}
					printf("ola read\n");
					size = ntohl(sizeR);
					msg_bufR = malloc(size);
					printf("size %d\n", size);


					//Le msg_buf enviado pelo cliente do socket referente 'a conexao
					if ((nbytes = read_all(my_pfds[i].fd, msg_bufR, size))
							< 0) {
						perror("Erro ao receber mensagem do cliente");
						close(my_pfds[i].fd);
						continue;
					}

					msg = buffer_to_message(msg_bufR, size);
					free(msg_bufR);

					if(msg->opcode=OC_PUT || msg->opcode=OC_CPUT || msg->opcode=OC_DEL){
						if(actualizar_backup(size,socSec,msg)<0){
							perror("Erro ao actualizar secundário");
							return -1;
						}
					}

					int r = invoke(msg);


					if (r != 0) {
						perror("ERRO OPCODE");
					}

					int size2 = 0;
					char *msg_buf = NULL;

					size2 = message_to_buffer(msg, &msg_buf);
					size2 = htonl(size2);

					//envia tamanho	
					if ((nbytes = write(my_pfds[i].fd, &size2, sizeof(size2)))
							!= sizeof(size2)) {
						perror("Erro ao enviar tamanho da mensagem ao cliente");
						close(my_pfds[i].fd);
						return -1;
					}

					//envia a mensagem
					size2 = ntohl(size2);

					if ((nbytes = write_all(my_pfds[i].fd, msg_buf, size2))
							!= size2) {
						perror("Erro ao enviar mensagem ao cliente");
						close(my_pfds[i].fd);
						return -1;
					}

				}

			}

		} //fecha primeiro if

	} //fecha while infinito

	//table_skel_destroy(t);
	// Fecha socket (só executado em caso de erro...)
	close(sockfd);
	return 0;
}
