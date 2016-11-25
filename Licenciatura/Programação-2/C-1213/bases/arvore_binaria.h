#ifndef _BINARYTREE
#define _BINARYTREE

/* TDA arvore binaria de inteiros */
typedef struct noArvBin *ArvBin;

/* operacoes */
/* construtor: criar uma arvore vazia */
ArvBin arvore_criar (void);

/* construtor: criar uma arvore a partir de um inteiro e de
 * outras duas arvores.
 */
ArvBin arvore_agregar (int elem, ArvBin esquerda,
                       ArvBin direita);

/* destrutor: libertar toda a memoria da arvore */
ArvBin arvore_destruir (ArvBin a);

/* verifica se arvore esta' vazia */
int arvore_esta_vazia (ArvBin a);

/* devolve o elemento na raiz */
/* pre: !arvore_esta_vazia(a) */
int arvore_valor_raiz(ArvBin a);

/* devolve o filho esquerdo na raiz */
/* pre: !arvore_esta_vazia(a) */
ArvBin arvore_esquerda(ArvBin a);

/* devolve o filho direito na raiz */
/* pre: !arvore_esta_vazia(a) */
ArvBin arvore_direita(ArvBin a);

/* devolve o número de elementos da arvore */
int arvore_tamanho(ArvBin a);

/* devolve a altura da arvore (uma arvore vazia tem altura zero) */
int arvore_altura(ArvBin a);

/* verifica se elemento pertence 'a arvore */
int arvore_contem(ArvBin a, int elemento);

/* descritor: imprime o conteudo da arvore */
void arvore_printf(ArvBin a);

/*******************************************************/
/* constantes (sendo estas usadas no controlo do erro) */
/* operacao correu sem erros */
#define ARVBIN_OK 0

/* erro ao reservar memoria para a arvore */
#define ARVBIN_MEM_ERROR 1

/* devolve o erro associado 'as operacoes */
int arvore_erro (void);

/* reinicia o erro associado 'as operacoes */
void arvore_reset_erro (void);

#endif




