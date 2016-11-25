#ifndef _HAHSTABLE
#define _HAHSTABLE

/* A dimensao deve ser um numero primo para se obter um bom desempenho.
 * O Load_factor deve ser inferior a 75% para evitar um excesso de colisoes.
 */
#define DIMENSAO_INICIAL 11
#define LOAD_FACTOR      0.5

/* tipos de dados */

typedef struct hashtable *Hashtable;

/* operacoes */

/* construtor: cria uma nova tabela (sem elementos) */
Hashtable hash_criar (void);

/* Insere um novo elemento da tabela de hash */
void hash_inserir(Hashtable ht, int key, void* info);

/* Apaga todos os elementos da tabela */
void hash_apagar(Hashtable ht);

/* destrutor: liberta toda a memoria reservada para a tabela */
void hash_destruir(Hashtable ht);

/* Verifica se a tabela esta' vazia */
int hash_esta_vazia(Hashtable ht);

/* Verifica se o elemento cuja chave e' key esta' na tabela */
int hash_pertence(Hashtable ht, int key);

/* Consulta a informacao cuja chave e' key */
/* pre: hash_pertence(ht, key) */
void* hash_consultar(Hashtable ht, int key);

/* Remove o elemento cuja chave e' key */
/* pre: hash_pertence(ht, key) */
void hash_remover(Hashtable ht, int key);

/* descritor: mostra todo o conteudo da tabela (incluindo vazios) */
void hash_printf(Hashtable ht);

#endif