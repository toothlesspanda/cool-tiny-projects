#ifndef _CLIENT_STUB_PRIVATE_H
#define _CLIENT_STUB_PRIVATE_H
#include "network_client-private.h"
#include "message-private.h"
#include "client_stub.h"
#include "inet.h"

/*remote tabela*/
struct rtable_t{
	struct message_t *msg;
	struct server_t *srv;
};



#endif
