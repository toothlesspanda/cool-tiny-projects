#include <stdlib.h>    /* uso: malloc, calloc, free */
#include <stdio.h>     /* uso: printf */
#include <math.h>      /* uso: sqrt, ceil */
#include "hashtable.h"

struct hashitem {
  int   key;
  void* info;
};

struct hashtable {
	struct hashitem **table,
                   *availableCell;
  int nElems,
      table_size;
};

/************************************************/
/* Funcao de Dispersao e de Pesquisa (Privadas) */

static int hf_tentativas,
           hf_key;
static Hashtable hf_table;

/* Funcao de dispersao que indica onde se deve colocar
 * o elemento se nao houver colisoes */
static int hash_value(Hashtable ht, int key)
{
  hf_tentativas = 0;
  hf_key        = key;
  hf_table      = ht;

  return hf_key % hf_table->table_size;
}

/* Funcao de pesquisa que indica o proximo indice a procurar
 * caso haja colisoes (o numero de colisoes e' dado pela
 * variavel privada 'hf_tentativas')
 */
static int hash_next( void )
{
  hf_tentativas++;              /* faz uma simples pesquisa linear */
  return (hf_key + hf_tentativas) % hf_table->table_size;
}

/****************************************/
/* Funcoes privadas auxiliares */

static int esta_disponivel(Hashtable ht, int n)
{
  return ht->table[n] == ht->availableCell;
}

static int esta_livre(Hashtable ht, int n)
{
  return ht->table[n] == NULL;
}

/* Devolve a posicao da chave na tabela se existir.
 * Se a chave nao existir devolve -1
 */
static int hash_procura(Hashtable ht, int key)
{
  int pos  = hash_value(ht, key),
      back = pos;

  do {
    if (esta_livre(ht, pos))
      return -1;
    if (esta_disponivel(ht, pos))
      pos = hash_next();            /* ver a posicao seguinte */
    else if (key == ht->table[pos]->key)
           return pos;
         else
           pos = hash_next();
  } while (back!=pos);      /* para se der uma volta completa */

  return -1;
}

/****************************************/
/* construtor: cria uma nova tabela (sem elementos) */
Hashtable hash_criar( void )
{
  Hashtable ht = malloc(sizeof(struct hashtable));

  ht->table_size = DIMENSAO_INICIAL;
  ht->table =                  /* neste caso, o calloc poe tudo a NULL */
    (struct hashitem**)calloc(ht->table_size, sizeof(struct hashitem*));
  ht->nElems = 0;

  ht->availableCell = malloc(sizeof(struct hashitem));
  ht->availableCell->key  = 0;
  ht->availableCell->info = NULL;

  return ht;
}

/****************************************/
/* Determina se um numero e' primo */
/* pre: n>1 */
static int ePrimo(int n) {
  if (n%2 == 0)
    return n==2;
  else
  {
    int i, limit = ceil(sqrt(n));
    for(i=3; i<=limit; i+=2)
      if (n%i == 0)
        return 0;
  }
  return 1;
}

/* Dado um 'n' encontra o proximo numero primo */
/* pre: n>1 */
static int buscar_proximo_primo(int n) {
  while (!ePrimo(n))
    n++;
  return n;
}

/* Aumenta a capacidade da tabela de dispersao
 * Para isso, duplica a dimensao da tabela e procura
 * o primo seguinte, para manter a eficacia da
 * dispersao. Por exemplo, se a tabela tem capacidade 11
 * e e' aumentada, passara' a ter 23 elementos, pois
 * 23 e' o proximo primo depois de 11*2=22 
 */
static void grow(Hashtable ht)
{
  int i, old_size = ht->table_size;
  struct hashitem **old = ht->table;

  ht->nElems = 0;
  ht->table_size = buscar_proximo_primo(2*ht->table_size);
  ht->table =
     (struct hashitem**)calloc(ht->table_size, sizeof(struct hashitem*));

  for(i=0; i<old_size; i++)
    if (old[i] != ht->availableCell && old[i] != NULL)
      hash_inserir(ht, old[i]->key, old[i]->info);

  free(old);
}

/* Insere um novo elemento da tabela de hash */
void hash_inserir(Hashtable ht, int key, void* info)
{
  int pos;

  if ((float)ht->nElems / ht->table_size > LOAD_FACTOR)
    grow(ht);

  pos = hash_value(ht, key);

  while(!esta_livre(ht, pos) && !esta_disponivel(ht, pos))
    pos = hash_next();

  ht->table[pos] = malloc(sizeof(struct hashitem));
  ht->table[pos]->key  = key;
  ht->table[pos]->info = info;
  ht->nElems++;
}

/* Apaga todos os elementos da tabela */
void hash_apagar(Hashtable ht)
{
  int i;

  for(i=0; i<ht->table_size; i++)
    if (!esta_livre(ht, i) && !esta_disponivel(ht, i)) {
      free(ht->table[i]);
      ht->table[i] = NULL;
    }
  ht->nElems = 0;
}

/* destrutor: liberta toda a memoria reservada para a tabela */
void hash_destruir(Hashtable ht)
{
  hash_apagar(ht);
  free(ht->table);
  free(ht->availableCell);
  free(ht);
}

/* Verifica se a tabela esta' vazia */
int hash_esta_vazia(Hashtable ht)
{
  return ht->nElems == 0;
}

/* Verifica se o elemento cuja chave e' key esta' na tabela */
int hash_pertence(Hashtable ht, int key)
{
  return hash_procura(ht, key) >= 0;
}

/* Consulta a informacao cuja chave e' key */
/* pre: hash_pertence(ht, key) */
void* hash_consultar(Hashtable ht, int key)
{
  return ht->table[hash_procura(ht,key)]->info;
}

/* Remove o elemento cuja chave e' key.
 * Neste caso, a posicao da tabela fica disponivel e nao vazia para manter
 * a integridade do aglomerado de chaves fruto de potenciais colisoes.
 */
/* pre: hash_pertence(ht, key) */
void hash_remover(Hashtable ht, int key)
{
  int pos = hash_procura(ht, key);

  free(ht->table[pos]);
  ht->table[pos] = ht->availableCell;  /* torna-a disponivel, nao vazia */
  ht->nElems--;
}

/* descritor: mostra todo o conteudo da tabela (incluindo vazios) */
void hash_printf(Hashtable ht)
{
  int i;
  printf("[|");
  for(i=0; i<ht->table_size; i++)
    if (esta_livre(ht,i))
       printf("() ");
    else if (esta_disponivel(ht,i))
           printf("-> ");
         else
           printf("%d ", ht->table[i]->key);
	printf("|]");
}
/****************************************/
