#include <stdbool.h> //para usar variaveis booleanas

/* Estrutura partilhada para sincronização dos processos */
struct sincronizar
{
	pthread_mutex_t mutex_tatuadora;
	pthread_mutex_t mutex_cadeira;
	pthread_cond_t cond;
};

/* Estrutura partilhada para manter estatisticas */
struct estatisticas
{
	int cheio;
	int esperei;
	int descansar;
	int pronta;
};


/**
 * Funcao generica para criar recurso (create==true) 
 * ou para se ligar a recurso (create==false)
 * Argumentos:
 * 1) string com nome do recurso, 
 * 2) tamanho (em bytes) do recurso,
 * 3) variavel booleana para definir se recurso é para criar
 * ou se e' apenas para o processo se ligar a recurso ja criado
 * Retorno: ponteiro para recurso partilhado
 */
void *inicia_recurso(char *nome, int tamanho, bool create);

/* Funcao generica para destruir recurso partilhado
 * Argumentos:
 * 1) string com nome do recurso, 
 * 2) tamanho (em bytes) do recurso,
 * 3) ponteiro para recurso partilhado.
 */
void destroi_recurso(char *nome, int tamanho, void *partilha);
