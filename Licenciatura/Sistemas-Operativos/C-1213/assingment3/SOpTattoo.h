/**
 * Este e' o modulo principal que define o cenario - o estudio de tatuagens SOp Tattoo.
 * O numero de clientes e de cadeiras sao os argumentos de entrada do programa 
 */
#include <stdbool.h> //para usar variaveis booleanas

#define CHEIO -1 // SOp Tattoo esta cheio

#define LIVRE 0 // Cadeira livre
#define OCUPADA 1 // Cadeira/tatuadora ocupada
#define DESCANSAR 2 // Tatuadora a descansar
#define PRONTA 3 // Tatuadora pronta para trabalhar

#define ESPEREI 4 // Cliente esperou um pouco antes de ser servido 

/**
 * Esta funcao cria o estudio SOp Tattoo (faz todas as inicializacoes)
 * Ha num_cadeiras na fila de espera. Inicialmente estao todas LIVREs.
 * A tatuadora vai estar a DESCANSAR no inicio.
 * E necessario criar seccoes de memoria partilhada, inicializar locks, e definir estados iniciais.
 * Parametros:
 * 1) tot_cl, numero total de clientes
 * 2) num_cad, numero de cadeiras do estudio de tatuagens.
 **/
void SOpTattoo_init(int num_cl, int num_cad);

/**
 * Funcao auxiliar que serve para inicializar os recursos partilhados que se julguem necessarios.
 * Parametros:
 * 1) tot_cl, numero total de clientes  
 * 2) num_cad define o numero de cadeiras do estudio de tatuagens
 * 3) criar e' variavel booleano que indica se e' para criar novo recurso (true) 
 * ou ligar-se a recurso existente (false)
 */
void cria_recursos_partilhados(int num_cad, int num_cl, bool criar);

/**
 * Esta funcao e' chamada quando o cliente quer fazer uma tatuagem. 
 * SE a tatuadora estiver a DESCANSAR entao o cliente pede para lhe ser feita a tatuagem.
 * SE a tatuadora esta ocupada o cliente ve se ha cadeiras livres. Se nao hover, sai da loja.
 * Se a tatuadora nao estiver a DESCANSAR nem OCUPADA entao pode servir o cliente imediatamente.
 *
 * NOTA: Cadeira e tatuadora sao variaveis partilhadas. Necessario sincronizar acesso a esta funcao!
 *
 * Parametros:
 * 1) O cliente que quer ser tatuado
 * Retorno: o estado da tatuadora
 */
int fazer_tatuagem(int cliente);

/**
 * Esta funcao e' chamada quando o cliente verifica que a tatuadora esta ocupada.
 * O cliente tem de esperar.  Ve se ha cadeira livre e marca-a OCUPADA
 * e retorna 1. Caso contrario, nao ha cadeiras, retorna 0.
 *
 * NOTA: Cadeira e' variavel partilhada. Necessario sincronizar acesso!
 *
 * Parametros: cliente - o cliente que esta a procura de lugar.
 * Retorno: 1 se encontrar cadeira vazia, 0 se SOp Tattoo estiver cheio.
 */
int procura_cadeira(int cliente);

/**
 * Esta funcao e' chamada quando o cliente ja fez a tatuagem e sai da loja
 * Primeiro e' preciso ver se ha mais algum cliente a espera. Se nao houver,
 * tatuadora descansa. Caso contrario, colocar o estado da tatuadora como PRONTO.
 * Finalmente, notificar quem esteja a espera.
 *
 * NOTA: Tatuadora e' variavel partilhada. Necessario sincronizar acesso!
 *
 * PARAMETRO: O cliente que terminou e que vai sair da SOp Tattoo
 */
void sair_SOpTattoo(int cliente);

/**
 * Teste para ver se ha alguem na sala de espera.
 *
 * NOTA: Cadeira e' variavel partilhada. Necessario sincronizar acesso!
 * 
 * Retorno:  1 se ha clientes a espera, 0 se nao houver
 */
int alguem_espera();

/**
 * Destruir memoria partilhada que achar necessario.
 */
void destroi_recursos_SopTatoo();
