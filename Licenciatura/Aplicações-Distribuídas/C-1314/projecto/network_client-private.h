#ifndef _NETWORK_CLIENT_PRIVATE_H
#define _NETWORK_CLIENT_PRIVATE_H

#include "network_client.h"
#define RETRY_TIME 10

struct server_t {
	int socketNum;
	struct sockaddr_in server;
	const char *address_p;
};

#endif
