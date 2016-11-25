#ifndef _LISTA
#define _LISTA

/* TDA lista de inteiros */
typedef struct lista *Lista;

/***** operacoes *****/

/* construtor: criar uma lista vazia */
Lista lista_criar( void );

/* destrutor: libertar toda a memoria da lista */
void lista_destruir( Lista l );

/* apagar todos os elementos da lista */
void lista_apagar( Lista l );

/* verifica se lista esta' vazia */
int lista_esta_vazia( Lista l );

/* devolve o elemento 'a cabeca */
/* pre: !lista_vazia(l) */
int lista_cabeca( Lista l );

/* devolve o ultimo elemento da lista */
/* pre: !lista_vazia(l) */
int lista_ultimo( Lista l );

/* inserir elemento 'a cabeca */
void lista_inserir_cabeca( Lista l, int elem );

/* inserir no fim da lista */
void lista_inserir_ultimo( Lista l, int elem );

/* Inserir, na lista, o elemento no indice dado (cabeca e' o indice 0) */
/* pre: 0 <= indice <= lista_comprimento(l) */
void lista_inserir_indice( Lista l, int indice,int elem );

/* remover elemento da cabeca */
/* pre: !lista_vazia(l) */
void lista_remover_cabeca( Lista l );

/* remover o ultimo elemento da lista */
/* pre: !lista_vazia(l) */
void lista_remover_ultimo( Lista l );

/* devolve o num.de elementos da lista */
int lista_comprimento( Lista l );

/* verifica se duas listas sao iguais */
int lista_igual( Lista l1, Lista l2 );

/* verifica se o elemento existe na lista */
int lista_contem( Lista l, int elem );

/* devolve o i-esimo elemento da lista(comeca no indice 0) */
/* pre: 0 <= index < lista_comprimento(l) */
int lista_indice( Lista l, int indice );

/* duplicador: devolve uma copia da lista dada */
Lista lista_copiar( Lista l );

/* descritor: imprimir lista no formato [a,b,c] */
void lista_printf( Lista l );

/* controlo de erro */

/* devolve o erro associado 'as operacoes */
int lista_erro( void );

/* reinicia o erro associado 'as operacoes */
void lista_reset_erro( void );

/***** constantes (usadas no controlo do erro) *****/

/* operacao correu sem erros */
#define LISTA_OK 0

/* erro ao reservar memoria para a lista */
#define LISTA_MEM_ERROR 1

#endif