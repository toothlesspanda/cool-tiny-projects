#include <pthread.h>
#include <sys/mman.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdbool.h>
#include <semaphore.h>
#include "mem_partilhada.h"
#include "SOpTattoo.h"

struct partilhar{
   int tatuadora;
   int num_cads;
   int *num_cls;
   int *recurso1, *recurso2;
};
struct partilhar *partilha; 

struct sincronizar *sinc;

/**
 * Esta funcao cria o estudio SOp Tattoo (faz todas as inicializacoes)
 * Ha num_cadeiras na fila de espera. Inicialmente estao todas LIVREs.
 * A tatuadora vai estar a DESCANSAR no inicio.
 * E necessario criar seccoes de memoria partilhada, inicializar locks, e definir estados iniciais.
 * Parametros:
 * 1) tot_cl, numero total de clientes
 * 2) num_cad, numero de cadeiras do estudio de tatuagens.
 **/
void SOpTattoo_init(int num_cl, int num_cad){
     
   cria_recursos_partilhados(num_cad,num_cl,true);
   pthread_mutexattr_t attr1;
   pthread_mutexattr_t attr2;

   pthread_mutexattr_init(&attr1);
   pthread_mutexattr_setpshared(&attr1,PTHREAD_PROCESS_SHARED);
   pthread_mutex_init(&sinc->mutex_tatuadora,&attr1);
   pthread_mutexattr_init(&attr2);
   pthread_mutexattr_setpshared(&attr2,PTHREAD_PROCESS_SHARED);
   pthread_mutex_init(&sinc->mutex_cadeira,&attr2);

   partilha->tatuadora = 2;
   partilha->num_cads = num_cad;
   partilha->num_cls = &num_cl; 

}



/**
 * Funcao auxiliar que serve para inicializar os recursos partilhados que se julguem necessarios.
 * Parametros:
 * 1) tot_cl, numero total de clientes  
 * 2) num_cad define o numero de cadeiras do estudio de tatuagens
 * 3) criar e' variavel booleano que indica se e' para criar novo recurso (true) 
 * ou ligar-se a recurso existente (false)
 */
void cria_recursos_partilhados(int num_cad, int num_cl, bool criar){
   
 
   partilha =inicia_recurso("/partilhada",sizeof(struct partilhar),criar); 
   
   partilha->num_cls=inicia_recurso("/num_clientes",sizeof(int),criar);     
   
   sinc=inicia_recurso("/sinc2",sizeof(struct sincronizar),criar);
   
   partilha->recurso1=inicia_recurso("/cliente",sizeof(int),criar);
   
}



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
int fazer_tatuagem(int cliente){
     
    pthread_mutex_lock(&sinc->mutex_tatuadora);

   if(partilha->tatuadora == DESCANSAR){   /*cliente entra e pede tatuagem*/
      if(alguem_espera()==1)            /*tatuadora ve se ha alguem em espera;*/
         partilha->tatuadora=3;
      else
         partilha->tatuadora=2;        
     }
   
   if(partilha->tatuadora == OCUPADA){  /*cliente espera e procura cadeira*/
      procura_cadeira(cliente);
      partilha->tatuadora=-1;   
 
      if(partilha->num_cads == 0)   /*cliente sai*/
         partilha->tatuadora = -1;
      }
   
   if(partilha->tatuadora != DESCANSAR &&  partilha->tatuadora != OCUPADA){
         /*tatuagem é feita imediatamente   */   
      partilha->tatuadora = 3;
   }
   pthread_mutex_unlock(&sinc->mutex_tatuadora);

   return partilha->tatuadora; /*estado da tatuadora*/
}


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
int procura_cadeira(int cliente){
   int var;
   pthread_mutex_lock(&sinc->mutex_cadeira);
 
   printf("ola3\n");
   
   if(partilha->tatuadora== OCUPADA){
         if(partilha->num_cads == LIVRE){
            var=1;
            partilha->num_cads--;
         }
         else if (partilha->num_cads ==-1)
            var=0;
      }
   
   pthread_mutex_unlock(&sinc->mutex_cadeira);
   return var;
}


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

void sair_SOpTattoo(int cliente){
  
   pthread_mutex_lock(&sinc->mutex_tatuadora);

   destroi_recursos_SopTatoo();
   if(alguem_espera()==1) //existe clientes
        partilha->tatuadora=3;
   else //nao existe clientes 
        partilha->tatuadora=2;
   partilha->num_cls--;

   pthread_mutex_unlock(&sinc->mutex_tatuadora);
}


/**
 * Teste para ver se ha alguem na sala de espera.
 *
 * NOTA: Cadeira e' variavel partilhada. Necessario sincronizar acesso!
 * 
 * Retorno:  1 se ha clientes a espera, 0 se nao houver
 */
int alguem_espera(){

      if(partilha->num_cads != 0)
         return 1;
      else
        return 0;
}


/**
 * Destruir memoria partilhada que achar necessario.
 */
void destroi_recursos_SopTatoo(){

   destroi_recurso("/cliente",sizeof(int) ,partilha->recurso1);
   destroi_recurso("/sinc2",sizeof(struct sincronizar) ,sinc);
   destroi_recurso("/num_clientes",sizeof(int),partilha->num_cls);
   pthread_mutex_destroy(&sinc->mutex_tatuadora);
   pthread_mutex_destroy(&sinc->mutex_cadeira);

}




